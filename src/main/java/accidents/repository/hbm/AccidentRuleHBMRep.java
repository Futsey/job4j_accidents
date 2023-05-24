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

    private static final String FIND_REQUIRED_RULES_BY_ACCIDENT = """
            FROM Rule r
            WHERE r.id
            IN :fAccId
            """;

    private static final String FIND_REQUIRED_RULES_IN_ARRAY = """
            FROM Rule r
            WHERE r.id
            IN (:fRId)
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

    public Set<Rule> getRequiredRules(int accidentId) {
        return new HashSet<>(crudRep.query(
                FIND_REQUIRED_RULES_BY_ACCIDENT, Rule.class,
                Map.of("fAccId", accidentId)));
    }

    public Set<Rule> getRequiredRulesInArray(Integer[] ids) {
        List<Integer> numbers = Arrays.asList(ids);
        return new HashSet<>(crudRep.query(
                FIND_REQUIRED_RULES_IN_ARRAY, Rule.class, Map.of("fRId", numbers)));
    }
}
