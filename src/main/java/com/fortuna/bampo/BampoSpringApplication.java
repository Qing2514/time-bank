package com.fortuna.bampo;

import com.fortuna.bampo.entity.UserRole;
import com.fortuna.bampo.repository.UserRoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * 基于区块链的时间银行 Bampo 后端
 *
 * @author Fortuna
 * @since 0.0.2
 */
@SpringBootApplication
public class BampoSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(BampoSpringApplication.class, args);
    }

    @Bean
    CommandLineRunner run(UserRoleRepository userRoleRepository) {
        // Test init command
        return args -> {
            userRoleRepository.saveAndFlush(UserRole.builder().name(UserRole.Role.VOLUNTEER).build());
            userRoleRepository.saveAndFlush(UserRole.builder().name(UserRole.Role.VERIFIER).build());
            userRoleRepository.saveAndFlush(UserRole.builder().name(UserRole.Role.EDITOR).build());
            userRoleRepository.saveAndFlush(UserRole.builder().name(UserRole.Role.ADMIN).build());
        };
    }
}
