package es.mdef.clientmanager.domain;

import es.mdef.clientmanager.annotations.Amount;
import es.mdef.clientmanager.annotations.PaymentType;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Embeddable;

/**
 * User: jonsurbe
 * Date: 15/12/14
 * Time: 17:17
 */
@Embeddable
@RooJavaBean
@RooToString
@RooSerializable
public class Service {

    private Boolean marketing=false;

    private Boolean corporateWeb=false;

    private Boolean onlineShop=false;

    private Boolean graphicDesign=false;

    private Boolean customDevelop=false;

    @Amount(key = "HOSTING", paymentType = PaymentType.MONTHLY)
    private Boolean hosting=false;

    @Amount(key = "DOMAIN", paymentType = PaymentType.MONTHLY)
    private Boolean domain=false;

    @Amount(key = "UPDATES", paymentType = PaymentType.MONTHLY)
    private Boolean updates=false;

    @Amount(key = "MAILING", paymentType = PaymentType.MONTHLY)
    private Boolean mailing=false;

    @Amount(key = "RRSS", paymentType = PaymentType.MONTHLY)
    private Boolean socialNetworks=false;

    @Amount(key = "LOGO", paymentType = PaymentType.FINAL)
    private Boolean logo=false;
}
