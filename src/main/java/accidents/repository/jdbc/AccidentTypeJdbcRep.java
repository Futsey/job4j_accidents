package accidents.repository.jdbc;

import accidents.model.AccidentType;
import accidents.model.Rule;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
            FROM accident_types
            """;

    private static final String FIND_TYPES_BY_ID = """
            SELECT *
            FROM accident_types
            WHERE id = ?
            """;

    public List<AccidentType> getAll() {
        return jdbc.query(FIND_ALL_TYPES, rowMapper);
    }

    public Optional<AccidentType> findById(int accidentTypeId) {
        return Optional.ofNullable(jdbc.queryForObject(FIND_TYPES_BY_ID, rowMapper, accidentTypeId));
    }
}
