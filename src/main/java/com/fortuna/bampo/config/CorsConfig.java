package com.fortuna.bampo.config;

import com.fortuna.bampo.config.properties.MiscProperties;
import com.fortuna.bampo.config.properties.RestApiProperties;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 跨域配置
 *
 * @author Eva7
 * @since 0.3.7
 */
@Configuration
@AllArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

    MiscProperties miscProperties;

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(@NonNull CorsRegistry registry) {
                registry.addMapping(RestApiProperties.BASE.concat("**"))
                        .allowedOrigins(miscProperties.getClientHost())
                        .allowedMethods(HttpMethod.GET.name(),
                                HttpMethod.POST.name(),
                                HttpMethod.PATCH.name(),
                                HttpMethod.DELETE.name())
                        .allowCredentials(true);
            }
        };
    }
}
