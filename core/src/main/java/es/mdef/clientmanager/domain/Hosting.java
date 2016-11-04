package es.mdef.clientmanager.domain;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 21/03/15
 * Time: 17:25
 */
@Entity
@RooJavaBean
@RooToString
@RooSerializable
public class Hosting {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;

    private String hostigName;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Future
    private Date expirationDate;

    private BigDecimal price;
}
