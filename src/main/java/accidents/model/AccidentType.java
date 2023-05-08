package accidents.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "accident_types")
@EqualsAndHashCode()
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccidentType {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
}
