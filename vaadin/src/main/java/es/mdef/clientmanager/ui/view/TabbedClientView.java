package es.mdef.clientmanager.ui.view;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import es.mdef.clientmanager.domain.Client;
import es.mdef.clientmanager.domain.WebAccount;
import es.mdef.clientmanager.repository.ClientRepository;
import es.mdef.clientmanager.repository.WebAccountRepository;
import es.mdef.clientmanager.security.SecuredView;
import es.mdef.clientmanager.ui.GestionClientesUI;
import es.mdef.clientmanager.ui.component.*;
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
public class TabbedClientView extends SecuredView {

    static final Logger LOG = LoggerFactory.getLogger(TabbedClientView.class);

    @Autowired
    private WebAccountRepository webAccountRepository;

    @Autowired
    private ClientRepository clientRepository;

    private WebAccount webAccount;

    private Client client;


    public TabbedClientView(){

        //setSizeUndefined();

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
        }else{
            webAccount=new WebAccount();
            client=new Client();
            webAccount.setClient(client);
        }
        Layout layout=getButtonsLayout();
        addComponent(layout);

        setComponentAlignment(layout, Alignment.TOP_CENTER);

        TabSheet tabSheet=getTabSheet();
        addComponent(tabSheet);
        setExpandRatio(tabSheet,1);
    }
    private MenuBar getMenuBar(){
        MenuBar menuBar=new MenuBar();
        menuBar.setWidth("100%");
        MenuBar.MenuItem guardar = menuBar.addItem("File",new ThemeResource("icons/OK.svg"), new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {

                clientRepository.save(client);
                webAccountRepository.save(webAccount);
                getUI().getNavigator().navigateTo(GestionClientesUI.CLIENT_LIST_VIEW);
            }
        });

        return menuBar;
    }
    private Layout getButtonsLayout(){
        HorizontalLayout buttonsLayout=new HorizontalLayout();

        buttonsLayout.setStyleName("toolbar");
        buttonsLayout.setHeight(50, Unit.PIXELS);

        Button saveButton=new Button("Guardar");
        saveButton.setStyleName("big default");
        saveButton.setIcon(new ThemeResource("icons/OK.svg"));
        saveButton.setDescription("Guardar");
        Button cancelButton=new Button("Cancelar");
        cancelButton.setStyleName("big default");
        cancelButton.setIcon(new ThemeResource("icons/cancelar.svg"));
        cancelButton.setDescription("Cancelar");

        //TODO Poner los tooltips con rich text https://vaadin.com/book/-/page/components.features.html
        saveButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                clientRepository.save(client);
                webAccountRepository.save(webAccount);
                getUI().getNavigator().navigateTo(GestionClientesUI.CLIENT_LIST_VIEW);

            }
        });
        cancelButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(GestionClientesUI.CLIENT_LIST_VIEW);
            }
        });

        buttonsLayout.addComponent(saveButton);
        buttonsLayout.addComponent(cancelButton);

        return buttonsLayout;
    }

     private TabSheet getTabSheet() {

        TabSheet ts = new TabSheet();
         ts.setSizeFull();
        //ts.setSizeFull();
        ts.addStyleName("framed compact-tabbar equal-width-tabs");

        TabSheet.Tab t = ts.addTab(new WebDomainComponent(webAccount,client), "Cliente");
        t.setClosable(false);
        t.setEnabled(true);

        t = ts.addTab(new ContactInfoTable(client), "Contacto");
        t.setClosable(false);
        t.setEnabled(true);

        t = ts.addTab(new DomainTable(webAccount), "Dominios");
        t.setClosable(false);
        t.setEnabled(true);

        t = ts.addTab(new UsersTable(webAccount), "Cuentas");
        t.setClosable(false);
        t.setEnabled(true);

        if(client.getId()!=null){
            t = ts.addTab(new FilesComponent(client), "Adjuntos");
            t.setClosable(false);
            t.setEnabled(true);
        }

        t = ts.addTab(new Label("En contrucción"), "RRSS");
        t.setClosable(false);
        t.setEnabled(true);
        //t.setIcon();

        ts.addSelectedTabChangeListener(new TabSheet.SelectedTabChangeListener() {
            @Override
            public void selectedTabChange(TabSheet.SelectedTabChangeEvent event) {
                try {
                    //TODO Aquí ira la grabación
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        return ts;
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
