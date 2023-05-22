package accidents.repository.hbm;

import accidents.model.Rule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
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
            SELECT r.id, r.name
            FROM Rule r
            WHERE r.id
            IN (
                SELECT ar.ruleId
                FROM AccidentsRules ar
                WHERE ar.accidentId = :fAccId)
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

    public Set<Rule> getRequiredRules(Integer[] ids) {
        return new HashSet<>(crudRep.query(
                FIND_REQUIRED_RULES_IN_ARRAY, Rule.class, Map.of("fAccId", ids)));
    }
}
