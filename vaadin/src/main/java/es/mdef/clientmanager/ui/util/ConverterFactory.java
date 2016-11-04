package es.mdef.clientmanager.ui.util;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.converter.DefaultConverterFactory;
import es.mdef.clientmanager.domain.Password;

/**
 * User: jonsurbe
 * Date: 14/05/14
 * Time: 16:38
 */
public class ConverterFactory extends DefaultConverterFactory{
    @Override
    public <PRESENTATION, MODEL> Converter<PRESENTATION, MODEL>
    createConverter(Class<PRESENTATION> presentationType,
                    Class<MODEL> modelType) {
        // Handle one particular type conversion
        if (String.class == presentationType &&
                Password.class == modelType)
            return (Converter<PRESENTATION, MODEL>)
                    new StringToPasswordConverter();


        // Default to the supertype
        return super.createConverter(presentationType,
                modelType);
    }

}
