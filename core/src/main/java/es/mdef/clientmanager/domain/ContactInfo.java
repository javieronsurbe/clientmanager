package es.mdef.clientmanager.domain;

import es.mdef.clientmanager.annotations.FieldConfig;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@RooJavaBean
@RooToString
@RooSerializable
public class ContactInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    private Integer version;

    public ContactInfo(){
    }
    public ContactInfo(String value, String description, ContactInfoType type){
        this.value=value;
        this.description=description;
        this.type=type;
    }
    /**
     */
    @Size(max=50)
    private String value;
    @FieldConfig(width="450")
    private String description;

    @ManyToOne
    private ContactInfoType type;

}
