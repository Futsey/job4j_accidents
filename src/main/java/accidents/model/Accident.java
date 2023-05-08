package accidents.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "accidents_accident")
@EqualsAndHashCode()
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Accident {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String text;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accident_type_id")
    private AccidentType accidentType;

    @OneToMany
    @JoinColumn(name = "accident_rule_id")
    private Set<Rule> rules;
}
