package accidents.repository.data;

import accidents.model.Accident;
import accidents.model.AccidentType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AccidentTypeDataRep extends CrudRepository<Accident, Integer> {

    @Query("FROM AccidentType")
    List<AccidentType> findAllTypes();

    @Query("FROM AccidentType AS at WHERE at.id = :id")
    Optional<AccidentType> findById(int id);
}
