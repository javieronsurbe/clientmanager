package es.mdef.clientmanager.repository;

import es.mdef.clientmanager.domain.Rate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Mellon TI.
 * User: jonsurbe
 * Date: 26/01/15
 * Time: 0:25
 */
public interface RateRepository extends CrudRepository<Rate, Long> {

    List<Rate> findAll();
    Rate findByKey(String key);

}
