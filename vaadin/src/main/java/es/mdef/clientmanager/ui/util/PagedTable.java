package es.mdef.clientmanager.ui.util;

import com.vaadin.data.Container;
import com.vaadin.data.util.filter.Like;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import es.mdef.clientmanager.ui.GestionClientesUI;

import java.util.ArrayList;
import java.util.List;

/**
 * User: jonsurbe
 * Date: 21/04/15
 * Time: 14:07
 */
public class PagedTable extends Table {
    private static final long serialVersionUID = 6881455780158545828L;

    private TextField searchField = new TextField();

    public interface PageChangeListener {
        public void pageChanged(PagedTableChangeEvent event);
    }

    public class PagedTableChangeEvent {

        final PagedTable table;

        public PagedTableChangeEvent(PagedTable table) {
            this.table = table;
        }

        public PagedTable getTable() {
            return table;
        }

        public int getCurrentPage() {
            return table.getCurrentPage();
        }

        public int getTotalAmountOfPages() {
            return table.getTotalAmountOfPages();
        }
    }

    private List<PageChangeListener> listeners = null;

    private PagedTableContainer container;


    public PagedTable() {
        this(null);
    }

    public PagedTable(String caption) {
        super(caption);
    }

    public PagedTable(String caption, Container container) {
        super(caption, container);
    }

    public HorizontalLayout createControls() {
        Label pageLabel = new Label("Pagina:&nbsp;", ContentMode.HTML);
        final Label currentPageLabel = new Label(String.valueOf(getCurrentPage()), ContentMode.HTML);
        Label separatorLabel = new Label("&nbsp;/&nbsp;", ContentMode.HTML);
        final Label totalPagesLabel = new Label(
                String.valueOf(getTotalAmountOfPages()), ContentMode.HTML);;

        pageLabel.setWidth(null);
        separatorLabel.setWidth(null);
        totalPagesLabel.setWidth(null);

        HorizontalLayout controlBar = new HorizontalLayout();

        HorizontalLayout pageManagement = new HorizontalLayout();
        //TODO Falta cambiar los botones por iconos
        final Button first = new Button("Primera", new Button.ClickListener() {
            private static final long serialVersionUID = -355520120491283992L;

            public void buttonClick(Button.ClickEvent event) {
                setCurrentPage(0);
            }
        });;
        first.setIcon(new ThemeResource("icons/page_begin.svg"));
        first.setStyleName("default icon-only");
        final Button previous = new Button("Anterior", new Button.ClickListener() {
            private static final long serialVersionUID = -355520120491283992L;

            public void buttonClick(Button.ClickEvent event) {
                previousPage();
            }
        });
        previous.setIcon(new ThemeResource("icons/page_less.svg"));
        previous.setStyleName("default icon-only");
        final Button next = new Button("Posterior", new Button.ClickListener() {
            private static final long serialVersionUID = -1927138212640638452L;

            public void buttonClick(Button.ClickEvent event) {
                nextPage();
            }
        });
        next.setIcon(new ThemeResource("icons/page_plus.svg"));
        next.setStyleName("default icon-only");
        final Button last = new Button("Ultima", new Button.ClickListener() {
            private static final long serialVersionUID = -355520120491283992L;

            public void buttonClick(Button.ClickEvent event) {
                setCurrentPage(getTotalAmountOfPages());
            }
        });
        last.setIcon(new ThemeResource("icons/page_end.svg"));
        last.setStyleName("default icon-only");

        pageManagement.addComponent(first);
        pageManagement.addComponent(previous);
        pageManagement.addComponent(pageLabel);
        pageManagement.addComponent(currentPageLabel);
        pageManagement.addComponent(separatorLabel);
        pageManagement.addComponent(totalPagesLabel);
        pageManagement.addComponent(next);
        pageManagement.addComponent(last);
        pageManagement.setComponentAlignment(first, Alignment.MIDDLE_LEFT);
        pageManagement.setComponentAlignment(previous, Alignment.MIDDLE_LEFT);
        pageManagement.setComponentAlignment(pageLabel, Alignment.MIDDLE_LEFT);
        pageManagement.setComponentAlignment(currentPageLabel,
                Alignment.MIDDLE_LEFT);
        pageManagement.setComponentAlignment(separatorLabel,
                Alignment.MIDDLE_LEFT);
        pageManagement.setComponentAlignment(totalPagesLabel,
                Alignment.MIDDLE_LEFT);
        pageManagement.setComponentAlignment(next, Alignment.MIDDLE_LEFT);
        pageManagement.setComponentAlignment(last, Alignment.MIDDLE_LEFT);
        pageManagement.setWidth(null);
        pageManagement.setSpacing(true);

        controlBar.addComponent(getBotonera());
        controlBar.addComponent(pageManagement);
        controlBar.setComponentAlignment(pageManagement,
                Alignment.MIDDLE_CENTER);
        controlBar.setWidth("100%");
        //controlBar.setExpandRatio(pageSize, 1);
        addListener(new PageChangeListener() {
            public void pageChanged(PagedTableChangeEvent event) {
                first.setEnabled(container.getStartIndex() > 0);
                previous.setEnabled(container.getStartIndex() > 0);
                next.setEnabled(container.getStartIndex() < container
                        .getRealSize() - getPageLength());
                last.setEnabled(container.getStartIndex() < container
                        .getRealSize() - getPageLength());
                currentPageLabel.setValue(String.valueOf(getCurrentPage()));
                totalPagesLabel.setValue(String.valueOf(getTotalAmountOfPages()));
            }
        });
        return controlBar;
    }


