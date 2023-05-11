package accidents.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "accidents_rules")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccidentsRules {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private int id;

    @JoinColumn(name = "accident_id")
    private int accidentId;
    @JoinColumn(name = "accident_rule_id")
    private int ruleId;
}
