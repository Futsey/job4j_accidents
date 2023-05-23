package accidents.repository.hbm;

import accidents.model.Rule;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

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

    public Set<Rule> getRequiredRulesOldVers(Integer[] ids) {
        List<Integer> numbers = Arrays.asList(ids);
        String tmpQuery = numbers.stream()
                .map(n -> n.toString())
                .collect(Collectors.joining(","));
        return new HashSet<>(crudRep.query(
                FIND_REQUIRED_RULES_IN_ARRAY, Rule.class, Map.of("fAccId", tmpQuery)));
    }

    public Set<Rule> getRequiredRulesTQVers(Integer[] ids) {
        List<Integer> numbers = Arrays.asList(ids);
        String tmpQuery = numbers.stream()
                .map(n -> n.toString())
                .collect(Collectors.joining(","));
        TypedQuery<Rule> query = crudRep.typedQuery(
                FIND_REQUIRED_RULES_IN_ARRAY, Rule.class);
        query.setParameter("fRId", tmpQuery);
        List<Rule> result = query.getResultList();
        System.out.println();
        return new HashSet<>(result);
    }
}
