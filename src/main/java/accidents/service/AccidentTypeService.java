package accidents.service;

import accidents.model.AccidentType;
import accidents.repository.AccidentTypeMem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeMem accidentTypeMem;

    public List<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }

    public Optional<AccidentType> findById(String id) {
        Optional.ofNullable(accidentTypeMem.findByTypeId(Integer.parseInt(id)));
        System.out.println();
        return Optional.ofNullable(accidentTypeMem.findByTypeId(Integer.parseInt(id)));
    }
}
