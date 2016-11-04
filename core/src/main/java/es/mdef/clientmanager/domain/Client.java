package es.mdef.clientmanager.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@RooJavaBean
@RooToString
@RooSerializable
public class Client {

    /**
     */
    @NotNull
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String company;

    /**
     */
    private String address;

    @Size(max = 100)
    private String city;
    @Size(max = 100)
    private String province;

    @Size(max = 5)
    @Pattern(regexp = "d{8}")
    private String zipCode;
    @Size(max = 12)
    private String nif;

    /**
     */
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ContactInfo> contactInfoList = new ArrayList<ContactInfo>();

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

}
