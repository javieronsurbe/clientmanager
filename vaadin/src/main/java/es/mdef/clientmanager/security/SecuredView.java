package es.mdef.clientmanager.security;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: jonsurbe
 * Date: 9/06/14
 * Time: 17:23
 */
public abstract class SecuredView extends VerticalLayout implements View {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        removeAllComponents();


        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        if (authentication != null && authentication.isAuthenticated()&& !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            //TODO Chequear que se tienen las autorizaciones adecuadas (positivas en lugar de negativas)
            UserDetail userDetail=(UserDetail)authentication.getDetails();

            Label labelLogin = new Label(userDetail.getAppUser().getUserName());
            labelLogin.addStyleName("h1");
            onEnter(event);
        } else {
            removeAllComponents();
            LoginWindow loginWindow=new LoginWindow();
            loginWindow.center();
            this.getUI().addWindow(loginWindow);
       }

    }

    public abstract void onEnter(ViewChangeListener.ViewChangeEvent event);
}
