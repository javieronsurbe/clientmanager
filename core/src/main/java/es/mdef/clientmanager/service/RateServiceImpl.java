package es.mdef.clientmanager.service;

import es.mdef.clientmanager.annotations.Amount;
import es.mdef.clientmanager.annotations.PaymentType;
import es.mdef.clientmanager.domain.AmountBudget;
import es.mdef.clientmanager.domain.Budget;
import es.mdef.clientmanager.domain.Rate;
import es.mdef.clientmanager.repository.RateRepository;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * User: jonsurbe
 * Date: 2/03/15
 * Time: 18:17
 */
@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    public RateRepository getRateRepository() {
        return rateRepository;
    }

    public void setRateRepository(RateRepository rateRepository) {
        this.rateRepository = rateRepository;
    }

    @Override
    public AmountBudget calculateBudget(Budget budget){
        AmountBudget amountBudget=calculate(budget);
        amountBudget.add(calculate(budget.getService()));
        return amountBudget;
    }
    private AmountBudget calculate(Object o){
        AmountBudget amountBudget=new AmountBudget();
        Field[] fields=o.getClass().getDeclaredFields();
        for(Field field:fields){
            Amount amount=field.getAnnotation(Amount.class);
            //TODO Interesa mover el tipo de pago a la tabla rate?
            if(field.getType().isAssignableFrom(Budget.Difficult.class)){
                try {
                    Budget.Difficult difficult=  (Budget.Difficult)PropertyUtils.getProperty(o, field.getName());
                    amountBudget.setFactor(difficult.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            if(amount!=null){

                Rate rate=rateRepository.findByKey(amount.key());
                BigDecimal rateValue=rate.getValue();

                if(field.getType().isAssignableFrom(Integer.class)){
                    BigDecimal fieldValue;
                    try {
                        fieldValue = new BigDecimal ((Integer)PropertyUtils.getProperty(o, field.getName()));
                    } catch (Exception e) {
                        //e.printStackTrace();
                        fieldValue=new BigDecimal(0);
                    }
                    if(amount.paymentType().equals(PaymentType.YEARLY)){
                        amountBudget.setYearlyAmount(amountBudget.getYearlyAmount().add(rateValue.multiply(fieldValue)));

                    }else if(amount.paymentType().equals(PaymentType.MONTHLY)){
                        amountBudget.setMonthlyAmount(amountBudget.getMonthlyAmount().add(rateValue.multiply(fieldValue)));
                    }else if(amount.paymentType().equals(PaymentType.FINAL)){
                        amountBudget.setTotalAmount(amountBudget.getTotalAmount().add(rateValue.multiply(fieldValue)));
                    }
                }else if(field.getType().isAssignableFrom(Boolean.class)){
                    Boolean booleanValue;
                    try {
                        booleanValue = (Boolean)PropertyUtils.getProperty(o, field.getName());
                    } catch (Exception e) {
                        //e.printStackTrace();
                        booleanValue=false;
                    }

                    if(booleanValue) {
                        if (amount.paymentType().equals(PaymentType.YEARLY)) {
                            amountBudget.setYearlyAmount(amountBudget.getYearlyAmount().add(rateValue));
                        } else if (amount.paymentType().equals(PaymentType.MONTHLY)) {
                            amountBudget.setMonthlyAmount(amountBudget.getMonthlyAmount().add(rateValue));
                        } else if (amount.paymentType().equals(PaymentType.FINAL)) {
                            amountBudget.setTotalAmount(amountBudget.getTotalAmount().add(rateValue));
                        }
                    }
                }
            }

        }
        return amountBudget;
    }

}
