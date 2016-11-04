package es.mdef.clientmanager.repository;

import es.mdef.clientmanager.domain.WebAccount;
import org.springframework.data.repository.CrudRepository;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 19/02/15
 * Time: 23:30
 */
public interface WebAccountRepository extends CrudRepository<WebAccount,Long>{
}
