package es.mdef.clientmanager.security;

import com.vaadin.navigator.Navigator;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * User: jonsurbe
 * Date: 9/06/14
 * Time: 17:29
 */
public class LogoutListener implements Button.ClickListener {
    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        SecurityContextHolder.clearContext();

        //UI.getCurrent().close();
        Navigator navigator = UI.getCurrent().getNavigator();
        navigator.navigateTo("");
    }
}
