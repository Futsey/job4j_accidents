package accidents.service;

import accidents.model.Accident;
import accidents.model.AccidentType;

import accidents.repository.inmemory.AccidentMem;
import accidents.repository.jdbc.AccidentJdbcRep;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccidentService {


    private final AccidentTypeService accidentTypeService;
    private final AccidentRuleService accidentRuleService;

    private final AccidentJdbcRep accidentJDBCRepostiory;

    private final AccidentMem accidentMem;

    private static final Logger LOG = LoggerFactory.getLogger(AccidentService.class.getName());


    public List<Accident> findAllJDBC() {
        return accidentJDBCRepostiory.getAll();
    }

    public AccidentType findByIdWithJDBC(int typeId) {
        return accidentTypeService.findByIdWithJDBC(typeId);
    }

    public boolean saveJDBC(Accident accident, int[] ids) {
        boolean rsl = false;
        if (accidentJDBCRepostiory.save(accident) && ids.length > 0) {
            System.out.println();
            accidentRuleService.setRequiredRulesWithJDBC(accident.getId(), ids);
            rsl = true;
        }
        return rsl;
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
