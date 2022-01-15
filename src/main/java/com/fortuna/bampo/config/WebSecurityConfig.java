package com.fortuna.bampo.config;

import com.fortuna.bampo.config.properties.JwtProperties;
import com.fortuna.bampo.entity.UserRole;
import com.fortuna.bampo.filter.UserAuthenticationFilter;
import com.fortuna.bampo.filter.UserAuthorizationFilter;
import com.fortuna.bampo.service.UserService;
import com.fortuna.bampo.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static com.fortuna.bampo.config.properties.JpaProperties.*;
import static com.fortuna.bampo.config.properties.RestApiProperties.*;

/**
 * Spring Security 配置
 *
 * @author Eva7
 * @since 0.1.1
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final JwtProperties jwtProperties;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().and()
                // No csrf
                .csrf().disable()
                // No session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // Request authorization
                .authorizeRequests()
                .antMatchers(PREFIX + USER_ENTITY_NAME + REGISTRATION_PATH).permitAll()
                .antMatchers(PREFIX + USER_ENTITY_NAME + VERIFICATION_PATH).permitAll()
                .antMatchers(HttpMethod.POST, PREFIX + ARTICLE_ENTITY_NAME)
                .hasAuthority(String.valueOf(new SimpleGrantedAuthority(UserRole.Role.EDITOR.name())))
                .antMatchers(HttpMethod.POST, PREFIX + ACTIVITY_ENTITY_NAME + "/verify")
                .hasRole(String.valueOf(new SimpleGrantedAuthority(UserRole.Role.VERIFIER.name())))
                .anyRequest().authenticated().and()
                // Authentication filter
                .addFilter(new UserAuthenticationFilter(jwtUtil, jwtProperties, authenticationManagerBean()))
                // Authorization filter
                .addFilterBefore(new UserAuthorizationFilter(jwtUtil, jwtProperties, authenticationManagerBean()),
                        UserAuthenticationFilter.class);
        // TODO: Exception handling
    }
}
