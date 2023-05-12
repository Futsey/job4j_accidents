package accidents.service;

import accidents.model.AccidentType;
import accidents.repository.inmemory.AccidentTypeMem;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccidentTypeService {

    private final AccidentTypeMem accidentTypeMem;

    private static final Logger LOG = LoggerFactory.getLogger(AccidentTypeService.class.getName());

    public List<AccidentType> findAllWithJDBC() {
        return accidentTypeMem.findAll();
    }

    public AccidentType findByIdWithJDBC(int id) {
        return accidentTypeMem.findByTypeId(id);
    }

    public List<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypeMem.findByTypeId(id));
    }
}
