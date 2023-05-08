package accidents.repository;

import accidents.model.AccidentType;
import accidents.model.Rule;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentTypeJdbcRep {

    private final JdbcTemplate jdbc;

    private final RowMapper<AccidentType> rowMapper = (rs, rowNum) -> AccidentType.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .build();

    private static final String FIND_ALL_TYPES = """
            SELECT *
            FROM types
            """;

    private static final String FIND_REQUIRED_TYPES = """
            SELECT *
            FROM types
            WHERE id = ?
            """;

    public List<AccidentType> getAll() {
        return jdbc.query(FIND_ALL_TYPES, rowMapper);
    }

    public List<AccidentType> getRequiredRules() {
        return jdbc.query(FIND_REQUIRED_TYPES, rowMapper);
    }
}
