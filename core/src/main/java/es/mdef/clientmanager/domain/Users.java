package es.mdef.clientmanager.domain;
import es.mdef.clientmanager.annotations.FieldConfig;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@RooJavaBean
@RooToString
@RooSerializable
public class Users {

    /**
     */
    @NotNull
    private String identifier;

    /**
     */
    @NotNull
    @Embedded
    private Password password;

    /**
     */
    @ManyToOne
    @FieldConfig(width = "250")
    private UserType userType;

    @ManyToOne
    private WebAccount webAccount;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;

}
