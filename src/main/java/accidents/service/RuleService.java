package accidents.service;

import accidents.model.Rule;
import accidents.repository.RuleMem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RuleService {

    private final RuleMem ruleMem;

    public Optional<List<Rule>> findRequiredRules(String[] ids) {
        return Optional.ofNullable(ruleMem.findRequiredRules(ids));
    }

    public List<Rule> findAllRules() {
        return ruleMem.findAll();
    }
}
