package accidents.service;

import accidents.model.Accident;
import accidents.model.AccidentType;
import accidents.repository.jdbc.AccidentJdbcRep;
import accidents.repository.inmemory.AccidentMem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;
    private final AccidentTypeService accidentTypeService;
    private final AccidentRuleService accidentRuleService;

    private final AccidentJdbcRep accidentJDBCRepostiory;

    public List<Accident> findAllJDBC() {
        return accidentJDBCRepostiory.getAll();
    }

    public AccidentType findByIdWithJDBC(int typeId) {

        return accidentTypeService.findByIdWithJDBC(typeId);
    }









    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Optional<Accident> findById(Integer accidentId) {
        return Optional.ofNullable(accidentMem.findById(accidentId));
    }

    public Optional<Accident> save(Accident accident, String[] ids) {
        accident.setRules(accidentRuleService.findRequiredRules(ids));
        accident.setAccidentType(accidentTypeService.findById(accident.getAccidentType().getId()).get());
        return Optional.ofNullable(accidentMem.save(accident));
    }

    public boolean update(Accident accident) {
        var rsl = false;
        if (Optional.ofNullable(accident).isPresent()) {
            accidentMem.update(accident);
            rsl = true;
        }
        return rsl;
    }

}
