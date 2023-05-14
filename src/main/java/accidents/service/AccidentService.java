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
        List<Accident> filledAccidentList = accidentJDBCRepostiory.getAll();
        if (!(filledAccidentList.size() == 0)) {
            LOG.info("Accidents was founded successfully");
        } else {
            LOG.error("Accidents wasn`t found. Empty list of accidents was returned");
        }
        return filledAccidentList;
    }

    public AccidentType findByIdWithJDBC(int typeId) {
        return accidentTypeService.findByIdWithJDBC(typeId);
    }

    public boolean saveJDBC(Accident accident, int[] ids) {
        boolean rsl = false;
        Optional<Accident> nonNullAccident = Optional.ofNullable(accidentJDBCRepostiory.save(accident));
        if (nonNullAccident.isPresent() && ids.length > 0) {
            Accident tmpAccident =  nonNullAccident.get();
            tmpAccident.setRules(accidentRuleService.findRequiredRulesWithJDBC(ids));
            accidentRuleService.setRequiredRulesWithJDBC(tmpAccident);
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
