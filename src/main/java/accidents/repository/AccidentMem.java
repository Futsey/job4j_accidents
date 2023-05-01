package accidents.repository;

import accidents.model.Accident;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class AccidentMem {

    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public void addTestAccidents() {
        accidents.put(1, Accident.builder()
                        .name("Parking")
                        .address("Pushkina 1")
                        .text("Wrong parking")
                .build());
        accidents.put(2, Accident.builder()
                .name("Traffic lite")
                .address("Lenina 24")
                .text("Run on red light")
                .build());
        accidents.put(3, Accident.builder()
                .name("Crosswalk")
                .address("Pushkina 1")
                .text("Didn't miss a pedestrian")
                .build());
    }

    public List<Accident> findAll() {
        List<Accident> accidentList = new LinkedList<>();
        for (Map.Entry<Integer, Accident> entry : accidents.entrySet()) {
                accidentList.add(entry.getValue());
        }
        return accidentList;
    }
}
