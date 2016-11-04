package es.mdef.clientmanager.security;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import es.mdef.clientmanager.ui.GestionClientesUI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 9/06/14
 * Time: 0:15
 */
@Service
public class LoginFormListener implements Button.ClickListener {
    @Autowired
    private AuthManager authManager;
    @Override
    public void buttonClick(Button.ClickEvent event) {
        try {
            Button source = event.getButton();

            LoginWindow parent = (LoginWindow) source.getParent().getParent();
            String username = parent.getTxtLogin().getValue();
            String password = parent.getTxtPassword().getValue();
            UsernamePasswordAuthenticationToken request =
                    new UsernamePasswordAuthenticationToken(username, password);
            Authentication result = authManager.authenticate(request);

            SecurityContextHolder.getContext().setAuthentication(result);

            GestionClientesUI current = (GestionClientesUI) UI.getCurrent();
            Navigator navigator = current.getNavigator();
            navigator.navigateTo(GestionClientesUI.CLIENT_LIST_VIEW);
            UI.getCurrent().removeWindow(parent);
        } catch (AuthenticationException e) {
            Notification.show("Authentication failed: " + e.getLocalizedMessage(), Notification.Type.WARNING_MESSAGE);
        }
    }
}
