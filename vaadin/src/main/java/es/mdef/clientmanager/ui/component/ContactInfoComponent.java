package es.mdef.clientmanager.ui.component;

import com.vaadin.data.Property;
import com.vaadin.data.Validator;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import es.mdef.clientmanager.domain.ContactInfo;
import es.mdef.clientmanager.domain.ContactInfoType;
import es.mdef.clientmanager.repository.ContactInfoTypeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * User: jonsurbe
 * Date: 7/04/14
 * Time: 12:38
 */
public class ContactInfoComponent extends HorizontalLayout {
    private static final Logger LOG = LoggerFactory.getLogger(ContactInfoComponent.class);
    private ComboBox typeSelect=new ComboBox();
    private TextField valueTextField=new TextField();
    private TextField descriptionTextField=new TextField();

    private static final String REG_EXP_EMAIL="^([0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*@([0-9a-zA-Z][-\\w]*[0-9a-zA-Z]\\.)+[a-zA-Z]{2,9})";
    private static final String REG_EXP_PHONE="[6789]\\d{8}";
    private ContactInfo contactInfo;

    @Autowired
    private ContactInfoTypeRepository contactInfoTypeRepository;

    public ContactInfoComponent(ContactInfo contactInfo) {
        super();
        setSpacing(true);
        this.contactInfo=contactInfo;
        BeanItem<ContactInfo> contactInfoBeanItem=new BeanItem<ContactInfo>(contactInfo);
        if(contactInfo.getType()!=null){
            typeSelect.setPropertyDataSource(contactInfoBeanItem.getItemProperty("type"));
        }
        if(contactInfo.getValue()!=null){
            valueTextField.setPropertyDataSource(contactInfoBeanItem.getItemProperty("value"));
        }
        if(contactInfo.getDescription()!=null){
            descriptionTextField.setPropertyDataSource(contactInfoBeanItem.getItemProperty("description"));
        }

        typeSelect.setWidth("100");
        valueTextField.addValidator(getValidator(contactInfo.getType()));

        typeSelect.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent valueChangeEvent) {
                valueTextField.removeAllValidators();
                valueTextField.addValidator(getValidator((ContactInfoType) typeSelect.getValue()));
                LOG.debug("Cambia el tipo de contacto");
            }
        });
        valueTextField.setWidth("220");
        List<ContactInfoType> contactInfoTypes=contactInfoTypeRepository.findAll();
        for(ContactInfoType contactInfoType: contactInfoTypes){
            typeSelect.addItem(contactInfoType);
        }

        valueTextField.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                LOG.debug("Cambia el texto");
            }
        });

        addComponent(typeSelect);
        addComponent(valueTextField);
        addComponent(descriptionTextField);

    }

    private Validator getValidator(ContactInfoType contactInfoType) {
        if(contactInfoType.getRegularExpression()!=null){
            return new RegexpValidator(contactInfoType.getRegularExpression(), contactInfoType.getNotValidMessage());
        }
 /*       if(contactInfoType.equals(ContactInfoType.Email)){
            return new RegexpValidator(REG_EXP_EMAIL, "No es una dirección de correo valida");
        }else if(contactInfoType.equals(ContactInfoType.Fax)||contactInfoType.equals(ContactInfoType.Fijo)||
                contactInfoType.equals(ContactInfoType.Movil)){
            return new RegexpValidator(REG_EXP_PHONE, "No es un télefono valido");

        }*/
        return null;
    }

    public ContactInfoComponent(ContactInfoType type, String value, String description){
        super();
        setSpacing(true);

        typeSelect=new ComboBox();
        typeSelect.setWidth("100");
        valueTextField=new TextField();
        descriptionTextField=new TextField();

        List<ContactInfoType> contactInfoTypes=contactInfoTypeRepository.findAll();
        for(ContactInfoType contactInfoType: contactInfoTypes){
            typeSelect.addItem(contactInfoType);
        }
        if(type!=null){
            typeSelect.setValue(type);
        }
        if(value!=null){
            valueTextField.setValue(value);
        }
        if(description!=null){
            descriptionTextField.setValue(description);
        }

        addComponent(typeSelect);
        addComponent(valueTextField);
        addComponent(descriptionTextField);


    }


    public ContactInfo getContactInfo(){
        return contactInfo;
    }


    public ContactInfoTypeRepository getContactInfoTypeRepository() {
        return contactInfoTypeRepository;
    }

    public void setContactInfoTypeRepository(ContactInfoTypeRepository contactInfoTypeRepository) {
        this.contactInfoTypeRepository = contactInfoTypeRepository;
    }
}
