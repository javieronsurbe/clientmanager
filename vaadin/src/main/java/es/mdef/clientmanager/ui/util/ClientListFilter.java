package es.mdef.clientmanager.ui.util;

import com.vaadin.data.Container;
import com.vaadin.data.Item;

/**
 * User: jonsurbe
 * Date: 8/04/15
 * Time: 15:07
 */
public class ClientListFilter implements Container.Filter {
    @Override
    public boolean passesFilter(Object itemId, Item item) throws UnsupportedOperationException {
        return true;
    }

    @Override
    public boolean appliesToProperty(Object propertyId) {
        if(propertyId.equals("client.name"))
            return true;
        else
            return false;
    }
}
