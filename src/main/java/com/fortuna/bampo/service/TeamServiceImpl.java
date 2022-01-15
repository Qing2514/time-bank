package com.fortuna.bampo.service;

import ch.qos.logback.core.CoreConstants;
import com.fortuna.bampo.entity.Activity;
import com.fortuna.bampo.entity.Team;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.model.request.data.TeamRegistration;
import com.fortuna.bampo.model.request.data.TeamSearchFilter;
import com.fortuna.bampo.model.response.data.TeamAbstract;
import com.fortuna.bampo.model.response.data.TeamInfo;
import com.fortuna.bampo.repository.TeamRepository;
import com.fortuna.bampo.util.CodecUtil;
import com.fortuna.bampo.util.FormatUtil;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.IntegerType;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.fortuna.bampo.config.properties.RestApiProperties.DEFAULT_PAGE;
import static com.fortuna.bampo.config.properties.RestApiProperties.DEFAULT_PAGE_SIZE;

/**
 * 团队服务实现
 *
 * @author CMT, Eva7, Qing2514
 * @since 0.2.5
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserService userService;

    @Override
    public UUID register(TeamRegistration teamRegistration, String username) {
        // Build the new team
        Team team = Team.builder()
                .name(teamRegistration.getName())
                .city(teamRegistration.getCity())
                .description(teamRegistration.getDescription())
                .founder(userService.loadUserByUsername(username))
                .type(Activity.Type.fromString(teamRegistration.getType()))
                .build();
        // Join team
        team.getMembers().add(userService.loadUserByUsername(username));
        return teamRepository.saveAndFlush(team).getId();
    }

    @Override
    public List<String> search(String query, String order, String orderBy, Integer page, Integer pageSize,
                               TeamSearchFilter teamSearchFilter) {
        List<UUID> teamIds =
                teamRepository.findPageByNameAndDescriptionContaining(FormatUtil.escapeAndFormatQuery(query),
                        Objects.requireNonNullElse(teamSearchFilter.getCity(), CoreConstants.EMPTY_STRING),
                        Objects.requireNonNullElse(teamSearchFilter.getFounder(), CoreConstants.EMPTY_STRING),
                        Activity.Type.fromString(teamSearchFilter.getType()),
                        Objects.requireNonNullElse(teamSearchFilter.getNumberLower(), IntegerType.ZERO),
                        Objects.requireNonNullElse(teamSearchFilter.getNumberUpper(), Integer.MAX_VALUE),
                        PageRequest.of(Objects.requireNonNullElse(page, DEFAULT_PAGE),
                                Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE),
                                Objects.isNull(orderBy) ? Sort.unsorted()
                                        : Sort.by(Sort.Direction.fromOptionalString(order)
                                                .orElse(Sort.Direction.ASC), orderBy)));
        return teamIds.isEmpty() ? Collections.emptyList()
                : teamIds.stream().map(CodecUtil::encodeUuid).collect(Collectors.toList());
    }

    @Override
    public TeamInfo getInfo(String encodedId, Integer page, Integer pageSize) {
        Integer finalPage = Objects.requireNonNullElse(page, DEFAULT_PAGE);
        Integer finalPageSize = Objects.requireNonNullElse(pageSize, DEFAULT_PAGE_SIZE);
        return teamRepository.findInfoById(CodecUtil.decodeUuid(encodedId))
                .map(teamInfoDTO -> TeamInfo.builder()
                        .city(teamInfoDTO.getCity())
                        .name(teamInfoDTO.getName())
                        .type(teamInfoDTO.getType().toString())
                        .description(teamInfoDTO.getDescription())
                        .id(CodecUtil.encodeUuid(teamInfoDTO.getId()))
                        .founder(teamInfoDTO.getFounder().getUsername())
                        .members(teamInfoDTO.getMembers() == null
                                ? Collections.emptyList()
                                : teamInfoDTO.getMembers().stream()
                                        .map(User::getUsername)
                                        .collect(Collectors.toList())
                                        .subList(finalPage * finalPageSize,
                                                Math.min(finalPage * finalPageSize + finalPageSize,
                                                        teamInfoDTO.getMembers().size())))
                        .build()).orElseThrow(() -> new UsernameNotFoundException("Team id not found"));
    }

    @Override
    public TeamAbstract getAbstract(String encodedId, String query) {
        return teamRepository.findAbstractById(CodecUtil.decodeUuid(encodedId))
                .map(teamAbstractDTO -> TeamAbstract.builder()
                        .name(teamAbstractDTO.getName())
                        .city(teamAbstractDTO.getCity())
                        .type(teamAbstractDTO.getType().toString())
                        .id(CodecUtil.encodeUuid(teamAbstractDTO.getId()))
                        .founder(teamAbstractDTO.getFounder().getUsername())
                        .description(FormatUtil
                                .abstractDescriptionByKeyword(teamAbstractDTO.getDescription(), query))
                        .build()).orElseThrow(() -> new UsernameNotFoundException("Team id not found"));
    }

    @Override
    public boolean delete(String encodedId) {
        return teamRepository.deleteTeamById(CodecUtil.decodeUuid(encodedId)) != IntegerType.ZERO;
    }

    @Override
    public boolean update(String encodedId, String name, String city, String type, String description) {
        Team team = teamRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(t -> {
                    t.setName(Objects.requireNonNullElse(name, t.getName()));
                    t.setCity(Objects.requireNonNullElse(city, t.getCity()));
                    t.setType(Activity.Type.fromString(Objects.requireNonNullElse(type, t.getType().toString())));
                    t.setDescription(Objects.requireNonNullElse(description, t.getDescription()));
                    return t;
                }).orElseThrow(() -> new IllegalStateException("Team id not found"));
        return teamRepository.saveAndFlush(team) == team;
    }

    @Override
    public boolean join(String encodedId, String username) {
        Team team = teamRepository.findById(CodecUtil.decodeUuid(encodedId))
                .map(t -> {
                    if (t.getMembers().add(userService.loadUserByUsername(username))) {
                        return t;
                    } else {
                        throw new IllegalStateException("Failed to join team");
                    }
                }).orElseThrow(() -> new IllegalStateException("Team id not found"));
        return teamRepository.saveAndFlush(team) == team;
    }

    @Override
    public boolean quit(String encodedId, String username) {
        Team team = teamRepository.findById(CodecUtil.decodeUuid(encodedId))
                .orElseThrow(() -> new IllegalStateException("Team id not found"));
        return team.getMembers().remove(userService.loadUserByUsername(username))
                && teamRepository.saveAndFlush(team) == team;
    }

    @Override
    public boolean transfer(String founder, String encodedId, String newFounder) {
        Team team = teamRepository.findById(CodecUtil.decodeUuid(encodedId))
                .filter(t -> Objects.equals(t.getFounder().getUsername(), founder)
                        && t.getMembers().contains(userService.loadUserByUsername(newFounder)))
                .map(t -> {
                    t.setFounder(userService.loadUserByUsername(newFounder));
                    return t.getMembers().remove(userService.loadUserByUsername(newFounder))
                            && t.getMembers().add(userService.loadUserByUsername(founder)) ? t : null;
                }).orElseThrow(() -> new IllegalStateException("Team id not found"));
        return teamRepository.saveAndFlush(team) == team;
    }
}
