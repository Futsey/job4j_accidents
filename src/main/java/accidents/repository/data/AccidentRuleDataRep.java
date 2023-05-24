package accidents.repository.data;

import accidents.model.Accident;
import accidents.model.Rule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface AccidentRuleDataRep extends CrudRepository<Accident, Integer> {

    @Query("FROM Accident AS a JOIN FETCH a.accidentType JOIN FETCH a.rules")
    List<Rule> findAllRules();

    @Query("FROM Rule r WHERE r.id IN :accidentId")
    Set<Rule> getRequiredRules(int accidentId);

    @Query("FROM Rule r WHERE r.id IN :ids")
    Set<Rule> getRequiredRulesOldVers(Integer[] ids);
}


