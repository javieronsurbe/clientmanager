package es.mdef.clientmanager.security;

import es.mdef.clientmanager.mail.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 9/06/14
 * Time: 0:10
 */
@Service
public class AuthManager implements AuthenticationManager, Serializable {


    @Autowired
    private UserService userService;

    @Autowired
    private MailService mailService;

    public Authentication authenticate(Authentication auth) throws AuthenticationException {
        String username = (String) auth.getPrincipal();
        String password = (String) auth.getCredentials();
        UserDetails user = userService.loadUserByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(username, password, authorities);
            authenticationToken.setDetails(user);
            return authenticationToken;
        }
        //TODO Read from and to from properties
//        mailService.sendMail("FROM","TO","Security exception",
//                "Alguien intenta logearse en la aplicacion con el usuario "+username+" y password "+password);
        throw new BadCredentialsException("Bad Credentials");
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public MailService getMailService() {
        return mailService;
    }

    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }
}
