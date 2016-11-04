package es.mdef.clientmanager.ui.component;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import es.mdef.clientmanager.domain.Password;
import es.mdef.clientmanager.domain.Users;
import es.mdef.clientmanager.domain.WebAccount;
import es.mdef.clientmanager.ui.util.TableFieldFactory;

import java.util.ArrayList;

/**
 * User: jonsurbe
 * Date: 3/04/14
 * Time: 15:12
 */
public class UsersTable extends VerticalLayout {

    private Table usersTable=new Table();
    private Button editButton;

    public UsersTable(final WebAccount webAccount){
        super();
        setSizeFull();
        if(webAccount.getUsers()==null){
            webAccount.setUsers(new ArrayList<Users>());
        }
        usersTable.setEditable(true);
        usersTable.setSelectable(true);
        usersTable.setWidth(100, Unit.PERCENTAGE);
        usersTable.setHeight(600, Unit.PIXELS);

        usersTable.setTableFieldFactory(TableFieldFactory.getInstance());
        usersTable.setContainerDataSource(getIndexedContainer(webAccount));
        usersTable.setVisibleColumns(new String[]{"userType", "identifier", "password"});
        usersTable.setColumnHeader("userType", "Tipo");
        usersTable.setColumnHeader("identifier", "Identificador");
        usersTable.setColumnHeader("password", "Contraseña");
        usersTable.addGeneratedColumn("", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table table, final Object itemId, Object columnId) {
                Button deleteButton = new Button("Delete");


                deleteButton.setIcon(new ThemeResource("icons/borrar.svg"));
                deleteButton.setStyleName("default icon-only");
                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        webAccount.getUsers().remove(itemId);
                        table.getContainerDataSource().removeItem(itemId);
                    }
                });
                return deleteButton;
            }
        });
        addComponent(usersTable);

        Button editButton=new Button("Aniadir");
        editButton.setIcon(new ThemeResource("icons/aniadir.svg"));
        editButton.setStyleName("default icon-only");
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Users user=new Users();
                user.setPassword(new Password());
                webAccount.getUsers().add(user);
                addUser(user);
            }
        });
        addComponent(editButton);
        setComponentAlignment(editButton, Alignment.BOTTOM_LEFT);

    }
    private Container getIndexedContainer(WebAccount webAccount){
        BeanItemContainer<Users> container=new BeanItemContainer<Users>(Users.class);
        container.addAll(webAccount.getUsers());

        return  container;
    }

    public void addUser(Users user){
        this.usersTable.getContainerDataSource().addItem(user);
    }
}
