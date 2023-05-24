package accidents.repository.data;

import accidents.model.Accident;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccidentDataRep extends CrudRepository<Accident, Integer> {

    @Query("FROM Accident AS a JOIN FETCH a.accidentType JOIN FETCH a.rules")
    List<Accident> findAll();

    @Query("FROM Accident AS a JOIN FETCH a.accidentType JOIN FETCH a.rules WHERE a.id = :accidentId")
    Optional<Accident> findById(int accidentId);
}
