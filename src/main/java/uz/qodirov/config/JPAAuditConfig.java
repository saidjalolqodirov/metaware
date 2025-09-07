package uz.qodirov.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableTransactionManagement
public class JPAAuditConfig {

    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            if (SecurityContextHolder.getContext().getAuthentication() != null) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication.getPrincipal() instanceof UserDetailsImpl) {
                    return Optional.ofNullable(((UserDetailsImpl) authentication.getPrincipal()).getId());
                }
            }
            return Optional.empty();
        };
    }
}