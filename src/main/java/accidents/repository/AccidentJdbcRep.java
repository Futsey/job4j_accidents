package accidents.repository;

import accidents.model.Accident;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class AccidentJdbcRep {

    private final JdbcTemplate jdbc;


    private final RowMapper<Accident> rowMapper = (rs, rowNum) -> Accident.builder()
            .id(rs.getInt("id"))
            .name(rs.getString("name"))
            .name(rs.getString("text"))
            .name(rs.getString("address"))
//            .name(rs.getString("accidentType"))
//            .name(rs.getString("rules"))
            .build();

    private static final String FIND_ALL_ACCIDENTS = """
            SELECT *
            FROM accidents
            """;

    private static final String SAVE_ACCIDENT = """
            INSERT INTO accidents (id, name)
            VALUES (?, ?, ?, ?, ?, ?)
            """;

    public boolean save(Accident accident) {
        return jdbc.update(SAVE_ACCIDENT, rowMapper) > 0;
    }

    public List<Accident> getAll() {
        List<Accident> accidentList = jdbc.query(FIND_ALL_ACCIDENTS, rowMapper);
        return accidentList;
    }

//    public Accident findById(Integer accidentId) {
//        return jdbc.queryForObject(
//                "select * from accidents where id = ?",
//                (resultSet, rowNum) -> {
//                    Accident accident = new Accident();
//                    accident.setName(resultSet.getString("first_name"));
//                    accident.setLastName(resultSet.getString("last_name"));
//                    return accident;
//                },
//                1212L);
//    }
}
