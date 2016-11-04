/*
 * Copyright 2009 IT Mill Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package es.mdef.clientmanager.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import es.mdef.clientmanager.security.UserDetail;
import es.mdef.clientmanager.security.UserService;
import es.mdef.clientmanager.ui.component.ChangePasswordWindow;
import es.mdef.clientmanager.ui.util.ConverterFactory;
import es.mdef.clientmanager.ui.view.BudgetView;
import es.mdef.clientmanager.ui.view.ClientListView;
import es.mdef.clientmanager.ui.view.ProvidersView;
import es.mdef.clientmanager.ui.view.TabbedClientView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
@Configurable(preConstruction = true)
@Theme("tests-valo-flat")
@Title("Gestión de clientes")
public class GestionClientesUI extends UI  {


//    private Layout content=new VerticalLayout();

    public static String CLIENT_LIST_VIEW="/clientList";
    public static String CLIENT_VIEW="/client";
    public static String BUDGETS_VIEW="/budgets";
    public static String PROVIDERS_VIEW="/providers";

    @Autowired
    private UserService userService;

    private ApplicationLayout root=new ApplicationLayout();
    ComponentContainer contentContainer=root.getContentContainer();
    CssLayout menu=new CssLayout();
    CssLayout menuItemsLayout = new CssLayout();
    {
        menu.setId("menu");
    }
    private Navigator navigator;
    private final LinkedHashMap<String, String> menuItems = new LinkedHashMap<String, String>();


    //TODO Configurarlo como un mapa de Spring y meterlo en un servicio
    public static HashMap<String, Class<? extends View>> routes = new HashMap<String, Class<? extends View>>() {
        {
            put("", ClientListView.class);
            put(CLIENT_VIEW, TabbedClientView.class);
            put(CLIENT_LIST_VIEW, ClientListView.class);
            put(BUDGETS_VIEW, BudgetView.class);
            put(PROVIDERS_VIEW, ProvidersView.class);

        }
    };


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VaadinSession.getCurrent().setConverterFactory(new ConverterFactory());
        setContent(root);
        root.setSizeFull();
        root.addMenu(buildMenu());
        buildNavigator();
    }

    private void buildNavigator(){
        // navigator.setErrorView(CommonParts.class);
        navigator = new Navigator(this, contentContainer);

        for (String route : routes.keySet()) {
            navigator.addView(route, routes.get(route));
        }
    }
    CssLayout buildMenu() {
        // Add items
        menuItems.put("common", "Common UI Elements");
        menu.setSizeFull();
        menu.addComponent(getMenuTitleComponent());

        final MenuBar settings = new MenuBar();
        settings.addStyleName("user-menu");

        // TODO Actualizar nombre de usuario cuando se logee con exito
        final MenuBar.MenuItem userMenuItem = settings.addItem(getNombreUsuario(),
                new ThemeResource("icons/usuario.svg"),
                null);

        userMenuItem.addItem("Cambiar contraseña", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                ChangePasswordWindow changePasswordWindow=new ChangePasswordWindow();
                changePasswordWindow.center();
                addWindow(changePasswordWindow);
            }
        });
        userMenuItem.addSeparator();
        userMenuItem.addItem("Salir", new MenuBar.Command() {
            @Override
            public void menuSelected(MenuBar.MenuItem selectedItem) {
                SecurityContextHolder.clearContext();

                //UI.getCurrent().close();
                Navigator navigator = UI.getCurrent().getNavigator();
                navigator.navigateTo("");
            }
        });
        menu.addComponent(settings);

        menuItemsLayout.setPrimaryStyleName("valo-menuitems");
        menu.addComponent(menuItemsLayout);


        final Button clientesButton = new Button("Clientes", new Button.ClickListener() {
                @Override
                public void buttonClick(final Button.ClickEvent event) {
                    navigator.navigateTo(CLIENT_LIST_VIEW);
                }
            });


        clientesButton.setHtmlContentAllowed(true);
        clientesButton.setPrimaryStyleName("valo-menu-item");
        clientesButton.setIcon(new ThemeResource("icons/clientes2.svg"));
        menuItemsLayout.addComponent(clientesButton);

        final Button budgetButton = new Button("Presupuesto", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                navigator.navigateTo(BUDGETS_VIEW);
            }
        });


        budgetButton.setHtmlContentAllowed(true);
        budgetButton.setPrimaryStyleName("valo-menu-item");
        budgetButton.setIcon(new ThemeResource("icons/money.svg"));
        menuItemsLayout.addComponent(budgetButton);

        final Button subscripcionButton = new Button("Suscripciones", new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                navigator.navigateTo(PROVIDERS_VIEW);
            }
        });


        subscripcionButton.setHtmlContentAllowed(true);
        subscripcionButton.setPrimaryStyleName("valo-menu-item");
        subscripcionButton.setIcon(new ThemeResource("icons/suscripciones.svg"));
        menuItemsLayout.addComponent(subscripcionButton);

        return menu;
    }

    private Component getMenuTitleComponent(){

        final HorizontalLayout top = new HorizontalLayout();
        top.setWidth("100%");
        top.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);
        top.addStyleName("valo-menu-title");

        final Label title = new Label(
                "<h3><strong>Gesti&oacute;n de clientes</strong></h3>", ContentMode.HTML);
        title.setSizeUndefined();
        top.addComponent(title);
        top.setExpandRatio(title, 1);
        return top;
    }
    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    private String getNombreUsuario(){
        String nombre="";
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        if (authentication != null && authentication.isAuthenticated()&& !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
            UserDetail userDetail=(UserDetail)authentication.getDetails();
            nombre=userDetail.getAppUser().getUserName();
        }
        return nombre;
    }
}
