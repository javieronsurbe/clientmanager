package es.mdef.clientmanager.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@RooJavaBean
@RooToString
@RooSerializable
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;

    private String key;
    private String description;
    private BigDecimal value;

 /*   public static Rate findByKey(String key) {
        if (key == null) return null;
        return entityManager().createQuery("SELECT o FROM Rate o where o.key = :key", Rate.class).setParameter("key",key).getSingleResult();
    }*/
}
