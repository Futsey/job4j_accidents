package accidents.service;

import accidents.model.Accident;
import accidents.repository.AccidentMem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;
    private final AccidentTypeService accidentTypeService;
    private final RuleService ruleService;

    public List<Accident> findAll() {
        return accidentMem.findAll();
    }

    public Optional<Accident> findById(Integer accidentId) {
        return Optional.ofNullable(accidentMem.findById(accidentId));
    }

    public Optional<Accident> save(Accident accident, String[] ids, String accidentId) {
        accident.setRules(new HashSet<>(ruleService.findRequiredRules(ids).get()));
        accident.setAccidentType(accidentTypeService.findById(accidentId).get());
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
