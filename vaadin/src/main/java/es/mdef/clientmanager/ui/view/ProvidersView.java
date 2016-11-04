package es.mdef.clientmanager.ui.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalLayout;
import es.mdef.clientmanager.security.SecuredView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 5/03/15
 * Time: 23:45
 */
@Configurable(preConstruction = true)
public class ProvidersView extends SecuredView implements View {

    final Logger LOG = LoggerFactory.getLogger(ProvidersView.class);

    private HorizontalLayout horizontalLayout=new HorizontalLayout();



    private void init(){
        addComponent(horizontalLayout);
        setSizeFull();


    }


     @Override
    public void onEnter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }
}
