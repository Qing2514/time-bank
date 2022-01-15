package com.fortuna.bampo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Token 配置
 *
 * @author Eva7
 * @since 0.2.3
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bampo.user")
public class UserProperties {
    private int verificationTokenTtl = 60 * 60;
    private int mailResendInterval = 5 * 60;
    private int usernameUpdateInterval = 90 * 24 * 60 * 60;
}
