package es.mdef.clientmanager.ui.component;

import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.Property;
import com.vaadin.data.util.filter.UnsupportedFilterException;
import es.mdef.clientmanager.domain.Client;

import java.util.Collection;
import java.util.List;

/**
 * User: jonsurbe
 * Date: 31/03/14
 * Time: 15:52
 */
public class ClientsContainer implements Container, Container.Sortable,
        Container.PropertySetChangeNotifier, Container.Filterable, Container.SimpleFilterable {

    List<Client> listClients;
    @Override
    public Item getItem(Object itemId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<?> getContainerPropertyIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<?> getItemIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Property getContainerProperty(Object itemId, Object propertyId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Class<?> getType(Object propertyId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int size() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean containsId(Object itemId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Item addItem(Object itemId) throws UnsupportedOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object addItem() throws UnsupportedOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeItem(Object itemId) throws UnsupportedOperationException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean addContainerProperty(Object propertyId, Class<?> type, Object defaultValue) throws UnsupportedOperationException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeContainerProperty(Object propertyId) throws UnsupportedOperationException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeAllItems() throws UnsupportedOperationException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addContainerFilter(Filter filter) throws UnsupportedFilterException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeContainerFilter(Filter filter) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addContainerFilter(Object propertyId, String filterString, boolean ignoreCase, boolean onlyMatchPrefix) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeAllContainerFilters() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeContainerFilters(Object propertyId) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<Filter> getContainerFilters() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void sort(Object[] propertyId, boolean[] ascending) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Collection<?> getSortableContainerPropertyIds() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object nextItemId(Object itemId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object prevItemId(Object itemId) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object firstItemId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object lastItemId() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isFirstId(Object itemId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isLastId(Object itemId) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object addItemAfter(Object previousItemId) throws UnsupportedOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Item addItemAfter(Object previousItemId, Object newItemId) throws UnsupportedOperationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addPropertySetChangeListener(PropertySetChangeListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void addListener(PropertySetChangeListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removePropertySetChangeListener(PropertySetChangeListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeListener(PropertySetChangeListener listener) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
