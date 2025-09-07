package co.com.pragma.model.role;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Role {

    private UUID roleId;
    private String name;
    private String description;

}
