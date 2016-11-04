package es.mdef.clientmanager.repository;

import es.mdef.clientmanager.domain.Budget;
import org.springframework.data.repository.CrudRepository;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 26/01/15
 * Time: 0:25
 */
public interface BudgetRepository extends CrudRepository<Budget, Long> {

}
