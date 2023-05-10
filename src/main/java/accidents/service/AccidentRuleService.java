package accidents.service;

import accidents.model.Rule;
import accidents.repository.inmemory.RuleMem;
import accidents.repository.jdbc.AccidentRuleJdbcRep;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccidentRuleService {

    private final RuleMem ruleMem;
    private final AccidentRuleJdbcRep accidentRuleJdbcRep;

    public Set<Rule> findRequiredRules(String[] ids) {
        return ruleMem.findRequiredRules(ids);
    }

    public List<Rule> findAllRules() {
        return ruleMem.findAll();
    }

    public Set<Rule> findRequiredRulesWithJDBC(int accidentId) {
        return accidentRuleJdbcRep.getRequiredRules(accidentId);
    }
}
