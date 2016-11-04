package es.mdef.clientmanager.domain;

import es.mdef.clientmanager.annotations.FieldConfig;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@RooJavaBean
@RooToString
@RooSerializable
public class WebDomain {

    /**
     */
    @NotNull
    @Size(max = 50)
    @FieldConfig(width = "200")
    private String domainName;

    /**
     */
    @Size(max = 100)
    @FieldConfig(width = "450")
    private String adminUrl;

    private Boolean managedDomain=true;

    private String template;

    @ManyToOne
    private Hosting hosting;
    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Future
    private Date expirationDate;

    @FieldConfig(width = "150")
    private String database;

    @ManyToOne
    private WebAccount webAccount;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;

}