    private Layout getBotonera(){
        HorizontalLayout botonera=new HorizontalLayout();
        Button addNewClientButton;
        addNewClientButton=new Button("Aniadir");
        addNewClientButton.setIcon(new ThemeResource("icons/aniadir.svg"));
        addNewClientButton.setStyleName("default icon-only");
        addNewClientButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                getUI().getNavigator().navigateTo(GestionClientesUI.CLIENT_VIEW);
            }
        });

        botonera.addComponent(searchField);
        botonera.setComponentAlignment(searchField, Alignment.BOTTOM_LEFT);

        botonera.addComponent(addNewClientButton);
        botonera.setComponentAlignment(addNewClientButton, Alignment.BOTTOM_LEFT);

        searchField.addTextChangeListener(new FieldEvents.TextChangeListener() {
            private static final long serialVersionUID = 1048639156493298177L;

            Container.Filter filter = null;

            public void textChange(FieldEvents.TextChangeEvent event) {
                Container.Filterable f = (Container.Filterable) getContainerDataSource();

                if (filter != null)
                    f.removeContainerFilter(filter);

                filter = new Like("client.name", "%" + event.getText() + "%");
                f.addContainerFilter(filter);
                setPageFirstIndex(0);
                firePagedChangedEvent();
            }
        });

        return botonera;
    }
    @Override
    public Container.Indexed getContainerDataSource() {
        return container;
    }

    @Override
    public void setContainerDataSource(Container newDataSource) {
        if (!(newDataSource instanceof Container.Indexed)) {
            throw new IllegalArgumentException(
                    "PagedTable can only use containers that implement Container.Indexed");
        }
        PagedTableContainer pagedTableContainer = new PagedTableContainer(
                (Container.Indexed) newDataSource);
        pagedTableContainer.setPageLength(getPageLength());
        super.setContainerDataSource(pagedTableContainer);
        this.container = pagedTableContainer;
        firePagedChangedEvent();
    }

    private void setPageFirstIndex(int firstIndex) {
        if (container != null) {
            if (firstIndex <= 0) {
                firstIndex = 0;
            }
            if (firstIndex > container.getRealSize() - 1) {
                int size = container.getRealSize() - 1;
                int pages = 0;
                if (getPageLength() != 0) {
                    pages = (int) Math.floor(0.0 + size / getPageLength());
                }
                firstIndex = pages * getPageLength();
            }
            container.setStartIndex(firstIndex);
            setCurrentPageFirstItemIndex(firstIndex);
            containerItemSetChange(new Container.ItemSetChangeEvent() {
                private static final long serialVersionUID = -5083660879306951876L;

                public Container getContainer() {
                    return container;
                }
            });
            if (alwaysRecalculateColumnWidths) {
                for (Object columnId : container.getContainerPropertyIds()) {
                    setColumnWidth(columnId, -1);
                }
            }
            firePagedChangedEvent();
        }
    }

    private void firePagedChangedEvent() {
        if (listeners != null) {
            PagedTableChangeEvent event = new PagedTableChangeEvent(this);
            for (PageChangeListener listener : listeners) {
                listener.pageChanged(event);
            }
        }
    }

    @Override
    public void setPageLength(int pageLength) {
        if (pageLength >= 0 && getPageLength() != pageLength) {
            container.setPageLength(pageLength);
            super.setPageLength(pageLength);
            firePagedChangedEvent();
        }
    }

    public void nextPage() {
        setPageFirstIndex(container.getStartIndex() + getPageLength());
    }

    public void previousPage() {
        setPageFirstIndex(container.getStartIndex() - getPageLength());
    }

    public int getCurrentPage() {
        double pageLength = getPageLength();
        int page = (int) Math.floor((double) container.getStartIndex()
                / pageLength) + 1;
        if (page < 1) {
            page = 1;
        }
        return page;
    }

    public void setCurrentPage(int page) {
        int newIndex = (page - 1) * getPageLength();
        if (newIndex < 0) {
            newIndex = 0;
        }
        if (newIndex >= 0 && newIndex != container.getStartIndex()) {
            setPageFirstIndex(newIndex);
        }
    }

    public int getTotalAmountOfPages() {
        int size = container.getContainer().size();
        double pageLength = getPageLength();
        int pageCount = (int) Math.ceil(size / pageLength);
        if (pageCount < 1) {
            pageCount = 1;
        }
        return pageCount;
    }

    public void addListener(PageChangeListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<PageChangeListener>();
        }
        listeners.add(listener);
    }

    public void removeListener(PageChangeListener listener) {
        if (listeners == null) {
            listeners = new ArrayList<PageChangeListener>();
        }
        listeners.remove(listener);
    }

    public void setAlwaysRecalculateColumnWidths(
            boolean alwaysRecalculateColumnWidths) {
        this.alwaysRecalculateColumnWidths = alwaysRecalculateColumnWidths;
    }
}

