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

import java.util.List;
import java.util.Set;

@Repository
@AllArgsConstructor
public class AccidentJdbcRep {

    private final AccidentTypeService accidentTypeService;
    private final AccidentRuleService accidentRuleService;
    private final JdbcTemplate jdbc;

    private final RowMapper<Accident> rowMapper = (rs, rowNum) -> Accident.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .text(rs.getString("text"))
            .address(rs.getString("address"))
            .accidentType(getType(rs.getInt("accident_type_id")))
            .rules(getRulesInAccident(rs.getInt("id")))
            .build();

    private static final String FIND_ALL_ACCIDENTS = """
            SELECT *
            FROM accidents
            """;

    private static final String SAVE_ACCIDENT = """
            INSERT INTO accidents (id, name)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    private AccidentType getType(int typeId) {
        return accidentTypeService.findByIdWithJDBC(typeId);
    }

    private Set<Rule> getRulesInAccident(int accidentId) {
        return accidentRuleService.findRequiredRulesWithJDBC(accidentId);
    }

    public boolean save(Accident accident) {
        return jdbc.update(SAVE_ACCIDENT, rowMapper) > 0;
    }

    public List<Accident> getAll() {
        Set<Rule> rules = getRulesInAccident(1);
        List<Accident> accidentList = jdbc.query(FIND_ALL_ACCIDENTS, rowMapper);
        Accident accident0 = accidentList.get(0);
        Accident accident1 = accidentList.get(1);
        Accident accident2 = accidentList.get(2);
        System.out.println(accident0.getRules());
        System.out.println(accident1.getRules());
        System.out.println(accident2.getRules());
        return accidentList;
    }
}
