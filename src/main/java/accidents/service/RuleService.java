package accidents.service;

import accidents.model.Rule;
import accidents.repository.RuleMem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleMem ruleMem;

    public Optional<Set<Rule>> findRequiredRules(String[] ids) {
        return Optional.ofNullable(ruleMem.findRequiredRules(ids));
    }

    public List<Rule> findAllRules() {
        return ruleMem.findAll();
    }
}
