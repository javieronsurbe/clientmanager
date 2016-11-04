package es.mdef.clientmanager.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Embeddable;
import java.math.BigDecimal;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 17/02/15
 * Time: 0:05
 */
@Embeddable
@RooJavaBean
@RooToString
@RooSerializable
public class AmountBudget {

    private BigDecimal totalAmount=new BigDecimal(0);;
    private BigDecimal monthlyAmount=new BigDecimal(0);;
    private BigDecimal yearlyAmount=new BigDecimal(0);;

    private BigDecimal factor=new BigDecimal(0);;


    public BigDecimal obtainTotalFinalAmount(){
        return this.totalAmount.multiply(factor);
    }
    public void add(AmountBudget amountBudget){
        this.monthlyAmount=this.monthlyAmount.add(amountBudget.getMonthlyAmount());
        this.yearlyAmount=this.yearlyAmount.add(amountBudget.getYearlyAmount());
        this.totalAmount=this.totalAmount.add(amountBudget.getTotalAmount());
    }
}
