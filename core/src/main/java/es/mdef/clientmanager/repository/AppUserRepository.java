package es.mdef.clientmanager.repository;

import es.mdef.clientmanager.domain.AppUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 19/02/15
 * Time: 23:22
 */
public interface AppUserRepository extends CrudRepository<AppUser, Long> {

    public List<AppUser> findAll();
    public AppUser findByLogin(String login);

}
