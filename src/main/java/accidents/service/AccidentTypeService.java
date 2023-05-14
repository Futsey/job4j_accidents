package accidents.service;

import accidents.model.AccidentType;
import accidents.repository.inmemory.AccidentTypeMem;
import accidents.repository.jdbc.AccidentTypeJdbcRep;
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
    private final AccidentTypeJdbcRep accidentTypeJdbcRep;

    private static final Logger LOG = LoggerFactory.getLogger(AccidentTypeService.class.getName());

    public List<AccidentType> findAllWithJDBC() {
        List<AccidentType> filledTypeList = accidentTypeJdbcRep.getAll();
        if (!(filledTypeList.size() == 0)) {
            LOG.info("Types was founded successfully");
        } else {
            LOG.error("Types wasn`t found. Empty list of accident type was returned");
        }
        return filledTypeList;
    }

    public AccidentType findByIdWithJDBC(int id) {
        AccidentType existType = new AccidentType();
        Optional<AccidentType> nonNullType = accidentTypeJdbcRep.findById(id);
        if (nonNullType.isPresent()) {
            existType = nonNullType.get();
            LOG.info("Type was found successfully");
        } else {
            LOG.error("Type wasn`t found. Empty accident type was returned");
        }
        return existType;
    }

    public List<AccidentType> findAll() {
        return accidentTypeMem.findAll();
    }

    public Optional<AccidentType> findById(int id) {
        return Optional.ofNullable(accidentTypeMem.findByTypeId(id));
    }
}
