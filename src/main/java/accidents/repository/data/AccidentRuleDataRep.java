package accidents.repository.data;

import accidents.model.Accident;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccidentRuleDataRep extends CrudRepository<Accident, Integer> {
}
