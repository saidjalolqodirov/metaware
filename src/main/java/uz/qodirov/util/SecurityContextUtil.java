package uz.qodirov.util;

import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.qodirov.config.UserDetailsImpl;
import uz.qodirov.exception.DataNotFoundException;

@UtilityClass
public class SecurityContextUtil {
    public String getId() {
        return getUser().getId();
    }

    public UserDetailsImpl getUser() {
        SecurityContext context = getContext();
        if (context == null) {
            throw new DataNotFoundException("User not found");
        }

        if (context.getAuthentication().getPrincipal() instanceof UserDetailsImpl) {
            return (UserDetailsImpl) context.getAuthentication().getPrincipal();
        }
        throw new DataNotFoundException("User not found");
    }

    SecurityContext getContext() {
        SecurityContext context = SecurityContextHolder.getContext();

        if (context == null || context.getAuthentication() == null) {
            return null;
        }
        return context;
    }
}
