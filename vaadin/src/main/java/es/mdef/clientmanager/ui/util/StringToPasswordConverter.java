package es.mdef.clientmanager.ui.util;

import com.vaadin.data.util.converter.Converter;
import es.mdef.clientmanager.domain.Password;

import java.util.Locale;

/**
 * User: jonsurbe
 * Date: 14/05/14
 * Time: 10:34
 */
public class StringToPasswordConverter implements Converter<String, Password> {
    @Override
    public Password convertToModel(String value, Class<? extends Password> targetType, Locale locale) throws ConversionException {
        if (value == null) {
            value="";
        }
        Password password=new Password();
        password.setValue(value);
        return password;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String convertToPresentation(Password password, Class<? extends String> aClass, Locale locale) throws ConversionException {
        if(password==null){
            return new Password().getValue();
        }else {
            return password.getValue();
        }
    }

    @Override
    public Class<Password> getModelType() {
        return Password.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }
}
