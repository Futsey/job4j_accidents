package accidents.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "accident_rules")
@EqualsAndHashCode(of = "name")
@ToString(exclude = "accidents")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @Builder.Default
    @ManyToMany(mappedBy = "rules")
    private Set<Accident> accidents = new HashSet<>();
}
