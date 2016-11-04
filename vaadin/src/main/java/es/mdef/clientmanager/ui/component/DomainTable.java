package es.mdef.clientmanager.ui.component;

import com.vaadin.data.Container;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import es.mdef.clientmanager.domain.WebAccount;
import es.mdef.clientmanager.domain.WebDomain;
import es.mdef.clientmanager.ui.util.StringToDateConverter;
import es.mdef.clientmanager.ui.util.TableFieldFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * User: jonsurbe
 * Date: 3/04/14
 * Time: 15:12
 */
public class DomainTable extends VerticalLayout {

    private Table domainTable=new Table();
    private Button editButton;

    public DomainTable(final WebAccount webAccount){
        super();
        setSizeFull();
        if(webAccount.getDomains()==null){
            webAccount.setDomains(new ArrayList<WebDomain>());
        }

        //setHeight(100, Unit.PERCENTAGE);

        domainTable.setEditable(true);
        domainTable.setSelectable(true);
        domainTable.setWidth(100, Unit.PERCENTAGE);
        domainTable.setHeight(600, Unit.PIXELS);


        domainTable.setTableFieldFactory(TableFieldFactory.getInstance());
        domainTable.setContainerDataSource(getIndexedContainer(webAccount));


        domainTable.setVisibleColumns(new String[]{"domainName", "adminUrl", "database", "managedDomain", "expirationDate"});
        domainTable.setColumnHeader("domainName", "Dominio");
        domainTable.setColumnHeader("adminUrl", "Url de administración");
        domainTable.setColumnHeader("database", "Base de datos");
        domainTable.setColumnHeader("managedDomain", "Gestionado");
        domainTable.setColumnHeader("expirationDate", "Fecha de expiración");

        domainTable.setConverter("expirationDate", new StringToDateConverter());

        domainTable.addGeneratedColumn("", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(final Table table, final Object itemId, Object columnId) {
                Button deleteButton = new Button("Delete");


                deleteButton.setIcon(new ThemeResource("icons/borrar.svg"));
                deleteButton.setStyleName("default icon-only");
                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        webAccount.getDomains().remove(itemId);
                        table.getContainerDataSource().removeItem(itemId);

                    }
                });
                return deleteButton;
            }
        });
        addComponent(domainTable);
        editButton=new Button("Aniadir");
        editButton.setIcon(new ThemeResource("icons/aniadir.svg"));
        editButton.setStyleName("default icon-only");
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            @Transactional
            public void buttonClick(Button.ClickEvent event) {
                WebDomain domain=new WebDomain();
                webAccount.getDomains().add(domain);
                addDomain(domain);
            }
        });


        addComponent(editButton);

        setComponentAlignment(editButton, Alignment.BOTTOM_LEFT);

    }
    private Container getIndexedContainer(WebAccount webAccount){
        BeanItemContainer<WebDomain> container=new BeanItemContainer<WebDomain>(WebDomain.class);
        container.addAll(webAccount.getDomains());

        return  container;
    }
    public void addDomain(WebDomain webDomain){
        this.domainTable.getContainerDataSource().addItem(webDomain);

    }
}
