package uz.qodirov.constant;

import org.springframework.security.core.GrantedAuthority;

public enum Privilege implements GrantedAuthority {
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
