package es.mdef.clientmanager.security;

import com.vaadin.ui.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 19/06/14
 * Time: 23:29
 */
@Configurable(preConstruction = true)
public class LoginWindow extends Window {
    private VerticalLayout loginLayout=new VerticalLayout();

    public TextField getTxtLogin() {
        return txtLogin;
    }

    public void setTxtLogin(TextField txtLogin) {
        this.txtLogin = txtLogin;
    }

    public PasswordField getTxtPassword() {
        return txtPassword;
    }

    public void setTxtPassword(PasswordField txtPassword) {
        this.txtPassword = txtPassword;
    }

    private TextField txtLogin = new TextField("Login: ");
    private PasswordField txtPassword = new PasswordField("Password: ");
    private Button btnLogin = new Button("Login");


    @Autowired
    private LoginFormListener loginFormListener;

    public LoginWindow(){
        loginLayout.addComponent(txtLogin);
        loginLayout.addComponent(txtPassword);
        loginLayout.addComponent(btnLogin);
        loginLayout.setMargin(true);
        btnLogin.addClickListener(loginFormListener);
        this.setContent(loginLayout);
        setResizable(false);
        setModal(true);
        setClosable(false);
    }

    public LoginFormListener getLoginFormListener() {
        return loginFormListener;
    }

    public void setLoginFormListener(LoginFormListener loginFormListener) {
        this.loginFormListener = loginFormListener;
    }
}
