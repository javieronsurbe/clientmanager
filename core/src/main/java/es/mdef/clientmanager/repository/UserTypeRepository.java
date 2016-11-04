package es.mdef.clientmanager.repository;

import es.mdef.clientmanager.domain.UserType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * User: jonsurbe
 * Date: 20/02/15
 * Time: 14:47
 */
public interface UserTypeRepository extends CrudRepository<UserType, Long> {

    public List<UserType> findAll();

}
