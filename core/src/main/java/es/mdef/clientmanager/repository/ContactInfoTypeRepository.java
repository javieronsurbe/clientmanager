package es.mdef.clientmanager.repository;

import es.mdef.clientmanager.domain.ContactInfoType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 19/02/15
 * Time: 23:46
 */
public interface ContactInfoTypeRepository extends CrudRepository<ContactInfoType, Long> {
    public List<ContactInfoType> findAll();
}
