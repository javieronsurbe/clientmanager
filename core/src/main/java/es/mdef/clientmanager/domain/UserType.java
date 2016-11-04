package es.mdef.clientmanager.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@RooJavaBean
@RooToString
@RooSerializable
public class UserType {

    /**
     */
    @NotNull
    private String description;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Version
    @Column(name = "version")
    private Integer version;

//    public static UserType findByDescription(String description){
//        return entityManager().createQuery("SELECT o FROM UserType o WHERE description=:description", UserType.class)
//                .setParameter("description", description).getSingleResult();
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserType userType = (UserType) o;

        if (!getDescription().equals(userType.getDescription())) return false;
        if (!getId().equals(userType.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getDescription().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getVersion().hashCode();
        return result;
    }

}
