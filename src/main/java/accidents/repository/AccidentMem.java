package accidents.repository;

import accidents.model.Accident;
import accidents.model.AccidentType;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final AtomicInteger count = new AtomicInteger(4);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        accidents.put(1, Accident.builder()
                .id(1)
                .name("Parking")
                .address("Pushkina 1")
                .text("Wrong parking")
                .accidentType(AccidentType.builder()
                        .name("Auto and parking")
                        .build())
                .build());
        accidents.put(2, Accident.builder()
                .id(2)
                .name("Traffic lite")
                .address("Lenina 24")
                .text("Run on red light")
                .accidentType(AccidentType.builder()
                        .name("PDD")
                        .build())
                .build());
        accidents.put(3, Accident.builder()
                .id(3)
                .name("Crosswalk")
                .address("Pushkina 1")
                .text("Didn't miss a pedestrian")
                .accidentType(AccidentType.builder()
                        .name("Auto and pedestrian")
                        .build())
                .build());
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public Accident findById(Integer accidentId) {
        return accidents.get(accidentId);
    }

    public Accident put(Accident accident) {
        int tmpId = count.getAndIncrement();
        accident.setId(tmpId);
        Accident tmpAccident = accidents.put(tmpId, accident);
        return tmpAccident;
    }

    public Accident update(Accident accident) {
        return accidents.put(accident.getId(), Accident.builder()
                .id(accident.getId())
                .name(accident.getName())
                .address(accident.getAddress())
                .text(accident.getText())
                .accidentType(accident.getAccidentType())
                .build());
    }

    /* Как вариант с проверкой на null
    public List<Accident> findAllOptional() {
        Optional<Map<Integer, Accident>> optionalMap = Optional.ofNullable(accidents);
        return optionalMap.isPresent() ? new LinkedList<Accident>(optionalMap.get().values()) : Collections.emptyList();
    }
     */
}
