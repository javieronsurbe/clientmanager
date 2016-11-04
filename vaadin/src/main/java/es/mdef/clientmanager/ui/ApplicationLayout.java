package es.mdef.clientmanager.ui;

import com.vaadin.ui.*;

/**
 * User: jonsurbe
 * Date: 20/11/14
 * Time: 11:51
 */
public class ApplicationLayout extends HorizontalLayout {

    VerticalLayout contentArea = new VerticalLayout();

    CssLayout menuArea = new CssLayout();

    public ApplicationLayout(){
        setSizeFull();

        menuArea.setPrimaryStyleName("valo-menu");

        contentArea.setPrimaryStyleName("valo-content");
        contentArea.addStyleName("v-scrollable");
        contentArea.setSizeFull();

        addComponents(menuArea, contentArea);
        setExpandRatio(contentArea, 1);
    }
    public ComponentContainer getContentContainer() {
        return contentArea;
    }

    public void addMenu(Component menu) {
        menu.addStyleName("valo-menu-part");
        menuArea.addComponent(menu);
    }
}
