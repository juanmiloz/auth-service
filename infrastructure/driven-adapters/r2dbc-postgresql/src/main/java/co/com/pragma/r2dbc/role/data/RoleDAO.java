package co.com.pragma.r2dbc.role.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "auth", name = "roles")
public class RoleDAO {

    @Id
    @Column("role_id")
    private UUID roleId;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

}
