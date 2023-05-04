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

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypeMem.findById(id));
    }
}
