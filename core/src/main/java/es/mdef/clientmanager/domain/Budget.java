package es.mdef.clientmanager.domain;

import es.mdef.clientmanager.annotations.Amount;
import es.mdef.clientmanager.annotations.PaymentType;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@RooJavaBean
@RooToString
@RooSerializable
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;


    public enum Difficult {
        Basica(new BigDecimal(1f)),
        Mediana(new BigDecimal(1.25f)),
        Dificil(new BigDecimal(1.50f));

        private BigDecimal value;
        Difficult(BigDecimal i) {
            this.value=i;
        }
        public BigDecimal getValue(){
            return value;
        }
    };

    @ManyToOne
    private Client client;


    private Date requestReceived;

    private Date responseSended;

    private Date lastContact;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="totalAmount", column=@Column(name="totalAmountInitial")),
            @AttributeOverride(name="monthlyAmount", column=@Column(name="monthlyAmountInitial")),
            @AttributeOverride(name="yearlyAmount", column=@Column(name="yearlyAmountInitial")),
            @AttributeOverride(name="factor", column=@Column(name="factorInitial"))
    })
    private AmountBudget initialBudget;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name="totalAmount", column=@Column(name="totalAmountSent")),
            @AttributeOverride(name="monthlyAmount", column=@Column(name="monthlyAmountSent")),
            @AttributeOverride(name="yearlyAmount", column=@Column(name="yearlyAmountSent")),
            @AttributeOverride(name="factor", column=@Column(name="factorSent"))
    })
    private AmountBudget budgetSent;

    private Date budgetApproved;

    private String description;


    @Embedded
    private Service service;

    private String sector;

    private String offeredServices;

    private String currentWeb;

    private String referenceWebs;

    private Date deadLineDate;

    @Amount(key = "CPP",paymentType = PaymentType.FINAL)
    private Integer numberOfPages;

    @Amount(key = "CPL",paymentType = PaymentType.FINAL)
    private Integer numberOfLayouts;

    @Enumerated(EnumType.STRING)
    private Difficult difficult=Difficult.Basica;



}
