package accidents.repository;

import accidents.model.Accident;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class AccidentMem {

    private AtomicInteger count = new AtomicInteger(0);

    Map<Integer, Accident> accidents = new ConcurrentHashMap<>();

    public AccidentMem() {
        accidents.put(1, Accident.builder()
                .id(idGenerator(count))
                .name("Parking")
                .address("Pushkina 1")
                .text("Wrong parking")
                .build());
        accidents.put(2, Accident.builder()
                .id(idGenerator(count))
                .name("Traffic lite")
                .address("Lenina 24")
                .text("Run on red light")
                .build());
        accidents.put(3, Accident.builder()
                .id(idGenerator(count))
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

    /* Как вариант с проверкой на null
    public List<Accident> findAllOptional() {
        Optional<Map<Integer, Accident>> optionalMap = Optional.ofNullable(accidents);
        return optionalMap.isPresent() ? new LinkedList<Accident>(optionalMap.get().values()) : Collections.emptyList();
    }
     */
}
