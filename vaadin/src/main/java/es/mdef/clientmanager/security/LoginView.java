package es.mdef.clientmanager.security;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.VerticalLayout;

/**
 * User: jonsurbe
 * Date: 9/06/14
 * Time: 17:18
 */
@Deprecated
public class LoginView extends VerticalLayout implements View {
    public LoginView() {

        LoginForm loginForm = new LoginForm();
        addComponent(loginForm);
    }
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
