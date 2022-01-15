package com.fortuna.bampo.filter;

import ch.qos.logback.core.CoreConstants;
import com.fortuna.bampo.config.properties.JwtProperties;
import com.fortuna.bampo.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

import static com.fortuna.bampo.config.properties.JpaProperties.USER_ENTITY_NAME;
import static com.fortuna.bampo.config.properties.JwtProperties.*;
import static com.fortuna.bampo.config.properties.RestApiProperties.LOGIN_PATH;
import static com.fortuna.bampo.config.properties.RestApiProperties.PREFIX;

/**
 * 用户授权 Filter
 *
 * @author Eva7
 * @since 0.3.7
 */
public class UserAuthorizationFilter extends BasicAuthenticationFilter {

    // TODO: Filter standard
    // TODO: Change token refreshing logic
    // TODO: Code styling

    private final JwtUtil jwtUtil;
    private final JwtProperties jwtProperties;

    public UserAuthorizationFilter(JwtUtil jwtUtil, JwtProperties jwtProperties,
                                   AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if (request.getCookies() != null && !Objects.equals(request.getContextPath(),
                PREFIX + USER_ENTITY_NAME + LOGIN_PATH)) {
            Cookie[] cookies = request.getCookies();
            if (Arrays.stream(cookies)
                    .noneMatch(cookie -> Objects.equals(cookie.getName(), jwtProperties.getAccessTokenName())
                            && jwtUtil.verifyAccessToken(cookie))) {
                Arrays.stream(cookies)
                        .filter(cookie -> Objects.equals(cookie.getName(), jwtProperties.getRefreshTokenName()))
                        .findAny().ifPresent(cookie -> {
                            String accessToken = jwtUtil.refreshAccessToken(cookie);
                            response.setHeader(HttpHeaders.SET_COOKIE, String.format(COOKIE_HEADER_FORMAT,
                                    jwtProperties.getAccessTokenName(), accessToken,
                                    COOKIE_PATH, jwtProperties.getPath(),
                                    COOKIE_MAX_AGE, jwtProperties.getAccessTokenTtl(),
                                    COOKIE_SAME_SITE, jwtProperties.getSameSite().attributeValue(),
                                    jwtProperties.isSecure() ? COOKIE_SECURE : CoreConstants.EMPTY_STRING,
                                    jwtProperties.isHttpOnly() ? COOKIE_HTTP_ONLY : CoreConstants.EMPTY_STRING));
                        });
            }
        }
        filterChain.doFilter(request, response);
    }
}
