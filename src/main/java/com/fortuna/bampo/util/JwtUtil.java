package com.fortuna.bampo.util;

import ch.qos.logback.core.CoreConstants;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fortuna.bampo.config.properties.JwtProperties;
import com.fortuna.bampo.entity.User;
import com.fortuna.bampo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.Cookie;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * JWT 工具
 *
 * @author Eva7
 * @since 0.0.1
 */
@Configuration
@RequiredArgsConstructor
public class JwtUtil {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    /**
     * 创建 JWT 访问令牌
     *
     * @param user 令牌所有者用户
     * @return JWT 访问令牌
     */
    public String createAccessToken(User user) {
        return JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withIssuedAt(new Date())
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()
                        + jwtProperties.getAccessTokenTtl() * CoreConstants.MILLIS_IN_ONE_SECOND))
                .withClaim(JwtProperties.TOKEN_CLAIM_ROLES,
                        user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .sign(getAlgorithm());
    }

    /**
     * 创建 JWT 刷新令牌
     *
     * @param username 令牌所有者用户名
     * @param remember 是否记住
     * @return JWT 刷新令牌
     */
    public String createRefreshToken(String username, boolean remember) {
        return JWT.create()
                .withIssuer(jwtProperties.getIssuer())
                .withIssuedAt(new Date())
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + (remember
                        ? jwtProperties.getRememberDays() * CoreConstants.MILLIS_IN_ONE_DAY
                        : jwtProperties.getRefreshTokenTtl() * CoreConstants.MILLIS_IN_ONE_SECOND)))
                .sign(getAlgorithm());
    }

    // TODO: Should not return a boolean but the token

    public boolean verifyAccessToken(Cookie cookie) {
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm())
                .withIssuer(jwtProperties.getIssuer())
                .withClaimPresence(JwtProperties.TOKEN_CLAIM_ROLES)
                .build();
        try {
            // TODO: Verify the authorities
            // TODO: Exception messages
            DecodedJWT decodedAccessToken = jwtVerifier.verify(cookie.getValue());
            Collection<SimpleGrantedAuthority> authorities =
                    decodedAccessToken.getClaim(JwtProperties.TOKEN_CLAIM_ROLES).asList(String.class)
                            .stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());

            SecurityContextHolder.getContext()
                    .setAuthentication(new UsernamePasswordAuthenticationToken(decodedAccessToken.getSubject(),
                            null, authorities));
            return true;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return false;
        }
    }

    public String refreshAccessToken(Cookie cookie) {
        JWTVerifier jwtVerifier = JWT.require(getAlgorithm())
                .withIssuer(jwtProperties.getIssuer())
                .build();
        DecodedJWT decodedRefreshToken = jwtVerifier.verify(cookie.getValue());
        User user = userService.loadUserByUsername(decodedRefreshToken.getSubject());
        return createAccessToken(user);
    }

    private Algorithm getAlgorithm() {
        return Algorithm.HMAC256(jwtProperties.getSecret());
    }

}
