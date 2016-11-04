package es.mdef.clientmanager.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 22/02/15
 * Time: 23:32
 */
@Documented
@Target(FIELD)
@Retention(RUNTIME)
public @interface FieldConfig {

    String width();
}
