package accidents.service;

import accidents.model.Rule;
import accidents.repository.inmemory.RuleMem;
import accidents.repository.jdbc.AccidentRuleJdbcRep;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AccidentRuleService {

    private final AccidentRuleJdbcRep accidentRuleJdbcRep;
    private final RuleMem ruleMem;

    private static final Logger LOG = LoggerFactory.getLogger(AccidentRuleService.class.getName());


    public List<Rule> findAllRulesJDBC() {
        return accidentRuleJdbcRep.getAll();
    }

    public Set<Rule> findRequiredRulesWithJDBC(int accidentId) {
        return accidentRuleJdbcRep.getRequiredRules(accidentId);
    }

    public Set<Rule> findRequiredRulesWithJDBC(int[] rIds) {
        return accidentRuleJdbcRep.getRequiredRules(rIds);
    }

    public void setRequiredRulesWithJDBC(int accidentId, int[] ids) {
        if (accidentRuleJdbcRep.setRequiredRulesInAccident(accidentId, ids)) {
            LOG.info("Rules added successful");
        } else {
            LOG.error("ERROR in adding rules");
        }
    }

    public Set<Rule> findRequiredRules(String[] ids) {
        return ruleMem.findRequiredRules(ids);
    }

    public List<Rule> findAllRules() {
        return ruleMem.findAll();
    }
}
