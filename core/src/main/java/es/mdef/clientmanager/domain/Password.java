package es.mdef.clientmanager.domain;

import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

/**
 * User: jonsurbe
 * Date: 12/05/14
 * Time: 18:09
 */
@RooToString
@Embeddable
@RooSerializable
public class Password {

    @NotNull
    @Column(name = "password")
    @Lob
    private String value;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
