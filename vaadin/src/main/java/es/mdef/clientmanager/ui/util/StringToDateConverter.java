package es.mdef.clientmanager.ui.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 3/04/14
 * Time: 0:25
 */
public class StringToDateConverter extends com.vaadin.data.util.converter.StringToDateConverter {

    @Override
    protected DateFormat getFormat(Locale locale) {
        return new SimpleDateFormat("dd/MM/yyyy");
    }
}
