package com.fortuna.bampo.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 杂项设置
 *
 * @author Eva7
 * @since 0.2.2
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "bampo")
public class MiscProperties {
    private String clientHost;
    private String contractAddress;
    private boolean development = false;
}
