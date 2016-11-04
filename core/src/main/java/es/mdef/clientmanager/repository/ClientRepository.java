package es.mdef.clientmanager.repository;

import es.mdef.clientmanager.domain.Client;
import org.springframework.data.repository.CrudRepository;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 19/02/15
 * Time: 23:33
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
}
