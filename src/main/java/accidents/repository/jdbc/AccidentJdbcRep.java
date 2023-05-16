package accidents.repository.jdbc;

import accidents.model.Accident;
import accidents.model.AccidentType;
import accidents.model.Rule;
import accidents.service.AccidentRuleService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
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

    private final ResultSetExtractor<Map<Integer, Accident>> extractor = (rs) -> {
        Map<Integer, Accident> result = new HashMap<>();
        while (rs.next()) {
            Accident accidentInDB = Accident.builder()
                    .name(rs.getString("name"))
                    .text(rs.getString("text"))
                    .address(rs.getString("address"))
                    .accidentType(AccidentType.builder()
                            .id(rs.getInt("accident_type_id"))
                            .name(rs.getString("typeName"))
                            .build())
                    .rules(new HashSet<>())
                    .build();
            accidentInDB.setId(rs.getInt("id"));
            result.putIfAbsent(accidentInDB.getId(), accidentInDB);
            result.get(accidentInDB.getId()).addRule(
                    Rule.builder()
                            .id(rs.getInt("ruleId"))
                            .name(rs.getString("ruleName"))
                            .build()
                    );
        }
        return result;
    };

    private final static String FIND_ALL_ACCIDENTS_JOIN_ACCIDENT_TYPES_AND_ACCIDENT_RULES = """
            SELECT a.id, a.name, text, address, accident_type_id, t.name as typeName,
                   r.id as ruleId, r.name as ruleName from accidents as a
                   JOIN accidents_rules ar on a.id = ar.accident_id
                   JOIN accident_rules r on ar.accident_rule_id = r.id
                   JOIN accident_types t on a.accident_type_id = t.id
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

    private static final String UPDATE_ACCIDENT = """
            UPDATE accidents 
            SET name = ?, text = ?, address = ? 
            WHERE id = ?
            """;

    private final static String DELETE_ACCIDENT = """
            DELETE 
            FROM accidents
            WHERE id = ?
            """;

    public List<Accident> getAllAccidents() {
        Collection<Accident> values = jdbc.query(
                FIND_ALL_ACCIDENTS_JOIN_ACCIDENT_TYPES_AND_ACCIDENT_RULES,
                extractor
                ).values();
        return values.size() > 0 ? new ArrayList<>(values) : new ArrayList<>();
    }

    public Optional<Accident> findById(int accidentId) {
        return Optional.ofNullable(jdbc.queryForObject(
                FIND_ACCIDENTS_BY_ID_JOIN_ACCIDENT_TYPES,
                new Object[] {accidentId},
                (rs, rowNum) -> Accident.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .text(rs.getString("text"))
                        .address(rs.getString("address"))
                        .accidentType(AccidentType.builder()
                                .id(rs.getInt("typeId"))
                                .name(rs.getString("typeName"))
                                .build())
                        .rules(new HashSet())
                        .build()));
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

    public boolean updateAccident(Accident accident) {
        return jdbc.update(UPDATE_ACCIDENT,
                accident.getName(),
                accident.getText(),
                accident.getAddress(),
                accident.getId()
        ) > 0;
    }

    public boolean delete(int id) {
        return jdbc.update(DELETE_ACCIDENT, id) > 0;
    }
}
