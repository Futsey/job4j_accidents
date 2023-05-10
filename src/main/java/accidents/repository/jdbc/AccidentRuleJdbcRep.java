package accidents.repository.jdbc;

import accidents.model.Rule;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<Rule> getAll() {
        return jdbc.query(FIND_ALL_RULES, rowMapper);
    }

    public Rule getRuleById(int ruleId) {
        return jdbc.query(FIND_RULE_BY_ID, rowMapper, ruleId).get(ruleId);
    }

    public Set<Rule> getRequiredRules(int accidentId) {
        return  new HashSet<>(jdbc.query(FIND_REQUIRED_RULES, rowMapper, accidentId));
    }
}
