package es.mdef.clientmanager.security;

import es.mdef.clientmanager.domain.AppUser;
import es.mdef.clientmanager.repository.AppUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import java.util.ArrayList;
import java.util.List;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 7/06/14
 * Time: 22:56
 */
@Service
public class UserServiceImpl implements UserService{

    static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        try{
            AppUser appUser=appUserRepository.findByLogin(username);
            LOG.debug("APPUSER"+appUser.getUserName());
            UserDetail userDetail=new UserDetail(appUser);

            authorities.add
                    (new SimpleGrantedAuthority("ADMINISTRATOR"));
/*            User user = new User(appUser.getLogin(), appUser.getPassword().getValue(), true, true,
                    false, false, authorities);*/
            return userDetail;
        }catch (NoResultException nre){
            return null;
        }
    }

    public AppUserRepository getAppUserRepository() {
        return appUserRepository;
    }

    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
}
