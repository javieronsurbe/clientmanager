package es.mdef.clientmanager.security;

import es.mdef.clientmanager.domain.AppUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 4/08/14
 * Time: 21:34
 */
public class UserDetail implements UserDetails {

    private AppUser appUser;

    private List<GrantedAuthority> authorities;

    public UserDetail(AppUser appUser){
        this.appUser=appUser;
        this.authorities=new ArrayList<GrantedAuthority>();
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return appUser.getPassword().getValue();
    }

    @Override
    public String getUsername() {
        return appUser.getLogin();
    }

    public AppUser getAppUser(){
        return appUser;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
