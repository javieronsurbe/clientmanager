package es.mdef.clientmanager.ui.util;

import com.vaadin.data.util.converter.Converter;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 15/04/15
 * Time: 21:25
 */
public class StringToCurrencyConverter implements Converter<String, BigDecimal> {
    private static NumberFormat nf=NumberFormat.getCurrencyInstance();

    @Override
    public BigDecimal convertToModel(String value, Class<? extends BigDecimal> targetType, Locale locale) throws ConversionException {

        if(value==null||value.length()==0){
            return BigDecimal.ZERO;
        }
        try {
            return new BigDecimal(nf.parse(value).doubleValue());
        } catch (ParseException e) {
            return BigDecimal.ZERO;
        }
    }

    @Override
    public String convertToPresentation(BigDecimal value, Class<? extends String> targetType, Locale locale) throws ConversionException {
        return nf.format(value.doubleValue());
    }

    @Override
    public Class<BigDecimal> getModelType() {
        return BigDecimal.class;
    }

    @Override
    public Class<String> getPresentationType() {
        return String.class;
    }

}
