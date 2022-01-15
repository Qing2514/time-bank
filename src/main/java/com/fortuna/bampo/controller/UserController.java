package com.fortuna.bampo.controller;

import com.fortuna.bampo.model.request.ModificationRequest;
import com.fortuna.bampo.model.request.RegistrationRequest;
import com.fortuna.bampo.model.request.SearchRequest;
import com.fortuna.bampo.model.request.UpdateRequest;
import com.fortuna.bampo.model.request.data.*;
import com.fortuna.bampo.model.response.*;
import com.fortuna.bampo.model.response.data.UserAbstract;
import com.fortuna.bampo.model.response.data.UserInfo;
import com.fortuna.bampo.service.UserService;
import com.fortuna.bampo.util.ResponseUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

import static com.fortuna.bampo.config.properties.JpaProperties.USER_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.RestApiProperties.PREFIX;
import static com.fortuna.bampo.config.properties.RestApiProperties.REGISTRATION_PATH;

/**
 * 用户实体 RESTful API
 *
 * @author Eva7, Qing2514, lhx
 * @since 0.3.7
 */
@RestController
@AllArgsConstructor
@RequestMapping(PREFIX + USER_ENTITY_NAME)
public class UserController {

    private final UserService userService;

    /**
     * 注册用户
     *
     * @param request 接收到的 HTTP 请求
     * @return 注册结果
     */
    @PostMapping(REGISTRATION_PATH)
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest<UserRegistration> request) {
        UserRegistration userRegistration = request.getData();
        URI location =
                URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/{username}")
                        .buildAndExpand(userRegistration.getUsername()).toUriString());
        System.out.println(userRegistration);
        return userService.register(userRegistration)
                ? ResponseUtil.registrationResponseCreated(location)
                : ResponseUtil.registrationResponseBadRequest();
    }

    @GetMapping
    public ResponseEntity<InfoResponse<UserInfo>> getUser() {
        return ResponseUtil.infoResponseOk(userService
                .getInfo(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal())));
    }

    @GetMapping("/{username}/verification")
    public ResponseEntity<VerificationResponse> getVerification(@PathVariable String username) {
        matchPrincipal(username);
        return userService
                .getVerification(username) ? ResponseUtil.verificationResponseOk() :
                ResponseUtil.verificationResponseBadRequest();
    }

    @PostMapping("/{username}/verification")
    public ResponseEntity<VerificationResponse> verify(@RequestParam String token, @PathVariable String username) {
        return userService.verify(token, username)
                ? ResponseUtil.verificationResponseOk() : ResponseUtil.verificationResponseBadRequest();
    }

    /**
     * 搜索用户名
     *
     * @param request 接收到的 HTTP 请求
     * @return 用户摘要集合
     */
    @GetMapping("/search")
    public ResponseEntity<SearchResponse> search(@RequestBody SearchRequest<UserSearchFilter> request) {
        UserSearchFilter userSearchFilter = request.getData();
        return ResponseUtil.searchResponseOk(userService.search(request.getQuery(),
                request.getOrder(),
                request.getOrderBy(),
                request.getPage(),
                request.getPageSize(),
                userSearchFilter));
    }

    @GetMapping("/{username}/abstract")
    public ResponseEntity<AbstractResponse<UserAbstract>> getAbstract(@PathVariable String username) {
        return ResponseUtil.abstractResponseOk(userService.getAbstract(username));
    }

    @GetMapping("/{username}")
    public ResponseEntity<InfoResponse<UserInfo>> getInfo(@PathVariable String username) {
        return ResponseUtil.infoResponseOk(userService.getInfo(username));
    }

    @GetMapping("/detail")
    public ResponseEntity<Object> getDetail() {
        return ResponseEntity.ok(userService
                .getDetail(String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal())));
    }

    @PatchMapping("/{username}")
    public ResponseEntity<Object> modify(@RequestBody ModificationRequest<UserModification> request,
                                         @PathVariable String username) {
        UserModification userModification = request.getData();
        matchPrincipal(username);
        return userService.modify(username,
                userModification.getCity(), userModification.getEmail(), userModification.getPhoneNumber())
                ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{username}/username")
    public ResponseEntity<Object> updateUsername(@RequestBody UpdateRequest<UsernameUpdate> request,
                                                 @PathVariable String username) {
        matchPrincipal(username);
        return userService.updateUsername(username, request.getPassword(), request.getData().getUsername())
                ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PatchMapping("/{username}/password")
    public ResponseEntity<Object> updatePassword(@RequestBody UpdateRequest<PasswordUpdate> request,
                                                 @PathVariable String username) {
        matchPrincipal(username);
        return userService.updatePassword(username, request.getPassword(), request.getData().getPassword())
                ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    private void matchPrincipal(String username) {
        boolean match = Objects.equals(username,
                String.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        if (!match) {
            throw new IllegalStateException("Username does not match with principals");
        }
    }
}
