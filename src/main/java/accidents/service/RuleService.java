package accidents.service;

import accidents.model.Rule;
import accidents.repository.RuleMem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleMem ruleMem;

    public List<Rule> findRequiredRules(String[] ids) {
        List<Rule> filledRulesList = ruleMem.findAll();
        List<Rule> rulesToReturn = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            String found = filledRulesList.get(i).getName();
            if (found.equals(ids[i])) {
                rulesToReturn.add(filledRulesList.get(i));
            }
        }
        return rulesToReturn;
    }

    public List<Rule> findAllRules() {
        return ruleMem.findAll();
    }
}
