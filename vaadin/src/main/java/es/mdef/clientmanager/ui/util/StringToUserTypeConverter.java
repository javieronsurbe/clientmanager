package es.mdef.clientmanager.ui.util;

import com.vaadin.data.util.converter.Converter;
import es.mdef.clientmanager.domain.UserType;

import java.util.Locale;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 18/05/14
 * Time: 23:01
 */
public class StringToUserTypeConverter implements Converter<String, UserType>{
    @Override
    public UserType convertToModel(String value, Class<? extends UserType> targetType, Locale locale) throws ConversionException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        UserType userType=null;//UserType.findByDescription(value);
        return userType;
    }

    @Override
    public String convertToPresentation(UserType userType, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return userType.getDescription();
    }

    @Override
    public Class<UserType> getModelType() {
        return UserType.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
