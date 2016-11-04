package es.mdef.clientmanager.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import es.mdef.clientmanager.domain.*;
import es.mdef.clientmanager.repository.ClientRepository;
import es.mdef.clientmanager.repository.WebAccountRepository;
import es.mdef.clientmanager.security.SecuredView;
import es.mdef.clientmanager.ui.GestionClientesUI;
import es.mdef.clientmanager.ui.component.*;
import es.mdef.clientmanager.ui.util.FileDropBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;



/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 2/04/14
 * Time: 0:21
 */
@Configurable(preConstruction = true)
public class ClientView extends SecuredView {

    static final Logger LOG = LoggerFactory.getLogger(ClientView.class);


    private WebAccount webAccount;

    private Client client;

    private DomainTable domainTable;

    private UsersTable usersTable;

    @Autowired
    private WebAccountRepository webAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    public ClientView(){

        setSizeUndefined();
        //fileDropBox=new FileDropBox(this, null);
    }

    @Override
    @Transactional
    public void onEnter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        setSizeFull();
        String[] parameters=viewChangeEvent.getParameters().split("/");
        String webAccountId;

        if(parameters[0]!=null&&parameters[0].length()>0){
            webAccountId=parameters[0];
            webAccount=webAccountRepository.findOne(new Long(webAccountId));
            client=webAccount.getClient();
            LOG.debug("Contact Info List="+client.getContactInfoList());
        }else{
            webAccount=new WebAccount();
            client=new Client();
            webAccount.setClient(client);
        }
    }
    private Component getButtonsLayout(){
        CssLayout buttonsLayout=new CssLayout();
        buttonsLayout.setStyleName("toolbar");

        buttonsLayout.setSizeUndefined();
        buttonsLayout.setWidth(100, Unit.PERCENTAGE);

        Button saveButton=new Button("Guardar");
        saveButton.setStyleName("big default");
        saveButton.setIcon(new ThemeResource("icons/OK.svg"));
        saveButton.setDescription("Guardar");
        Button cancelButton=new Button("Cancelar");
        cancelButton.setStyleName("big default");
        cancelButton.setIcon(new ThemeResource("icons/cancelar.svg"));
        cancelButton.setDescription("Cancelar");
        Button attachButton=new Button("Adjuntos");
        attachButton.setStyleName("big default");
        attachButton.setIcon(new ThemeResource("icons/adjunto.svg"));
        attachButton.setDescription("Adjuntar");
        //TODO Poner los tooltips con rich text https://vaadin.com/book/-/page/components.features.html
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {

                clientRepository.save(client);
                webAccountRepository.save(webAccount);
/*
                if(client.getId()==null){
                    client.persist();
                }else{
                    client.merge();
                }
                if(webAccount.getId()==null){
                    webAccount.persist();
                }else{
                    webAccount.merge();
                }
*/
                getUI().getNavigator().navigateTo(GestionClientesUI.CLIENT_LIST_VIEW);

            }
        });
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(GestionClientesUI.CLIENT_LIST_VIEW);
            }
        });
        attachButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Window window=  new Window("Adjuntos", new FileDropBox(new FilesComponent(client)));
                window.center();
                window.setModal(true);
                getUI().addWindow(window);
            }
        });

        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);
        buttonsLayout.addComponent(attachButton);

        return buttonsLayout;
    }

    private Component getClienteEditionLayout(final WebAccount webAccount){
        HorizontalLayout clientEditionLayout=new HorizontalLayout();
        clientEditionLayout.setWidth(100, Unit.PERCENTAGE);
        VerticalLayout leftLayout =new VerticalLayout();
        VerticalLayout rightLayout = new VerticalLayout();

        leftLayout.addComponent(new Panel("Cliente", new WebDomainComponent(webAccount,client)));
        leftLayout.addComponent(new Panel("Información de contacto",new ContactInfoListComponent(client.getContactInfoList())));
        clientEditionLayout.addComponent(leftLayout);

        domainTable=new DomainTable(webAccount);
//        domainTable.addGeneratedColumn("", new Table.ColumnGenerator() {
//            @Override
//            public Object generateCell(final Table table, final Object itemId, Object columnId) {
//                Button deleteButton = new Button("Delete");
//
//
//                deleteButton.setIcon(new ThemeResource("icons/borrar.svg"));
//                deleteButton.setStyleName("default icon-only");
//                deleteButton.addClickListener(new Button.ClickListener() {
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        webAccount.getDomains().remove(itemId);
//                        table.getContainerDataSource().removeItem(itemId);
//
//                    }
//                });
//                return deleteButton;
//            }
//        });
        rightLayout.addComponent(new Panel("Dominios",domainTable));
        Button editButton=new Button("Aniadir");
        editButton.setIcon(new ThemeResource("icons/aniadir.svg"));
        editButton.setStyleName("default icon-only");
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            @Transactional
            public void buttonClick(Button.ClickEvent event) {
                WebDomain domain=new WebDomain();
                webAccount.getDomains().add(domain);
                domainTable.addDomain(domain);
            }
        });
        rightLayout.addComponent(editButton);
        usersTable = new UsersTable(webAccount);
//        usersTable.addGeneratedColumn("", new Table.ColumnGenerator() {
//            @Override
//            public Object generateCell(final Table table, final Object itemId, Object columnId) {
//                Button deleteButton = new Button("Edit");
//
//
//                deleteButton.setIcon(new ThemeResource("icons/borrar.svg"));
//                deleteButton.setStyleName("default icon-only");
//
//                deleteButton.addClickListener(new Button.ClickListener() {
//                    @Override
//                    public void buttonClick(Button.ClickEvent event) {
//                        webAccount.getUsers().remove(itemId);
//                        table.getContainerDataSource().removeItem(itemId);
//                    }
//                });
//                return deleteButton;
//            }
//        });

        rightLayout.addComponent(new Panel("Usuarios",usersTable));
        editButton=new Button("Aniadir");
        editButton.setIcon(new ThemeResource("icons/aniadir.svg"));
        editButton.setStyleName("default icon-only");
        editButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                Users user=new Users();
                user.setPassword(new Password());
                webAccount.getUsers().add(user);
                usersTable.addUser(user);
            }
        });
        rightLayout.addComponent(editButton);

        clientEditionLayout.addComponent(rightLayout);
        return clientEditionLayout;
    }

    public WebAccountRepository getWebAccountRepository() {
        return webAccountRepository;
    }

    public void setWebAccountRepository(WebAccountRepository webAccountRepository) {
        this.webAccountRepository = webAccountRepository;
    }

    public ClientRepository getClientRepository() {
        return clientRepository;
    }

    public void setClientRepository(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
}
