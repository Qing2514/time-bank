package com.fortuna.bampo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.server.Cookie.SameSite;
import org.springframework.context.annotation.Configuration;

/**
 * JWT 配置
 *
 * @author Eva7
 * @since 0.2.6
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bampo.jwt")
public class JwtProperties {

    public static final String COOKIE_HEADER_FORMAT = "%s=%s%s=%s%s=%s%s=%s%s%s";
    public static final String COOKIE_PATH = "; Path";
    public static final String COOKIE_MAX_AGE = "; Max-Age";
    public static final String COOKIE_SECURE = "; Secure";
    public static final String COOKIE_HTTP_ONLY = "; HttpOnly";
    public static final String COOKIE_SAME_SITE = "; SameSite";

    public static final String TOKEN_CLAIM_ROLES = "roles";

    private int rememberDays = 30;
    private int refreshTokenTtl = 60 * 60;
    private int accessTokenTtl = 10 * 60;
    private String issuer;
    private String secret;
    private String accessTokenName;
    private String refreshTokenName;
    private String path = "/";
    private boolean secure = true;
    private boolean httpOnly = true;
    private SameSite sameSite = SameSite.LAX;
}
