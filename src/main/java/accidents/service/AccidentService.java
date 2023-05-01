package accidents.service;

import accidents.model.Accident;
import accidents.repository.AccidentMem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccidentService {

    private final AccidentMem accidentMem;

    public List<Accident> findAllInTestList() {
        accidentMem.addTestAccidents();
        return accidentMem.findAll();
    }

    public List<Accident> findAllInList() {
        return accidentMem.findAll();
    }

    public static void main(String[] args) {
        AccidentService accidentService = new AccidentService(new AccidentMem());
        accidentService.findAllInTestList();
    }
}
