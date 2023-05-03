package accidents.repository;

import accidents.model.Rule;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class RuleMem {

    private final List<Rule> rules = new ArrayList<>();

    public RuleMem() {
        rules.add(new Rule(1, "1.1.1 Parking rule"));
        rules.add(new Rule(2, "1.1.2 Traffic lite rule"));
        rules.add(new Rule(3, "1.1.3 Crosswalk rule"));
    }

    public List<Rule> findAll() {
        return List.copyOf(rules);
    }
}
