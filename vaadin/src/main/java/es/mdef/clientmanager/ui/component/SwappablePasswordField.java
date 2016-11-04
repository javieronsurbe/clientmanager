package es.mdef.clientmanager.ui.component;

import com.vaadin.data.Property;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.*;
import es.mdef.clientmanager.domain.Password;
import es.mdef.clientmanager.security.EncryptorService;
import es.mdef.clientmanager.security.EncryptorServiceImpl;
import es.mdef.clientmanager.ui.util.StringToPasswordConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * User: jonsurbe
 * Date: 13/05/14
 * Time: 15:37
 */
@Configurable(preConstruction = true)
public class SwappablePasswordField extends CustomField {
    static final Logger LOG = LoggerFactory.getLogger(SwappablePasswordField.class);

    private AbstractTextField  passwordField=new PasswordField();
    private Button toggleButton = new Button("Ver");
    private Boolean showPassword=false;
    private Layout layout= new HorizontalLayout();


    private EncryptorService encryptorServiceImpl=new EncryptorServiceImpl();


    @Override
    protected Component initContent() {

        toggleButton.setIcon(new ThemeResource("icons/ojo.svg"));
        toggleButton.setStyleName("default icon-only");
        toggleButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                showPassword = !showPassword;
                passwordField = getPasswordField();
                layout.removeAllComponents();
                layout.addComponent(passwordField);
                layout.addComponent(toggleButton);
                if (showPassword) {
                    toggleButton.addStyleName("down");
                } else {
                    toggleButton.removeStyleName("down");
                }

                LOG.debug("click");
            }
        });
        passwordField=getPasswordField();
        layout.addComponent(passwordField);
        layout.addComponent(toggleButton);

        return layout;
    }
    public AbstractTextField getPasswordField(){
        AbstractTextField  tempField;
        if(showPassword){
            tempField=new TextField();
        }else{
            tempField=new PasswordField();
        }
        tempField.setNullRepresentation("");
        Password password= (Password) getPropertyDataSource().getValue();
        if(password!=null){
            tempField.setValue(encryptorServiceImpl.decryptString(password.getValue()));
        }else {
            tempField.setValue("");
            getPropertyDataSource().setValue(new Password());
        }
        tempField.setConverter(new StringToPasswordConverter());
        tempField.addValueChangeListener(new ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Password password= (Password) getPropertyDataSource().getValue();
                password.setValue(encryptorServiceImpl.encryptString(passwordField.getValue()));
                LOG.debug("value change="+passwordField.getValue());
                LOG.debug("value change="+getPropertyDataSource().getValue());
            }
        });
        return tempField;
    }
    @Override
    public Class getType() {
        return Password.class;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public EncryptorService getEncryptorServiceImpl() {
        return encryptorServiceImpl;
    }

    public void setEncryptorServiceImpl(EncryptorService encryptorServiceImpl) {
        this.encryptorServiceImpl = encryptorServiceImpl;
    }
}
