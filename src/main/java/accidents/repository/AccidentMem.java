package accidents.repository;

import accidents.model.Accident;
import accidents.model.AccidentType;
import accidents.model.Rule;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private final AtomicInteger count = new AtomicInteger(4);

    private final Map<Integer, Accident> accidents = new ConcurrentHashMap<>();
    private final Map<Integer, AccidentType> accidentTypes = Map.of(
            1, new AccidentType(1, "Две машины"),
            2, new AccidentType(2, "Машина и человек"),
            3, new AccidentType(3, "Машина и велосипед"));

    public AccidentMem() {
        accidents.put(1, Accident.builder()
                .id(1)
                .name("Parking")
                .address("Pushkina 1")
                .text("Wrong parking")
                .accidentType(accidentTypes.get(1))
                .rules(Set.of(Rule.builder()
                        .name("1.1.1 Parking rule")
                        .build()))
                .build());
        accidents.put(2, Accident.builder()
                .id(2)
                .name("Traffic lite")
                .address("Lenina 24")
                .text("Run on red light")
                .accidentType(accidentTypes.get(2))
                .rules(Set.of(Rule.builder()
                        .name("1.1.2 Traffic lite rule")
                        .build()))
                .build());
        accidents.put(3, Accident.builder()
                .id(3)
                .name("Crosswalk")
                .address("Pushkina 1")
                .text("Didn't miss a pedestrian")
                .accidentType(accidentTypes.get(3))
                .rules(Set.of(Rule.builder()
                        .name("1.1.3 Crosswalk rule")
                        .build()))
                .build());
    }

    public List<Accident> findAll() {
        return new ArrayList<>(accidents.values());
    }

    public List<AccidentType> findAllTypes() {
        return new ArrayList<>(accidentTypes.values());
    }

    public Accident findById(Integer accidentId) {

        return accidents.get(accidentId);
    }

    public Accident save(Accident accident) {
        int tmpId = count.getAndIncrement();
        accident.setId(tmpId);

        return accidents.put(tmpId, accident);
    }

    public boolean update(Accident accident) {
        return accidents.computeIfPresent(accident.getId(),
                (key, oldAccident) ->
                        Accident.builder()
                                .id(oldAccident.getId())
                                .name(oldAccident.getName())
                                .address(oldAccident.getAddress())
                                .text(oldAccident.getText())
                                .accidentType(oldAccident.getAccidentType())
                                .rules(oldAccident.getRules())
                                .build()) != null;
    }


}
