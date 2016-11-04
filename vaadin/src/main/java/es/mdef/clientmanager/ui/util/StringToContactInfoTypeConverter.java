package es.mdef.clientmanager.ui.util;

import com.vaadin.data.util.converter.Converter;
import es.mdef.clientmanager.domain.ContactInfoType;

import java.util.Locale;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 18/05/14
 * Time: 23:01
 */
public class StringToContactInfoTypeConverter implements Converter<String, ContactInfoType>{
    @Override
    public ContactInfoType convertToModel(String value, Class<? extends ContactInfoType> targetType, Locale locale) throws ConversionException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        ContactInfoType contactInfoType=null;//UserType.findByDescription(value);
        return contactInfoType;
    }

    @Override
    public String convertToPresentation(ContactInfoType contactInfoType, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return contactInfoType.toString();
    }

    @Override
    public Class<ContactInfoType> getModelType() {
        return ContactInfoType.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
