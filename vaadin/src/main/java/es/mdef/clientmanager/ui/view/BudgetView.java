package es.mdef.clientmanager.ui.view;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import es.mdef.clientmanager.domain.AmountBudget;
import es.mdef.clientmanager.domain.Budget;
import es.mdef.clientmanager.domain.Service;
import es.mdef.clientmanager.security.SecuredView;
import es.mdef.clientmanager.service.RateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import java.text.NumberFormat;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 5/03/15
 * Time: 23:45
 */
@Configurable(preConstruction = true)
public class BudgetView extends SecuredView implements View {

    final Logger LOG = LoggerFactory.getLogger(BudgetView.class);

    private HorizontalLayout horizontalLayout=new HorizontalLayout();

    private TextField monthlyBudget=new TextField("Pago mensual");
    private TextField totalBudget=new TextField("Unico pago");

    private Budget budget=new Budget();

    private Service service = new Service();

    private static NumberFormat nf=NumberFormat.getCurrencyInstance();

    @Autowired
    private RateService rateService;


    private void init(){
        budget.setService(service);
        horizontalLayout.setSizeFull();
        horizontalLayout.addComponent(getDataLayout(new ValueChangeListener()));
        horizontalLayout.addComponent(getResultLayout());
        addComponent(horizontalLayout);
        setSizeFull();


        //this.addComponent(getTablePanel());
    }


    private class ValueChangeListener implements Property.ValueChangeListener{
        @Override
        public void valueChange(Property.ValueChangeEvent event) {
            LOG.debug("ValueChangeListener");
            AmountBudget amountBudget= rateService.calculateBudget(budget);
            //FIXME Formatear resultados
            monthlyBudget.setReadOnly(false);
            totalBudget.setReadOnly(false);
            monthlyBudget.setValue(nf.format(amountBudget.getMonthlyAmount()));
            totalBudget.setValue(nf.format(amountBudget.obtainTotalFinalAmount()));
            monthlyBudget.setReadOnly(true);
            totalBudget.setReadOnly(true);
        }
    }
    private Layout getDataLayout(Property.ValueChangeListener valueChangeListener){
        FormLayout formLayout=new FormLayout();
        BeanItem<Budget> budgetBeanItem=new BeanItem<Budget>(budget);
        BeanItem<Service> serviceBeanItem=new BeanItem<Service>(service);

        TextField layoutsField=new TextField("Numero de layouts");
        layoutsField.setPropertyDataSource(budgetBeanItem.getItemProperty("numberOfLayouts"));
        layoutsField.addValueChangeListener(valueChangeListener);
        layoutsField.setNullRepresentation("");

        formLayout.addComponent(layoutsField);

        //TODO Controlar un minimo de 3 para el calculo
        TextField pagesField=new TextField("Numero de paginas");
        pagesField.setPropertyDataSource(budgetBeanItem.getItemProperty("numberOfPages"));
        pagesField.addValueChangeListener(valueChangeListener);
        pagesField.setNullRepresentation("");

        formLayout.addComponent(pagesField);

        CheckBox hostingCheckBox=new CheckBox("Hosting");
        hostingCheckBox.setPropertyDataSource(serviceBeanItem.getItemProperty("hosting"));
        hostingCheckBox.addValueChangeListener(valueChangeListener);

        formLayout.addComponent(hostingCheckBox);

        CheckBox domainCheckBox=new CheckBox("Dominio");
        domainCheckBox.setPropertyDataSource(serviceBeanItem.getItemProperty("domain"));
        domainCheckBox.addValueChangeListener(valueChangeListener);

        formLayout.addComponent(domainCheckBox);

        CheckBox updatesCheckBox=new CheckBox("Actualización");
        updatesCheckBox.setPropertyDataSource(serviceBeanItem.getItemProperty("updates"));
        updatesCheckBox.addValueChangeListener(valueChangeListener);

        formLayout.addComponent(updatesCheckBox);

        CheckBox mailingCheckBox=new CheckBox("Mailing");
        mailingCheckBox.setPropertyDataSource(serviceBeanItem.getItemProperty("mailing"));
        mailingCheckBox.addValueChangeListener(valueChangeListener);

        formLayout.addComponent(mailingCheckBox);

        CheckBox socialNetworksCheckBox=new CheckBox("RRSS");
        socialNetworksCheckBox.setPropertyDataSource(serviceBeanItem.getItemProperty("socialNetworks"));
        socialNetworksCheckBox.addValueChangeListener(valueChangeListener);

        formLayout.addComponent(socialNetworksCheckBox);

        CheckBox logoCheckBox=new CheckBox("Logo");
        logoCheckBox.setPropertyDataSource(serviceBeanItem.getItemProperty("logo"));
        logoCheckBox.addValueChangeListener(valueChangeListener);

        formLayout.addComponent(logoCheckBox);

        ComboBox difficultComboBox = new ComboBox("Dificultad");

        difficultComboBox.addItem(Budget.Difficult.Basica);
        difficultComboBox.addItem(Budget.Difficult.Mediana);
        difficultComboBox.addItem(Budget.Difficult.Dificil);

        difficultComboBox.setPropertyDataSource(budgetBeanItem.getItemProperty("difficult"));

        difficultComboBox.addValueChangeListener(valueChangeListener);

        formLayout.addComponent(difficultComboBox);

        return formLayout;
    }

    private Layout getResultLayout(){
        FormLayout resultLayout=new FormLayout();
        monthlyBudget.setReadOnly(true);
        monthlyBudget.addStyleName("borderless");
        //monthlyBudget.setConverter(new StringToCurrencyConverter());

        totalBudget.setReadOnly(true);
        totalBudget.addStyleName("borderless");
        //totalBudget.setConverter(new StringToCurrencyConverter());

        resultLayout.addComponent(monthlyBudget);
        resultLayout.addComponent(totalBudget);
        return resultLayout;
    }
    @Override
    public void onEnter(ViewChangeListener.ViewChangeEvent event) {
        init();
    }


    public RateService getRateService() {
        return rateService;
    }

    public void setRateService(RateService rateService) {
        this.rateService = rateService;
    }
}
