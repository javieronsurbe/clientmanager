package es.mdef.clientmanager.service;

import es.mdef.clientmanager.domain.AmountBudget;
import es.mdef.clientmanager.domain.Budget;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 2/03/15
 * Time: 23:26
 */
public interface RateService {
    AmountBudget calculateBudget(Budget budget);
}
