package es.mdef.clientmanager.ui.component;

import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.ui.*;
import es.mdef.clientmanager.domain.Client;
import es.mdef.clientmanager.domain.WebAccount;

import java.util.Date;

/**
 * User: jonsurbe
 * Date: 3/04/14
 * Time: 15:12
 */
public class WebDomainComponent extends HorizontalLayout {

    private FormLayout column1=new FormLayout();
    private FormLayout column2=new FormLayout();

    // Cliente
    private TextField nombreClienteField;
    private TextField companiaField;
    private TextField nifField;
    private TextArea direccionField;
    private TextField poblacionField;
    private TextField provinciaField;
    private TextField codigoPostalField;

    //WebDomain
    private TextField nombreWebField;
    private DateField creationDateField;
    private DateField contractExpirationDateField;
    private CheckBox webMailField;

    public WebDomainComponent(WebAccount webAccount, Client client){
        super();
        this.addComponent(column1);
        this.addComponent(column2);
        column1.setMargin(true);
        column2.setMargin(true);
        nombreClienteField=new TextField("Nombre:");
        companiaField=new TextField("Compañia");
        nifField=new TextField("NIF:");
        direccionField=new TextArea("Dirección:");
        poblacionField=new TextField("Población:");
        provinciaField=new TextField("Provincia:");
        codigoPostalField=new TextField("Codigo Postal:");

        //WebDomain
        nombreWebField=new TextField("Nombre de la web");
        creationDateField = new DateField("Fecha de creación");
        contractExpirationDateField = new DateField("Fecha fin de contrato:");
        webMailField=new CheckBox("Webmail:");

        BeanItem<WebAccount> webAccountBeanItem= new BeanItem<WebAccount>(webAccount);
        BeanItem<Client> clientBeanItem=new BeanItem<Client>(client);

        nombreClienteField.setPropertyDataSource(clientBeanItem.getItemProperty("name"));
        nombreClienteField.setRequired(true);
        nombreClienteField.setRequiredError("Debe insertar un nombre de cliente");
        nombreClienteField.setImmediate(true);
        nombreClienteField.setNullRepresentation("");
       // nombreClienteField.setColumns(20);
        column1.addComponent(nombreClienteField);

        companiaField.setPropertyDataSource(clientBeanItem.getItemProperty("company"));
        companiaField.setRequired(false);
        companiaField.setImmediate(true);
        companiaField.setNullRepresentation("");

        column1.addComponent(companiaField);

        nifField.setPropertyDataSource(clientBeanItem.getItemProperty("nif"));
        //TODO Crear un validador generico basado en OVAL que permita validar campos segun su anotacion
        nifField.setImmediate(true);
        nifField.setNullRepresentation("");
        column1.addComponent(nifField);

        direccionField.setPropertyDataSource(clientBeanItem.getItemProperty("address"));
        direccionField.setRequired(true);
        direccionField.setRequiredError("Debe insertar una dirección");
        direccionField.setImmediate(true);
        direccionField.setNullRepresentation("");
        column1.addComponent(direccionField);

        poblacionField.setPropertyDataSource(clientBeanItem.getItemProperty("city"));
        poblacionField.setRequired(true);
        poblacionField.setRequiredError("Debe insertar una poblacion");
        poblacionField.setImmediate(true);
        poblacionField.setNullRepresentation("");
        column2.addComponent(poblacionField);

        provinciaField.setPropertyDataSource(clientBeanItem.getItemProperty("province"));
        provinciaField.setImmediate(true);
        provinciaField.setNullRepresentation("");
        column2.addComponent(provinciaField);

        codigoPostalField.setPropertyDataSource(clientBeanItem.getItemProperty("zipCode"));
        codigoPostalField.setImmediate(true);
        codigoPostalField.setNullRepresentation("");
        column2.addComponent(codigoPostalField);

        nombreWebField.setPropertyDataSource(webAccountBeanItem.getItemProperty("name"));
        nombreWebField.setRequired(true);
        nombreWebField.setRequiredError("Debe insertar el nombre de la web");
        nombreWebField.setImmediate(true);
        nombreWebField.setNullRepresentation("");
        column2.addComponent(nombreWebField);

        creationDateField.setPropertyDataSource((Property<Date>) webAccountBeanItem.getItemProperty("creationDate"));
        creationDateField.setImmediate(true);
        column2.addComponent(creationDateField);

        contractExpirationDateField.setPropertyDataSource((Property<Date>) webAccountBeanItem.getItemProperty("contractExpirationDate"));
        contractExpirationDateField.setImmediate(true);
        column2.addComponent(contractExpirationDateField);

        webMailField.setPropertyDataSource(webAccountBeanItem.getItemProperty("webMail"));
        webMailField.setImmediate(true);
        column1.addComponent(webMailField);

    }
}
