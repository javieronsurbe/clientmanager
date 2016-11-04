package es.mdef.clientmanager.ui.util;

import com.vaadin.data.Container;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import es.mdef.clientmanager.annotations.FieldConfig;
import es.mdef.clientmanager.domain.ContactInfoType;
import es.mdef.clientmanager.domain.Password;
import es.mdef.clientmanager.domain.UserType;
import es.mdef.clientmanager.repository.ContactInfoTypeRepository;
import es.mdef.clientmanager.repository.UserTypeRepository;
import es.mdef.clientmanager.ui.component.SwappablePasswordField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.util.Date;

/**
 * User: jonsurbe
 * Date: 7/05/14
 * Time: 16:25
 */
@Configurable(preConstruction = true)
public class TableFieldFactory implements com.vaadin.ui.TableFieldFactory {
    private static final TableFieldFactory instance = new TableFieldFactory();
    private static final Logger LOG = LoggerFactory.getLogger(TableFieldFactory.class);

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private ContactInfoTypeRepository contactInfoTypeRepository;


    /**
     * Singleton method to getInstance an instance of DefaultFieldFactory.
     *
     * @return an instance of DefaultFieldFactory
     */
    public static TableFieldFactory getInstance() {
        return instance;
    }

    protected TableFieldFactory() {
    }


    @Override
    public Field createField(Container container, Object itemId,
                             Object propertyId, Component uiContext) {

        Property containerProperty = container.getContainerProperty(itemId,
                propertyId);

        FieldConfig fieldConfig=null;
        for(java.lang.reflect.Field field:itemId.getClass().getDeclaredFields()){
            if(field.getName().equals(propertyId)) {
                fieldConfig = field.getAnnotation(FieldConfig.class);
            }
        }
        System.out.println(fieldConfig);
        Class<?> type = containerProperty.getType();
        Field<?> field = createFieldByPropertyType(type, containerProperty, fieldConfig);

        //field.setCaption(createCaptionByPropertyId(propertyId));
        setRestrictions(itemId, propertyId, field);
        return field;
    }

    /**
     * If name follows method naming conventions, convert the name to spaced
     * upper case text. For example, convert "firstName" to "First Name"
     *
     * @param propertyId
     * @return the formatted caption string
     */
    public static String createCaptionByPropertyId(Object propertyId) {
        String name = propertyId.toString();
        if (name.length() > 0) {

            int dotLocation = name.lastIndexOf('.');
            if (dotLocation > 0 && dotLocation < name.length() - 1) {
                name = name.substring(dotLocation + 1);
            }
            if (name.indexOf(' ') < 0
                    && name.charAt(0) == Character.toLowerCase(name.charAt(0))
                    && name.charAt(0) != Character.toUpperCase(name.charAt(0))) {
                StringBuffer out = new StringBuffer();
                out.append(Character.toUpperCase(name.charAt(0)));
                int i = 1;

                while (i < name.length()) {
                    int j = i;
                    for (; j < name.length(); j++) {
                        char c = name.charAt(j);
                        if (Character.toLowerCase(c) != c
                                && Character.toUpperCase(c) == c) {
                            break;
                        }
                    }
                    if (j == name.length()) {
                        out.append(name.substring(i));
                    } else {
                        out.append(name.substring(i, j));
                        out.append(" " + name.charAt(j));
                    }
                    i = j + 1;
                }

                name = out.toString();
            }
        }
        return name;
    }

    /**
     * Creates fields based on the property type.
     * <p>
     * The default field type is {@link TextField}. Other field types generated
     * by this method:
     * <p>
     * <b>Boolean</b>: {@link CheckBox}.<br/>
     * <b>Date</b>: {@link DateField}(resolution: day).<br/>
     * <b>Item</b>: {@link Form}. <br/>
     * <b>default field type</b>: {@link TextField}.
     * <p>
     *
     *
     * @param type
     *            the type of the property
     * @param containerProperty
     * @param fieldConfig
     * @return the most suitable generic {@link Field} for given type
     */
    public Field<?> createFieldByPropertyType(Class<?> type, Property containerProperty, FieldConfig fieldConfig) {
        // Null typed properties can not be edited
        AbstractField field=null;
        if (type == null) {
            return null;
        }else if(Password.class.isAssignableFrom(type)){
            SwappablePasswordField passwordField=new SwappablePasswordField();
            //passwordField.setConverter(new StringToPasswordConverter());
            field=passwordField;
        }else if(UserType.class.isAssignableFrom(type)){
            final BeanItemContainer<UserType> beanItemContainer=new BeanItemContainer(UserType.class, userTypeRepository.findAll());
            ComboBox userTypeSelect=new ComboBox();
            userTypeSelect.setContainerDataSource(beanItemContainer);
            userTypeSelect.setItemCaptionPropertyId("description");
            field=userTypeSelect;
        } else if(ContactInfoType.class.isAssignableFrom(type)){
            final BeanItemContainer<ContactInfoType> beanItemContainer=new BeanItemContainer(ContactInfoType.class, contactInfoTypeRepository.findAll());
            ComboBox contactInfoTypeSelect=new ComboBox();
            contactInfoTypeSelect.setContainerDataSource(beanItemContainer);
            contactInfoTypeSelect.setItemCaptionPropertyId("value");
            field=contactInfoTypeSelect;
        }else if (Date.class.isAssignableFrom(type)) {
            final DateField df = new DateField();
            df.setResolution(Resolution.DAY);
            field= df;
        }else if (Boolean.class.isAssignableFrom(type)) {
            field= new CheckBox();
        }else{
            TextField textField=new TextField();
            textField.setNullRepresentation("");
            field=textField;
        }
        if(fieldConfig!=null){
            field.setWidth(fieldConfig.width());
        }
        return field;
    }

    private void setRestrictions(Object itemId, Object propertyId, Field field){
        field.addValidator(new BeanValidator(itemId.getClass(), (String)propertyId));
    }


    public UserTypeRepository getUserTypeRepository() {
        return userTypeRepository;
    }

    public void setUserTypeRepository(UserTypeRepository userTypeRepository) {
        this.userTypeRepository = userTypeRepository;
    }

    public ContactInfoTypeRepository getContactInfoTypeRepository() {
        return contactInfoTypeRepository;
    }

    public void setContactInfoTypeRepository(ContactInfoTypeRepository contactInfoTypeRepository) {
        this.contactInfoTypeRepository = contactInfoTypeRepository;
    }
}
