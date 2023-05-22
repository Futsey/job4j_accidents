package accidents.repository.hbm;

import accidents.model.Rule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentRuleHBMRep {

    private final CRUDRep crudRep;

    private static final String FIND_ALL_RULES = """
            FROM Rule
            """;

    private static final String FIND_RULE_BY_ID = """
            FROM Rule r
            WHERE r.id = :fId
            """;

    private static final String FIND_REQUIRED_RULES = """
            FROM Rule r
            WHERE r.id
            IN (:fId)
            """;

    public List<Rule> getAll() {
        return crudRep.query(FIND_ALL_RULES, Rule.class);
    }

    public Optional<Rule> getRuleById(int ruleId) {
        return crudRep.optional(
                FIND_RULE_BY_ID, Rule.class,
                Map.of("fId", ruleId)
        );
    }

    public Set<Rule> getRequiredRules(int[] ids) {
        Set<Rule> ruleHashSet = new HashSet<>();
        for (int i = 0; i < ids.length; i++) {
            ruleHashSet.add(getRuleById(ids[i]).get());
        }
        return ruleHashSet;

    }

    public Set<Rule> getRequiredRules(int accidentId) {
        return new HashSet<>(crudRep.query(
                FIND_REQUIRED_RULES, Rule.class,
                Map.of("fId", accidentId)));
    }
}
