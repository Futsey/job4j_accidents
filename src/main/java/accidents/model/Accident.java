package accidents.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accidents_accident")
@EqualsAndHashCode(of = {"name", "text", "address"})
@ToString(exclude = "rules")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Accident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String text;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_type_id")
    private AccidentType accidentType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "accident_rules",
            joinColumns = {@JoinColumn(name = "accident_id")},
            inverseJoinColumns = {@JoinColumn(name = "accident_rule_id")})
    private Set<Rule> rules;

    public void addRule(Rule r) {
        this.rules.add(r);
        r.getAccidents().add(this);
    }
}
