package accidents.repository.jdbc;

import accidents.model.Accident;
import accidents.model.AccidentType;
import accidents.model.Rule;
import accidents.service.AccidentRuleService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
@AllArgsConstructor
public class AccidentJdbcRep {

    private final AccidentRuleService accidentRuleService;
    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> fullMapper = (rs, rowNum) -> Accident.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .text(rs.getString("text"))
            .address(rs.getString("address"))
            .accidentType(AccidentType.builder()
                    .id(rs.getInt("typeId"))
                    .name(rs.getString("typeName"))
                    .build())
            .rules(new HashSet<>(Collections.singleton(Rule.builder()
                    .id(rs.getInt("ruleId"))
                    .name(rs.getString("ruleName"))
                    .build())))
            .build();

    private final static String FIND_ALL_ACCIDENTS_JOIN_ACCIDENT_TYPES_AND_ACCIDENT_RULES = """
            SELECT a.id, a.name, a.text, a.address,
                   at.id AS typeId, at.name AS typeName,
                   ar.id AS ruleId, ar.name AS ruleName
            FROM accidents AS a
                     JOIN accident_types AS at ON at.id = a.accident_type_id
                     JOIN accident_rules AS ar ON a.id = ar.id
            ORDER BY a.id;
            """;

    private final static String FIND_ACCIDENTS_BY_ID_JOIN_ACCIDENT_TYPES = """
            SELECT a.id, a.name, a.text, a.address, at.id AS typeId, at.name AS typeName
            FROM accidents AS a
            JOIN accident_types AS at ON at.id = a.accident_type_id
            WHERE a.id = ?
            """;

    private static final String SAVE_ACCIDENT = """
            INSERT INTO accidents (name, text, address, accident_type_id)
            VALUES (?, ?, ?, ?)
            """;

    public List<Accident> getAll() {
        return jdbc.query(FIND_ALL_ACCIDENTS_JOIN_ACCIDENT_TYPES_AND_ACCIDENT_RULES, fullMapper);
    }

    public Optional<Accident> findById(int accidentId) {
        return Optional.ofNullable(jdbc.queryForObject(
                FIND_ACCIDENTS_BY_ID_JOIN_ACCIDENT_TYPES,
                new Object[] {accidentId},
                fullMapper));
    }

    public Accident save(Accident accident) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SAVE_ACCIDENT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, accident.getName());
            ps.setString(2, accident.getText());
            ps.setString(3, accident.getAddress());
            ps.setInt(4, accident.getAccidentType().getId());
            return ps;
        }, keyHolder);
        accident.setId((Integer) keyHolder.getKeys().get("id"));
        accident.setRules(getRulesInAccident(accident.getId()));
        return accident;
    }

    private Set<Rule> getRulesInAccident(int accidentId) {
        return accidentRuleService.findRequiredRulesWithJDBC(accidentId);
    }
}
