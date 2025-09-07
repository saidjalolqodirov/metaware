package uz.qodirov.config;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.qodirov.constant.Privilege;
import uz.qodirov.constant.Status;
import uz.qodirov.user.UserEntity;

import java.util.ArrayList;
import java.util.Collection;

@Getter
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 5377255672157361226L;

    private final UserEntity user;

    private final Collection<Privilege> authorities;

    public UserDetailsImpl(UserEntity user) {
        this.user = user;
        if (user != null) {
            authorities = user.getPrivileges();
        } else {
            authorities = new ArrayList<>();
        }
    }

    public String getId() {
        return this.user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    public Boolean getEnabled() {
        return user.getStatus().equals(Status.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return this.user.getStatus().equals(Status.ACTIVE);
    }

}
