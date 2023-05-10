package accidents.repository.inmemory;

import accidents.model.AccidentType;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class AccidentTypeMem {

    private final Map<Integer, AccidentType> accidentTypes = new HashMap<>();

    public AccidentTypeMem() {
        accidentTypes.put(1, new AccidentType(1, "Две машины"));
        accidentTypes.put(2, new AccidentType(2, "Машина и человек"));
        accidentTypes.put(3, new AccidentType(3, "Машина и велосипед"));
    }

    public List<AccidentType> findAll() {
        return new ArrayList<>(accidentTypes.values());
    }

    public AccidentType findByTypeId(int id) {
        List<AccidentType> types = findAll();
        AccidentType accidentType = types.stream()
                .filter(idFound -> idFound.getId() == id)
                .findFirst()
                .orElseThrow();
        System.out.println(accidentType);
        return accidentType;
    }
}
