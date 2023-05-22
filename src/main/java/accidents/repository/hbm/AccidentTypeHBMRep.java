package accidents.repository.hbm;

import accidents.model.AccidentType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentTypeHBMRep {

    private final CRUDRep crudRep;

    private static final String FIND_ALL_TYPES = """
            FROM AccidentType
            """;

    private static final String FIND_TYPE_BY_ID = """
            FROM AccidentType AS at
            WHERE at.id = :fId
            """;

    public List<AccidentType> findAll() {
        return crudRep.query(FIND_ALL_TYPES, AccidentType.class);
    }

    public Optional<AccidentType> findById(int id) {
        return crudRep.optional(FIND_TYPE_BY_ID, AccidentType.class,
                Map.of("fId", id));
    }
}
