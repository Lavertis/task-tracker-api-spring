package org.lavertis.tasktrackerapi.config;

import lombok.Setter;
import org.lavertis.tasktrackerapi.entity.AppUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import java.util.Collection;

public class AppUserAuthentication implements Authentication {
    private final AppUser appUser;
    private boolean authenticated = true;
    @Setter
    private WebAuthenticationDetails details;


    public AppUserAuthentication(AppUser appUser) {
        this.appUser = appUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return appUser.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return appUser.getPassword();
    }

    @Override
    public WebAuthenticationDetails getDetails() {
        return details;
    }

    @Override
    public AppUser getPrincipal() {
        return appUser;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        this.authenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        return appUser.getUsername();
    }
}
