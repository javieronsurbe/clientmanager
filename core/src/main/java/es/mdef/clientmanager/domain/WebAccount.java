package es.mdef.clientmanager.domain;

import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.FetchProfile;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@FetchProfile(name = "customer-with-orders", fetchOverrides = {
        @FetchProfile.FetchOverride(entity = WebAccount.class, association = "client", mode = FetchMode.JOIN)
})
@RooJavaBean
@RooToString
@RooSerializable
public class WebAccount {

    /**
     */
    @NotNull
    @Size(max=50)
    private String name;

    /**
     */
    @NotNull
    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date creationDate=new Date();

    /**
     */
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    @Future
    private Date contractExpirationDate;

    /**
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<WebDomain> domains = new ArrayList<WebDomain>();

    /**
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Users> users = new ArrayList<Users>();

    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    //@Pattern(regexp = "((https?|http):((//)|(\\\\\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)")
    private Boolean webMail;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Version
    @Column(name = "version")
    private Integer version;


    private String templateName;

    @ManyToOne
    private Provider templateProvider;
    @ManyToOne (fetch = FetchType.LAZY)
    private Provider hosting;




 }
