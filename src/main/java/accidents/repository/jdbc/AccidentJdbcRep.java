package accidents.repository.jdbc;

import accidents.model.Accident;
import accidents.model.AccidentType;
import accidents.model.Rule;
import accidents.service.AccidentRuleService;
import accidents.service.AccidentTypeService;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class AccidentJdbcRep {

    private final AccidentTypeService accidentTypeService;
    private final AccidentRuleService accidentRuleService;
    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> fullMapper = (rs, rowNum) -> Accident.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .text(rs.getString("text"))
            .address(rs.getString("address"))
            .accidentType(getType(rs.getInt("accident_type_id")))
            .rules(getRulesInAccident(rs.getInt("id")))
            .build();

    private final RowMapper<Accident> outOfRuleMapper = (rs, rowNum) -> Accident.builder()
            .id(16)
            .name(rs.getString("name"))
            .text(rs.getString("text"))
            .address(rs.getString("address"))
            .accidentType(getType(rs.getInt("accident_type_id")))
            .build();

    private static final String CHECK_MAX_ID = """
            SELECT
            MAX(id)
            FROM accidents
            """;

    private static final String FIND_ALL_ACCIDENTS = """
            SELECT *
            FROM accidents
            """;

    private static final String SAVE_ACCIDENT = """
            INSERT INTO accidents (id, name, text, address, accident_type_id)
            VALUES (?, ?, ?, ?, ?)
            """;

    public List<Accident> getAll() {
        return jdbc.query(FIND_ALL_ACCIDENTS, fullMapper);
    }

    public boolean save(Accident accident) {
        jdbc.query(SAVE_ACCIDENT, outOfRuleMapper);
        return true;
    }

    public int getMaxId(String sql) {
        return jdbc.queryForObject(sql, Integer.class) + 1;
    }

    private AccidentType getType(int typeId) {
        return accidentTypeService.findByIdWithJDBC(typeId);
    }

    private Set<Rule> getRulesInAccident(int accidentId) {
        return accidentRuleService.findRequiredRulesWithJDBC(accidentId);
    }
}
