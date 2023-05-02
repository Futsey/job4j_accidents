package accidents.repository;

import accidents.model.Accident;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private AtomicInteger count = new AtomicInteger(1);

    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        accidents.put(idGenerator(count), Accident.builder()
                .id(1)
                .name("Parking")
                .address("Pushkina 1")
                .text("Wrong parking")
                .build());
        accidents.put(idGenerator(count), Accident.builder()
                .id(2)
                .name("Traffic lite")
                .address("Lenina 24")
                .text("Run on red light")
                .build());
        accidents.put(idGenerator(count), Accident.builder()
                .id(3)
                .name("Crosswalk")
                .address("Pushkina 1")
                .text("Didn't miss a pedestrian")
                .build());
    }

    public Integer idGenerator(AtomicInteger count) {
        return count.getAndIncrement();
    }

    public List<Accident> findAll() {
        return new LinkedList<>(accidents.values());
    }

    public Accident findById(Integer accidentId) {
        return accidents.get(accidentId);
    }

    public Accident put(Accident accident) {
        return accidents.put(idGenerator(count), accident);
    }

    public Accident update(Accident accident) {
        return Accident.builder()
                .id(accident.getId())
                .name(accident.getName())
                .address(accident.getAddress())
                .text(accident.getText())
                .build();
    }

    /* Как вариант с проверкой на null
    public List<Accident> findAllOptional() {
        Optional<Map<Integer, Accident>> optionalMap = Optional.ofNullable(accidents);
        return optionalMap.isPresent() ? new LinkedList<Accident>(optionalMap.get().values()) : Collections.emptyList();
    }
     */
}
