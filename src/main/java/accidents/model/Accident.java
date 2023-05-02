package accidents.model;

import lombok.*;

import javax.persistence.*;

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

    private AccidentType accidentType;
}
