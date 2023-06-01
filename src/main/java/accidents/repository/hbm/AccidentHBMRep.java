package accidents.repository.hbm;

import accidents.model.Accident;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class AccidentHBMRep {

    private final CRUDRep crudRepository;

    private static final Logger LOG = LoggerFactory.getLogger(AccidentHBMRep.class.getName());

    private static final String SELECT_ALL_WITH_TYPE_AND_RULES = """
            SELECT DISTINCT a FROM Accident AS a
            LEFT JOIN FETCH a.accidentType
            LEFT JOIN FETCH a.rules
            """;
    private static final String SELECT_BY_ID = """
            FROM Accident AS a
            JOIN FETCH a.accidentType
            JOIN FETCH a.rules
            WHERE a.id = :fId
            """;

    private static final String DELETE = """
            DELETE Accident AS a
            WHERE a.id = :fId
            """;

    public void save(Accident accident) {
        crudRepository.run(session -> session.save(accident));
    }

    public List<Accident> getAll() {
        return crudRepository.query(SELECT_ALL_WITH_TYPE_AND_RULES, Accident.class);
    }

    public Optional<Accident> findById(int id) {
        return crudRepository.optional(
                SELECT_BY_ID, Accident.class,
                Map.of("fId", id)
        );
    }

    public Optional<Accident> update(Accident accident) {
        Optional<Accident> rsl = Optional.empty();
        if (findById(accident.getId()).isPresent()) {
            merge(accident);
            rsl = Optional.of(accident);
        }
        return rsl;
    }

    public Accident merge(Accident accident) {
        crudRepository.run(tmpAccident -> tmpAccident.merge(accident));
        return Accident.builder()
                .id(accident.getId())
                .name(accident.getName())
                .text(accident.getText())
                .address(accident.getAddress())
                .accidentType(accident.getAccidentType())
                .rules(accident.getRules())
                .build();
    }

    public boolean delete(int id) {
        boolean rsl = false;
        try {
            crudRepository.run(DELETE, Map.of("fId", id));
            rsl = true;
        } catch (Exception e) {
            LOG.error("Exception: TaskDBStore{ update() }", e);
        }
        return rsl;
    }
}
