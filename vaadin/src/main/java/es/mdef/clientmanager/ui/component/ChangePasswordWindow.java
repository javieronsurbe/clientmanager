package es.mdef.clientmanager.ui.component;

import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import es.mdef.clientmanager.domain.AppUser;
import es.mdef.clientmanager.repository.AppUserRepository;
import es.mdef.clientmanager.security.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 21/03/15
 * Time: 18:55
 */
@Configurable(preConstruction = true)
public class ChangePasswordWindow extends Window{
    private VerticalLayout changePasswordLayout=new VerticalLayout();

    private PasswordField originalPasswordField=new PasswordField("Antigua contaseña");
    private PasswordField newPasswordField=new PasswordField("Nueva contraseña");
    private Button btnConfirmar = new Button("Confirmar");

    @Autowired
    private AppUserRepository appUserRepository;

    public ChangePasswordWindow(){
        super();
        changePasswordLayout.addComponent(originalPasswordField);
        changePasswordLayout.addComponent(newPasswordField);
        changePasswordLayout.addComponent(btnConfirmar);
        changePasswordLayout.setMargin(true);
        btnConfirmar.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                SecurityContext context = SecurityContextHolder.getContext();
                Authentication authentication = context.getAuthentication();

                if (authentication != null && authentication.isAuthenticated()&& !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
                    UserDetail userDetail = (UserDetail) authentication.getDetails();
                    AppUser appUser=userDetail.getAppUser();
                    if(appUser.getPassword().getValue().equals(originalPasswordField.getValue())){
                        appUser.getPassword().setValue(newPasswordField.getValue());
                        appUserRepository.save(appUser);
                    }
                }
            }
        });
        this.setContent(changePasswordLayout);
        setResizable(false);
        setModal(true);
        setClosable(true);
    }

    public AppUserRepository getAppUserRepository() {
        return appUserRepository;
    }

    public void setAppUserRepository(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }
}
