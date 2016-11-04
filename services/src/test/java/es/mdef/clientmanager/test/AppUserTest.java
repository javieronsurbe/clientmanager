package es.mdef.clientmanager.test;

import es.mdef.clientmanager.domain.AppUser;
import es.mdef.clientmanager.domain.Password;
import es.mdef.clientmanager.repository.AppUserRepository;
import es.mdef.clientmanager.security.EncryptorService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 1/07/14
 * Time: 0:06
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/test-context.xml" })
public class AppUserTest {
    @Autowired
    public EncryptorService encryptorService;

    @Autowired
    public AppUserRepository appUserRepository;

    @Test
    @Transactional
    public void recoverAppUser(){
        List<AppUser> appUserList=appUserRepository.findAll();
        Assert.notEmpty(appUserList);
    }
    @Test
    public void addUser(){
        AppUser appUser=new AppUser();
        appUser.setLogin("jonsurbe");
        appUser.setUserName("Javier Onsurbe");
        Password password=new Password();
        password.setValue("temporal");
        appUser.setPassword(password);
        appUserRepository.save(appUser);

    }
}
