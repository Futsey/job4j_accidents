package accidents.repository;

import accidents.model.Rule;
import org.apache.logging.log4j.message.Message;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RuleMem {

    private final Map<Integer, Rule> rules = new HashMap<>();

    public RuleMem() {
        rules.put(1, new Rule(1, "1.1.1 Parking rule"));
        rules.put(2, new Rule(2, "1.1.2 Traffic lite rule"));
        rules.put(3, new Rule(3, "1.1.3 Crosswalk rule"));
    }

    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    public List<Rule> findRequiredRules(String[] ids) {
        List<Rule> rules = findAll();
        List<Rule> resultList = new ArrayList<>();
        for (String idToFind : ids) {
            resultList.add(rules.stream()
                    .filter(id -> id.getId() == Integer.parseInt(idToFind))
                    .findFirst()
                    .orElseThrow());
        }
        return resultList;
    }
}
