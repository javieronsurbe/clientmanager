package es.mdef.clientmanager.security;

import com.vaadin.ui.Button;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 9/06/14
 * Time: 0:16
 */
@Configurable(preConstruction = true)
public class LoginForm extends VerticalLayout {
    private TextField txtLogin = new TextField("Login: ");
    private PasswordField txtPassword = new PasswordField("Password: ");
    private Button btnLogin = new Button("Login");


    @Autowired
    private LoginFormListener loginFormListener;

    public LoginForm() {
        txtLogin.focus();
        addComponent(txtLogin);
        addComponent(txtPassword);
        addComponent(btnLogin);
        btnLogin.addClickListener(loginFormListener);
    }

    public TextField getTxtLogin() {
        return txtLogin;
    }
    public PasswordField getTxtPassword() {
        return txtPassword;
    }
}
