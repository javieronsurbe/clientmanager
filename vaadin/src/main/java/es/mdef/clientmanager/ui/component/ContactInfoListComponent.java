package es.mdef.clientmanager.ui.component;

import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import es.mdef.clientmanager.domain.ContactInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * User: jonsurbe
 * Date: 23/04/14
 * Time: 11:27
 */
public class ContactInfoListComponent extends VerticalLayout {
    private static final Logger LOG = LoggerFactory.getLogger(ContactInfoListComponent.class);

    private VerticalLayout contactInfoListLayout=new VerticalLayout();

    private final List<ContactInfo> contactInfoList;

    public ContactInfoListComponent(List<ContactInfo> contactInfoList){
        super();
        this.contactInfoList=contactInfoList;
        if(contactInfoList.size()==0){
            initContactInfoList(contactInfoList);
        }
        setSizeFull();
        setSpacing(true);
        contactInfoListLayout.setSizeFull();
        contactInfoListLayout.setSpacing(true);


        for(final ContactInfo contactInfo:contactInfoList){
            ContactInfoComponent contactInfoComponent=new ContactInfoComponent(contactInfo);
            contactInfoListLayout.addComponent(contactInfoComponent);
            Button deleteButton=new Button("Delete");
            deleteButton.setIcon(new ThemeResource("icons/borrar.svg"));
            deleteButton.setStyleName("default icon-only");

            deleteButton.addClickListener(new DeleteListener(contactInfoComponent));
            contactInfoComponent.addComponent(deleteButton);
        }
        this.addComponent(contactInfoListLayout);
        Button editButton=new Button("Aniadir");
        editButton.setIcon(new ThemeResource("icons/aniadir.svg"));
        editButton.setStyleName("default icon-only");
        editButton.addClickListener(new NewListener());
        addComponent(editButton);
    }

    private void initContactInfoList(List<ContactInfo> contactInfoList) {
        contactInfoList.add(new ContactInfo("", "", null));
    }

    private class DeleteListener implements Button.ClickListener{
        private ContactInfoComponent contactInfoComponent;
        public DeleteListener(ContactInfoComponent contactInfoComponent){
            this.contactInfoComponent=contactInfoComponent;
        }
        @Override
        public void buttonClick(Button.ClickEvent event) {
            LOG.debug("borrar click="+contactInfoComponent.getContactInfo().getId());
            //contactInfo.remove();
            contactInfoList.remove(contactInfoComponent.getContactInfo());
            contactInfoListLayout.removeComponent(contactInfoComponent);

        }
    }
    private class NewListener implements Button.ClickListener{
        @Override
        public void buttonClick(Button.ClickEvent event) {
            ContactInfo newContactInfo=new ContactInfo("", "", null);
            contactInfoList.add(newContactInfo);
            ContactInfoComponent contactInfoComponent=new ContactInfoComponent(newContactInfo);
            Button deleteButton=new Button("Delete");
            deleteButton.setIcon(new ThemeResource("icons/borrar.svg"));
            deleteButton.setStyleName("default icon-only");

            deleteButton.addClickListener(new DeleteListener(contactInfoComponent));
            contactInfoComponent.addComponent(deleteButton);
            contactInfoListLayout.addComponent(contactInfoComponent);
        }
    }
}
