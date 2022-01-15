package com.fortuna.bampo.service;

import ch.qos.logback.core.CoreConstants;
import com.fortuna.bampo.config.properties.MiscProperties;
import com.fortuna.bampo.config.properties.UserProperties;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.entity.UserRole.Role;
import com.fortuna.bampo.entity.UserVerificationToken;
import com.fortuna.bampo.model.request.data.UserRegistration;
import com.fortuna.bampo.model.request.data.UserSearchFilter;
import com.fortuna.bampo.model.response.data.EmailItem;
import com.fortuna.bampo.model.response.data.UserAbstract;
import com.fortuna.bampo.model.response.data.UserDetail;
import com.fortuna.bampo.model.response.data.UserInfo;
import com.fortuna.bampo.repository.UserRepository;
import com.fortuna.bampo.repository.UserRoleRepository;
import com.fortuna.bampo.repository.UserVerificationTokenRepository;
import com.fortuna.bampo.util.CodecUtil;
import com.fortuna.bampo.util.EmailUtil;
import com.fortuna.bampo.util.FormatUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.fortuna.bampo.config.properties.JpaProperties.USER_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.RestApiProperties.*;

/**
 * 用户服务实现
 *
 * @author Eva7, Qing2514, lhx, CMT
 * @since 0.3.1
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final EmailUtil emailUtil;
    private final UserRepository userRepository;
    private final UserProperties userProperties;
    private final MiscProperties miscProperties;
    private final UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserVerificationTokenRepository userVerificationTokenRepository;

    @Override
    public boolean register(UserRegistration userRegistration) throws EntityExistsException,
            EntityNotFoundException {
        // Check if the identity exists
        loadUserByIdentity(userRegistration.getIdentity()).ifPresent(duplicateUser -> {
            throw new IllegalStateException(String.format("Identity already exists, User %s",
                    duplicateUser.getUsername()));
        });
        // Build the new user
        User newUser = User.builder()
                .city(userRegistration.getCity())
                .email(userRegistration.getEmail())
                .enabled(miscProperties.isDevelopment())
                .username(userRegistration.getUsername())
                .fullName(userRegistration.getFullName())
                .phoneNumber(userRegistration.getPhoneNumber())
                .id(bCryptPasswordEncoder.encode(userRegistration.getIdentity()))
                .password(bCryptPasswordEncoder.encode(userRegistration.getPassword()))
                .build();
        // Add default role
        userRoleRepository.findByName(Role.VOLUNTEER)
                .ifPresentOrElse(role -> newUser.getRoles().add(role), () -> {
                    // Throw an exception if the default role does not exist
                    throw new IllegalStateException(String.format("User role %s not found", Role.VOLUNTEER));
                });
        // Save the new user
        userRepository.saveAndFlush(newUser);
        // Send verification email
        return miscProperties.isDevelopment() || getVerification(newUser.getUsername());
    }

    @Override
    public boolean getVerification(String username) {
        userVerificationTokenRepository.deleteAllByDateExpiredBefore(LocalDateTime.now());
        User user = userRepository.findByUsername(username).orElseThrow();
        if (user.isEnabled()) {
            throw new IllegalStateException("Nothing to verify");
        }
        UUID token = userVerificationTokenRepository.findByUsernameAndDateExpiredAfter(username, LocalDateTime.now())
                .map(userVerificationDTO -> {
                    // Resend email
                    boolean canResend = userVerificationDTO.getDateSent()
                            .isBefore(LocalDateTime.now().minusSeconds(userProperties.getMailResendInterval()));
                    if (canResend) {
                        userVerificationTokenRepository
                                .saveAndFlush(userVerificationTokenRepository
                                        .findById(userVerificationDTO.getToken()).map(userVerificationToken -> {
                                            userVerificationToken.setDateSent(LocalDateTime.now());
                                            return userVerificationToken;
                                        }).orElseThrow());
                        return userVerificationDTO.getToken();
                    } else {
                        throw new IllegalStateException("Please wait for 5 minutes to resend a verification email");
                    }
                })
                // New token
                .orElseGet(() -> userVerificationTokenRepository
                        .saveAndFlush(UserVerificationToken.builder()
                                .user(user)
                                .dateExpired(LocalDateTime.now().plusSeconds(userProperties.getVerificationTokenTtl()))
                                .build()).getToken());
        EmailItem emailItem = EmailItem.builder()
                .to(user.getEmail())
                .subject(VERIFICATION_EMAIL_SUBJECT)
                .text(VERIFICATION_EMAIL_TEMPLATE
                        .replace(EMAIL_USERNAME_PATTERN, username)
                        .replace(EMAIL_VERIFICATION_LINK_PATTERN, ServletUriComponentsBuilder
                                .fromHttpUrl(miscProperties.getClientHost())
                                .path(USER_ENTITY_NAME.concat(SEPARATOR).concat(username).concat(VERIFICATION_PATH))
                                .queryParam(VERIFICATION_PARAM_TOKEN,
                                        CodecUtil.encodeUuid(token)).toUriString())
                        .replace(EMAIL_CONTACT_LINK_PATTERN, ServletUriComponentsBuilder
                                .fromHttpUrl(miscProperties.getClientHost())
                                .path(CONTACT_PATH).toUriString()))
                .build();
        return userVerificationTokenRepository.existsById(token) && emailUtil.sendSimpleMail(emailItem);
    }

    @Override
    public boolean verify(String encodedToken, String username) {
        UUID token = CodecUtil.decodeUuid(encodedToken);
        userVerificationTokenRepository.deleteAllByDateExpiredBefore(LocalDateTime.now());
        User user =
                userVerificationTokenRepository.findByTokenAndDateExpiredAfter(token, LocalDateTime.now())
                        .orElseThrow(() -> new IllegalStateException("Illegal verification")).getUser();
        user.setEnabled(true);
        return Objects.equals(username, user.getUsername())
                && userRepository.saveAndFlush(user) == user
                && userVerificationTokenRepository.deleteByToken(token) != 0;
    }

    @Override
    public List<String> search(String query,
                               String order,
                               String orderBy,
                               Integer page,
                               Integer pageSize,
                               UserSearchFilter userSearchFilter) {
        return userRepository.findPageByUsernameContaining(FormatUtil.escapeAndFormatQuery(query),
                Objects.requireNonNullElse(userSearchFilter.getCity(), CoreConstants.EMPTY_STRING),
                userSearchFilter.isLocked(),
                userSearchFilter.isEnabled(),
                userSearchFilter.getUserRoleList() == null ? null
                        : userSearchFilter.getUserRoleList().stream()
                                .map(Role::fromString).collect(Collectors.toList()),
                PageRequest.of(Objects.requireNonNullElse(page, DEFAULT_PAGE),
                        Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE),
                        Objects.isNull(orderBy) ? Sort.unsorted()
                                : Sort.by(Sort.Direction.fromOptionalString(order)
                                        .orElse(Sort.Direction.ASC), orderBy)));
    }

    @Override
    public UserAbstract getAbstract(String username) {
        return userRepository.findAbstractByUsername(username)
                .map(userAbstractDTO -> UserAbstract.builder()
                        .city(userAbstractDTO.getCity())
                        .username(userAbstractDTO.getUsername())
                        .followersCount(userAbstractDTO.getFollowers().size())
                        .roles(userAbstractDTO.getRoles().isEmpty() ? Collections.emptyList()
                                : userAbstractDTO.getRoles().stream().map(role -> role.getName().toString()).toList())
                        .teams(userAbstractDTO.getTeams().isEmpty()
                                ? Collections.emptyList()
                                : userAbstractDTO.getTeams().stream()
                                        .filter(team -> Objects.equals(team.getFounder().getUsername(),
                                                userAbstractDTO.getUsername()))
                                        .map(team -> CodecUtil.encodeUuid(team.getId()))
                                        .collect(Collectors.toList()))
                        .build()).orElseThrow(() -> new UsernameNotFoundException("user info not found"));
    }

    @Override
    public UserInfo getInfo(String username) {
        return userRepository.findInfoByUsername(username)
                .map(userInfoDTO -> UserInfo.builder()
                        .city(userInfoDTO.getCity())
                        .email(userInfoDTO.getEmail())
                        .username(userInfoDTO.getUsername())
                        .phoneNumber(userInfoDTO.getPhoneNumber())
                        .followersCount(userInfoDTO.getFollowers().size())
                        .followingCount(userInfoDTO.getFollowing().size())
                        .roles(userInfoDTO.getRoles().isEmpty() ? Collections.emptyList()
                                : userInfoDTO.getRoles().stream().map(role -> role.getName().toString()).toList())
                        .teams(userInfoDTO.getTeams().isEmpty()
                                ? Collections.emptyList()
                                : userInfoDTO.getTeams().stream()
                                        .map(team -> CodecUtil.encodeUuid(team.getId()))
                                        .collect(Collectors.toList()))
                        .build()).orElseThrow(() -> new UsernameNotFoundException("user info not found"));
    }

    @Override
    public UserDetail getDetail(String username) {
        return userRepository.findDetailByUsername(username)
                .map(userDetailDTO -> UserDetail.builder()
                        .city(userDetailDTO.getCity())
                        .email(userDetailDTO.getEmail())
                        .username(userDetailDTO.getUsername())
                        .fullName(userDetailDTO.getFullName())
                        .phoneNumber(userDetailDTO.getPhoneNumber())
                        .followersCount(userDetailDTO.getFollowers().size())
                        .followingCount(userDetailDTO.getFollowing().size())
                        .roles(userDetailDTO.getRoles().isEmpty() ? Collections.emptyList()
                                : userDetailDTO.getRoles().stream().map(role -> role.getName().toString()).toList())
                        .teams(userDetailDTO.getTeams().isEmpty()
                                ? Collections.emptyList()
                                : userDetailDTO.getTeams().stream()
                                        .map(team -> CodecUtil.encodeUuid(team.getId()))
                                        .collect(Collectors.toList()))
                        .build()).orElseThrow(() -> new UsernameNotFoundException("user details not found"));
    }

    @Override
    public boolean modify(String username, String city, String email, Long phoneNumber) {
        User user = userRepository.findByUsername(username)
                .map(u -> {
                    u.setCity(Objects.requireNonNullElse(city, u.getCity()));
                    u.setEmail(Objects.requireNonNullElse(email, u.getEmail()));
                    u.setPhoneNumber(Objects.requireNonNullElse(phoneNumber, u.getPhoneNumber()));
                    u.setEnabled(email != null ? miscProperties.isDevelopment() : u.isEnabled());
                    return u;
                }).orElseThrow(() -> new IllegalStateException("Password not match"));
        return userRepository.saveAndFlush(user) == user
                && (email == null || getVerification(user.getUsername()));
    }

    @Override
    public boolean updateUsername(String username, String password, String newUsername) {
        User user = userRepository.findByUsername(username)
                .filter(u -> bCryptPasswordEncoder.matches(password, u.getPassword()))
                .orElseThrow(() -> new IllegalStateException("Password not match"));
        boolean canUpdate = user.getDateUsernameUpdated()
                .isBefore(LocalDateTime.now().minusSeconds(userProperties.getUsernameUpdateInterval()));
        if (canUpdate) {
            user.setUsername(username);
            return userRepository.saveAndFlush(user) == user;
        } else {
            throw new IllegalStateException("Username can be updated only once every 90 days");
        }
    }

    @Override
    public boolean updatePassword(String username, String password, String newPassword) {
        User user = userRepository.findByUsername(username)
                .filter(u -> bCryptPasswordEncoder.matches(password, u.getPassword()))
                .map(u -> {
                    u.setPassword(bCryptPasswordEncoder.encode(newPassword));
                    return u;
                }).orElseThrow(() -> new IllegalStateException("Password not match"));
        return userRepository.saveAndFlush(user) == user;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    /**
     * 通过用户身份标识获取用户，返回一个 {@code Optional} 描述该用户实体，若获取结果为空则返回一个空的 {@code Optional}
     *
     * @param identity 用户身份标识
     * @return 返回一个 {@code Optional} 包含获取到的用户，若获取结果为空则返回一个空的 {@code Optional}
     * @throws IllegalStateException 当遇到未知错误时抛出
     */
    private Optional<User> loadUserByIdentity(String identity) throws IllegalStateException {
        Set<String> userIdList = userRepository.findAllIds();
        return userIdList.isEmpty() ? Optional.empty()
                : userIdList.stream()
                        // Find the id matches the identity
                        .filter(id -> bCryptPasswordEncoder.matches(identity, id))
                        // Make sure the result is unique
                        .reduce((id, duplicateId) -> {
                            throw new IllegalStateException("Duplicate identity " + id);
                        })
                        // Get the user
                        .map(id -> userRepository.findById(id).orElseThrow(() ->
                                new IllegalStateException("Unexpected error")));
    }
}
