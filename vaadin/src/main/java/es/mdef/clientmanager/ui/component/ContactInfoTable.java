package es.mdef.clientmanager.ui.component;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import es.mdef.clientmanager.domain.Client;
import es.mdef.clientmanager.domain.ContactInfo;
import es.mdef.clientmanager.ui.util.TableFieldFactory;

import java.util.ArrayList;

/**
 * User: jonsurbe
 * Date: 3/04/14
 * Time: 15:12
 */
public class ContactInfoTable extends VerticalLayout {
    private Table contactInfoTable=new Table();
    private Button editButton;

    public ContactInfoTable(final Client client){
        super();
        setSizeFull();
        if(client.getContactInfoList()==null){
            client.setContactInfoList(new ArrayList<ContactInfo>());
        }

        contactInfoTable.setEditable(true);
        contactInfoTable.setSelectable(true);
        contactInfoTable.setWidth(100, Unit.PERCENTAGE);
        contactInfoTable.setHeight(600, Unit.PIXELS);

        contactInfoTable.setTableFieldFactory(TableFieldFactory.getInstance());
        contactInfoTable.setContainerDataSource(getIndexedContainer(client));

        contactInfoTable.setVisibleColumns(new String[]{"type", "value", "description"});
        contactInfoTable.setColumnHeader("type", "Tipo");
        contactInfoTable.setColumnHeader("value", "Valor");
        contactInfoTable.setColumnHeader("description", "Descriptición");

        contactInfoTable.addGeneratedColumn("", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table table, final Object itemId, Object columnId) {
                Button deleteButton = new Button("Delete");


                deleteButton.setIcon(new ThemeResource("icons/borrar.svg"));
                deleteButton.setStyleName("default icon-only");
                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        client.getContactInfoList().remove(itemId);
                        table.getContainerDataSource().removeItem(itemId);
                    }
                });
                return deleteButton;
            }
        });
        addComponent(contactInfoTable);
        Button editButton=new Button("Aniadir");
        editButton.setIcon(new ThemeResource("icons/aniadir.svg"));
        editButton.setStyleName("default icon-only");
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ContactInfo contactInfo=new ContactInfo();
                client.getContactInfoList().add(contactInfo);
                addContactInfo(contactInfo);
            }
        });
        addComponent(editButton);
        setComponentAlignment(editButton, Alignment.BOTTOM_LEFT);

    }
    private Container getIndexedContainer(Client client){
        BeanItemContainer<ContactInfo> container=new BeanItemContainer<ContactInfo>(ContactInfo.class);
        container.addAll(client.getContactInfoList());
        return  container;
    }
    public void addContactInfo(ContactInfo contactInfo){
        this.contactInfoTable.getContainerDataSource().addItem(contactInfo);

    }
}
