package accidents.repository;

import accidents.model.Rule;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

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
            FROM rules
            """;

    private static final String FIND_REQUIRED_RULES = """
            SELECT *
            FROM rules
            WHERE id = ?
            """;

    public List<Rule> getAll() {
        return jdbc.query(FIND_ALL_RULES, rowMapper);
    }

    public List<Rule> getRequiredRules() {
        return jdbc.query(FIND_REQUIRED_RULES, rowMapper);
    }
}
