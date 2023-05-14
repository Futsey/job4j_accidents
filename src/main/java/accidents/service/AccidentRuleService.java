package accidents.service;

import accidents.model.Accident;
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
        List<Rule> filledRuleList = accidentRuleJdbcRep.getAll();
        if (!(filledRuleList.size() == 0)) {
            LOG.info("Rules was founded successfully");
        } else {
            LOG.error("Rules wasn`t found. Empty list of rules was returned");
        }
        return filledRuleList;
    }

    public Set<Rule> findRequiredRulesWithJDBC(int accidentId) {
        Set<Rule> filledRuleSet = accidentRuleJdbcRep.getRequiredRules(accidentId);
        if (!(filledRuleSet.size() == 0)) {
            LOG.info("Rules was founded successfully");
        } else {
            LOG.error("Rules wasn`t found. Empty set of rules was returned");
        }
        return filledRuleSet;
    }

    public Set<Rule> findRequiredRulesWithJDBC(int[] rIds) {
        Set<Rule> filledRuleSet = accidentRuleJdbcRep.getRequiredRules(rIds);
        if (!(filledRuleSet.size() == 0)) {
            LOG.info("Rules was founded successfully");
        } else {
            LOG.error("Rules wasn`t found. Empty set of rules was returned");
        }
        return filledRuleSet;
    }

    public void setRequiredRulesWithJDBC(Accident accident) {
        if (accidentRuleJdbcRep.setRequiredRulesInAccident(accident)) {
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
