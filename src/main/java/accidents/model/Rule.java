package accidents.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "accident_rules")
@EqualsAndHashCode()
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Rule {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
