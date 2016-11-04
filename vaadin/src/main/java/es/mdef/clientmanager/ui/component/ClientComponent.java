package es.mdef.clientmanager.ui.component;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Panel;
import es.mdef.clientmanager.domain.Client;
import es.mdef.clientmanager.domain.WebAccount;

/**
 * User: jonsurbe
 * Date: 3/04/14
 * Time: 15:12
 */
public class ClientComponent extends HorizontalLayout {

    private WebDomainComponent webDomainComponent;
    private ContactInfoListComponent contactInfoListComponent;

    public ClientComponent(WebAccount webAccount, Client client){
        super();
        setSizeFull();
        setWidth(100, Unit.PERCENTAGE);
        webDomainComponent=new WebDomainComponent(webAccount,client);
        contactInfoListComponent=new ContactInfoListComponent(client.getContactInfoList());
        Panel webDomainComponentPanel=new Panel(webDomainComponent);
        webDomainComponentPanel.setSizeFull();
        addComponent(webDomainComponentPanel);
        Panel contactInfoListPanel=new Panel(contactInfoListComponent);
        contactInfoListPanel.setSizeFull();
        addComponent(contactInfoListPanel);
    }


 }
