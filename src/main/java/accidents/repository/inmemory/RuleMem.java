package accidents.repository.inmemory;

import accidents.model.Rule;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class RuleMem {

    private final Map<Integer, Rule> rules = new HashMap<>();

    public RuleMem() {
        rules.put(1, new Rule(1, "1.1.1 Parking rule", new HashSet<>()));
        rules.put(2, new Rule(2, "1.1.2 Traffic lite rule", new HashSet<>()));
        rules.put(3, new Rule(3, "1.1.3 Crosswalk rule", new HashSet<>()));
    }

    public List<Rule> findAll() {
        return new ArrayList<>(rules.values());
    }

    public Set<Rule> findRequiredRules(String[] ids) {
        return Arrays
                .stream(ids)
                .map(id -> rules.get(Integer.parseInt(id)))
                .collect(Collectors.toSet());
    }
}
