package com.fortuna.bampo.filter;

import ch.qos.logback.core.CoreConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fortuna.bampo.config.properties.JwtProperties;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.model.request.UserLoginRequest;
import com.fortuna.bampo.model.response.LoginResponse;
import com.fortuna.bampo.util.JwtUtil;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

import static com.fortuna.bampo.config.properties.JpaProperties.USER_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.JwtProperties.*;
import static com.fortuna.bampo.config.properties.RestApiProperties.LOGIN_PATH;
import static com.fortuna.bampo.config.properties.RestApiProperties.PREFIX;

/**
 * 用户验证 Filter
 *
 * @author Eva7
 * @since 0.3.7
 */
public class UserAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    // TODO: Exceptions, Without Cookies

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;
    private final AuthenticationManager authenticationManager;
    private final ThreadLocal<Boolean> remember = new ThreadLocal<>();

    public UserAuthenticationFilter(JwtUtil jwtUtil, JwtProperties jwtProperties,
                                    AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl(PREFIX + USER_ENTITY_NAME
                + LOGIN_PATH);
    }

    @Override
    @SneakyThrows(IOException.class)
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        UserLoginRequest userLoginRequest = new ObjectMapper().readValue(request.getInputStream(),
                UserLoginRequest.class);
        remember.set(userLoginRequest.isRemember());

        // TODO: email login
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLoginRequest.getData().getUsername(),
                        userLoginRequest.getData().getPassword(), new ArrayList<>());
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        String accessToken = jwtUtil.createAccessToken(user);
        String refreshToken = jwtUtil.createRefreshToken(user.getUsername(), remember.get());
        response.setHeader(HttpHeaders.SET_COOKIE, String.format(COOKIE_HEADER_FORMAT,
                jwtProperties.getAccessTokenName(), accessToken,
                COOKIE_PATH, jwtProperties.getPath(),
                COOKIE_MAX_AGE, jwtProperties.getAccessTokenTtl(),
                COOKIE_SAME_SITE, jwtProperties.getSameSite().attributeValue(),
                jwtProperties.isSecure() ? COOKIE_SECURE : CoreConstants.EMPTY_STRING,
                jwtProperties.isHttpOnly() ? COOKIE_HTTP_ONLY : CoreConstants.EMPTY_STRING));
        response.addHeader(HttpHeaders.SET_COOKIE, String.format(COOKIE_HEADER_FORMAT,
                jwtProperties.getRefreshTokenName(), refreshToken,
                COOKIE_PATH, jwtProperties.getPath(),
                COOKIE_MAX_AGE, jwtProperties.getRefreshTokenTtl(),
                COOKIE_SAME_SITE, jwtProperties.getSameSite().attributeValue(),
                jwtProperties.isSecure() ? COOKIE_SECURE : CoreConstants.EMPTY_STRING,
                jwtProperties.isHttpOnly() ? COOKIE_HTTP_ONLY : CoreConstants.EMPTY_STRING));
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(new ObjectMapper().writeValueAsString(LoginResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Login success")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault())
                        .format(DateTimeFormatter.ISO_OFFSET_DATE_TIME))
                .build()));
        remember.remove();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(new ObjectMapper().writeValueAsString(LoginResponse.builder()
                .message("Failed to login")
                .error(failed.getLocalizedMessage())
                .status(HttpStatus.FORBIDDEN.value())
                .trace(Arrays.toString(failed.getStackTrace()))
                .build()));
        remember.remove();
    }
}