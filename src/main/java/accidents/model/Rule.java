package accidents.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class Rule {

    @EqualsAndHashCode.Include
    private int id;

    private String name;
}
