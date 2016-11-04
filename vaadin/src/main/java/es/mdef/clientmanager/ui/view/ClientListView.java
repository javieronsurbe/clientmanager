package es.mdef.clientmanager.ui.view;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingMutableLocalEntityProvider;
import com.vaadin.data.Container;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import es.mdef.clientmanager.domain.WebAccount;
import es.mdef.clientmanager.domain.WebDomain;
import es.mdef.clientmanager.repository.ClientRepository;
import es.mdef.clientmanager.repository.WebAccountRepository;
import es.mdef.clientmanager.security.SecuredView;
import es.mdef.clientmanager.ui.GestionClientesUI;
import es.mdef.clientmanager.ui.util.PagedTable;
import es.mdef.clientmanager.ui.util.StringToDateConverter;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

/**
 * User: jonsurbe
 * Date: 2/04/14
 * Time: 10:23
 */
@Configurable(preConstruction = true)
public class ClientListView extends SecuredView implements View {

    private PagedTable contactList;

    @Autowired
    private WebAccountRepository webAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private void init(){

        setSizeFull();
        initContactList();

        this.addComponent(getTablePanel());
    }
    public ClientListView(){
        //init();
    }


    private Container getIndexedContainer() {

        CachingMutableLocalEntityProvider entityProvider=new CachingMutableLocalEntityProvider<WebAccount>(WebAccount.class,entityManager);
        JPAContainer<WebAccount> webAccountContainer=new JPAContainer<WebAccount> (WebAccount.class);
        webAccountContainer.addNestedContainerProperty("client.name");
        webAccountContainer.addNestedContainerProperty("client.address");
        webAccountContainer.setEntityProvider(entityProvider);
        return webAccountContainer;
    }

    private Component getTablePanel(){
        VerticalLayout tableLayout=new VerticalLayout();
        tableLayout.addComponent(contactList);
        tableLayout.addComponent(contactList.createControls());
        return tableLayout;
    }

    private void initContactList() {
        contactList=new PagedTable("", getIndexedContainer());
        contactList.setWidth(100, Unit.PERCENTAGE);
        contactList.setHeight(600, Unit.PIXELS);
        contactList.setPageLength(10);

        contactList.setColumnHeader("client.name", "Nombre");
        contactList.setColumnHeader("client.address", "Dirección");
        contactList.setColumnHeader("name", "Web");
        contactList.setColumnHeader("creationDate", "Fecha de creación");
        contactList.setColumnHeader("contractExpirationDate", "Fin de contrato");


        contactList.setConverter("creationDate", new StringToDateConverter());
        contactList.setConverter("contractExpirationDate", new StringToDateConverter());

        contactList.setVisibleColumns(new String[]{"client.name", "client.address", "name", "creationDate",
                "contractExpirationDate"});

        contactList.setSelectable(false);
        contactList.addGeneratedColumn("", new Table.ColumnGenerator() {
            @Override
            public Object generateCell(Table table, final Object itemId, Object columnId) {
                HorizontalLayout botonesLayout = new HorizontalLayout();

                Button editButton = new Button("Edit");
                editButton.setIcon(new ThemeResource("icons/lapiz.svg"));
                editButton.setStyleName("default icon-only");

                editButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        System.out.println("editar click=" + itemId);
                        getUI().getNavigator().navigateTo(GestionClientesUI.CLIENT_VIEW + "/" + itemId);
                    }
                });
                Button deleteButton = new Button("Delete");
                deleteButton.setIcon(new ThemeResource("icons/borrar.svg"));
                deleteButton.setStyleName("default icon-only");

                deleteButton.addClickListener(new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        System.out.println("borrar click=" + itemId);
                        WebAccount webAccount = webAccountRepository.findOne((Long) itemId);
                        clientRepository.delete(webAccount.getClient());
                        webAccountRepository.delete(webAccount);
                        contactList.setContainerDataSource(getIndexedContainer());

                    }
                });

                botonesLayout.addComponent(editButton);
                botonesLayout.addComponent(deleteButton);

                return botonesLayout;
            }
        });

        contactList.setCellStyleGenerator(new Table.CellStyleGenerator() {
            @Override
            public String getStyle(Table source, Object itemId, Object propertyId) {
                WebAccount webAccount = webAccountRepository.findOne((Long) itemId);
                List<WebDomain> webDomainList = webAccount.getDomains();
                for (WebDomain webDomain : webDomainList) {
                    Date date = new Date();
                    if (webDomain.getExpirationDate() != null && DateUtils.addMonths(date, 2).after(webDomain.getExpirationDate())) {
                        return "v-warning";
                    }

                }
                return null;
            }
        });

    }
    @Override
    public void onEnter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }

    public WebAccountRepository getWebAccountRepository() {
        return webAccountRepository;
    }

    public void setWebAccountRepository(WebAccountRepository webAccountRepository) {
        this.webAccountRepository = webAccountRepository;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
