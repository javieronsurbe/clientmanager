package es.mdef.clientmanager.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 16/04/15
 * Time: 22:28
 */
@Entity
@RooToString
@RooJavaBean
@RooSerializable
public class Provider {

    public enum ProviderType{
        Hosting,
        Plantillas,
        Plugins
    };

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;

    private String name;

    @Enumerated
    private ProviderType type;

    private String description;
}
