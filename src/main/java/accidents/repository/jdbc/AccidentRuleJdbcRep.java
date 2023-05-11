package accidents.repository.jdbc;

import accidents.model.Accident;
import accidents.model.AccidentsRules;
import accidents.model.Rule;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AccidentRuleJdbcRep {

    private final JdbcTemplate jdbc;

    private final RowMapper<Rule> rowMapper = (rs, rowNum) -> Rule.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .build();

    private static final String FIND_ALL_RULES = """
            SELECT *
            FROM accident_rules
            """;

    private static final String FIND_RULE_BY_ID = """
            SELECT * 
            FROM accident_rules
            WHERE id = ?
            """;

    private static final String FIND_REQUIRED_RULES = """
            SELECT *
            FROM accident_rules
            WHERE id
            IN (
                SELECT accident_rule_id
                FROM accidents_rules 
                WHERE accident_id = ?)
            """;

    private static final String SET_REQUIRED_RULES = """
            INSERT INTO accidents_rules (accident_id, accident_rule_id)
            VALUES (?, ?)
            """;

    public List<Rule> getAll() {
        return jdbc.query(FIND_ALL_RULES, rowMapper);
    }

    /**TODO Get rid from hardcode
     * .get(0)
     * @param ruleId
     * @return
     */
    public Rule getRuleById(int ruleId) {
        return jdbc.query(FIND_RULE_BY_ID, rowMapper, ruleId).get(0);
    }

    public Set<Rule> getRequiredRules(int accidentId) {
        Set<Rule> som = new HashSet<>(jdbc.query(FIND_REQUIRED_RULES, rowMapper, accidentId));
        return  som;
    }

    /**TODO Rewrite to stream API
     *
     * @param ids
     * @return
     */
    public Set<Rule> getRequiredRules(int[] ids) {
        Set<Rule> ruleHashSet = new HashSet<>();
        for(int i = 0; i < ids.length; i++) {
            ruleHashSet.add(getRuleById(ids[i]));
        }
        return ruleHashSet;
    }

    public boolean setRequiredRulesInAccident(int accidentId, int[] ids) {
        boolean rsl = false;
        for (Integer ruleId : ids) {
            jdbc.query(SET_REQUIRED_RULES, (rs, rowNum) -> AccidentsRules.builder()
                    .accidentId(accidentId)
                    .ruleId(ruleId)
                    .build());
        }
        return true;
    }
}
