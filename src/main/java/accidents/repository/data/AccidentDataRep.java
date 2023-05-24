package accidents.repository.data;

import accidents.model.Accident;
import org.springframework.data.repository.CrudRepository;

public interface AccidentDataRep extends CrudRepository<Accident, Integer> {
}
