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

public class AppUser{

    @NotNull
    private String login;


    @NotNull
    @Embedded
    private Password password;

    @NotNull
    private String userName;

    private String email;

    @Version
    private Integer version;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
}
