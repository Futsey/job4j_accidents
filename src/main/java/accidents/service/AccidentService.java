package accidents.service;

import accidents.model.Accident;

import accidents.model.AccidentType;
import accidents.model.Rule;
import accidents.repository.data.AccidentDataRep;
import accidents.repository.data.AccidentRuleDataRep;
import accidents.repository.data.AccidentTypeDataRep;
import accidents.repository.hbm.AccidentHBMRep;
import accidents.repository.hbm.AccidentRuleHBMRep;
import accidents.repository.hbm.AccidentTypeHBMRep;
import accidents.repository.inmemory.AccidentMem;
import accidents.repository.jdbc.AccidentJdbcRep;
import accidents.repository.jdbc.AccidentRuleJdbcRep;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentRuleService accidentRuleService;
    private final AccidentTypeService accidentTypeService;

    private final AccidentHBMRep accidentHBMRep;

    private final AccidentDataRep accidentDataRep;

    private final AccidentTypeDataRep accidentTypeDataRep;
    private final AccidentRuleDataRep accidentRuleDataRep;

    private final AccidentRuleHBMRep accidentRuleHBMRep;
    private final AccidentTypeHBMRep accidentTypeHBMRep;

    private final AccidentJdbcRep accidentJDBCRepostiory;
    private final AccidentRuleJdbcRep accidentRuleJdbcRep;

    private final AccidentMem accidentMem;

    private static final Logger LOG = LoggerFactory.getLogger(AccidentService.class.getName());

    public List<Accident> findAllSData() {
        return accidentDataRep.findAll();
    }

    public Optional<Accident> findByIdSData(int accidentId) {
        Optional<Accident> nonNullAccident = Optional.ofNullable(accidentDataRep.findById(accidentId));
        if (nonNullAccident.isPresent()) {
            LOG.info("Accident was found successfully");
        } else {
            LOG.error("Accident wasn`t found. Empty accident was returned");
        }
        return nonNullAccident;
    }

    public boolean saveSData(Accident accident, Integer[] ids) {
        boolean rsl = false;
        Optional<AccidentType> type = accidentTypeDataRep.findById(accident.getAccidentType().getId());
        Set<Rule> rules = accidentRuleDataRep.getRequiredRulesOldVers(ids);
        if (type.isPresent() || rules.size() > 0) {
            accident.setAccidentType(type.get());
            accident.setRules(rules);
            accidentDataRep.save(accident);
            LOG.info("Accident was saved successfully");
            rsl = true;
        } else {
            LOG.error("Accident wasn`t saved");
        }
        return rsl;
    }

    public void deleteSData(int id) {
        accidentDataRep.delete(Accident.builder().id(id).build());
    }

    public List<Accident> findAllHBM() {
        List<Accident> filledAccidentList = accidentHBMRep.getAll();
        if (!(filledAccidentList.size() == 0)) {
            LOG.info("Accidents was founded successfully");
        } else {
            LOG.error("Accidents wasn`t found. Empty list of accidents was returned");
        }
        return filledAccidentList;
    }

    public Optional<Accident> findByIdWithHBM(int accidentId) {
        Optional<Accident> nonNullAccident = accidentHBMRep.findById(accidentId);
        if (nonNullAccident.isPresent()) {
            LOG.info("Accident was found successfully");
        } else {
            LOG.error("Accident wasn`t found. Empty accident was returned");
        }
        return nonNullAccident;
    }

    public List<Accident> findAllJDBC() {
        List<Accident> filledAccidentList = accidentJDBCRepostiory.getAllAccidents();
        if (!(filledAccidentList.size() == 0)) {
            LOG.info("Accidents was founded successfully");
        } else {
            LOG.error("Accidents wasn`t found. Empty list of accidents was returned");
        }
        return filledAccidentList;
    }

    public boolean saveHBM(Accident accident, Integer[] ids) {
        boolean rsl = false;
        Optional<AccidentType> type = accidentTypeHBMRep.findById(accident.getAccidentType().getId());
        Set<Rule> rules;
        rules = accidentRuleHBMRep.getRequiredRulesInArray(ids);
        if (type.isPresent() || rules.size() > 0) {
            accident.setAccidentType(type.get());
            accident.setRules(rules);
            accidentHBMRep.save(accident);
            LOG.info("Accident was saved successfully");
            rsl = true;
        } else {
            LOG.error("Accident wasn`t saved");
        }
        return rsl;
    }


    public Optional<Accident> findByIdWithJDBC(int accidentId) {
        Optional<Accident> nonNullAccident = accidentJDBCRepostiory.findById(accidentId);
        if (nonNullAccident.isPresent()) {
            nonNullAccident.get().setRules(accidentRuleJdbcRep.getRequiredRules(accidentId));
            LOG.info("Accident was found successfully");
        } else {
            LOG.error("Accident wasn`t found. Empty accident was returned");
        }
        return nonNullAccident;
    }

    public boolean saveJDBC(Accident accident, int[] ids) {
        boolean rsl = false;
        Optional<Accident> nonNullAccident = Optional.ofNullable(accidentJDBCRepostiory.save(accident));
        if (nonNullAccident.isPresent() && ids.length > 0) {
            Accident tmpAccident =  nonNullAccident.get();
            tmpAccident.setRules(accidentRuleJdbcRep.getRequiredRules(ids));
            accidentRuleJdbcRep.setRequiredRulesInAccident(tmpAccident);
            LOG.info("Accident was saved successfully");
            rsl = true;
        } else {
            LOG.error("Accident wasn`t saved");
        }
        return rsl;
    }

    public boolean updateJDBC(Accident accident) {
        return accidentJDBCRepostiory.updateAccident(accident);
    }

    public boolean updateHBM(Accident accident, Integer[] ids) {
        accident.setRules(accidentRuleService.findRequiredRulesWithHBM(ids));
        return accidentHBMRep.update(accident);
    }

    public boolean deleteJDBC(int id) {
        return accidentJDBCRepostiory.delete(id);
    }

    public boolean deleteHBM(int id) {
        return accidentHBMRep.delete(id);
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
