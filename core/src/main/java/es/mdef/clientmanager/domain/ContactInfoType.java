package es.mdef.clientmanager.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;

/**
 * User: jonsurbe
 * Date: 7/04/14
 * Time: 12:20
 */
@Entity
@RooJavaBean
@RooToString
@RooSerializable

public class ContactInfoType {
    public static final Long MOBILE_ID=1l;
    public static final Long LANDLINE_ID=2l;
    public static final Long FAX_ID=3l;
    public static final Long EMAIL_ID=4l;
    private String value;
    private String regularExpression;
    private String notValidMessage;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private Integer version;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ContactInfoType contactInfoType = (ContactInfoType) o;

        if (!getValue().equals(contactInfoType.getValue())) return false;
        if (!getId().equals(contactInfoType.getId())) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = getValue().hashCode();
        result = 31 * result + getId().hashCode();
        result = 31 * result + getVersion().hashCode();
        return result;
    }

}
