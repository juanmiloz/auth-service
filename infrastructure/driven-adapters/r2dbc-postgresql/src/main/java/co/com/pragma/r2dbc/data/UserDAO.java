package co.com.pragma.r2dbc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(schema = "auth", name = "users")
public class UserDAO {

    @Id
    @Column("user_id")
    private UUID userId;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

    @Column("email")
    private String email;

    @Column("id")
    private String id;

    @Column("phone")
    private String phoneNumber;

    @Column("role_id")
    private UUID roleId;

    @Column("base_salary")
    private BigDecimal baseSalary;

    @Column("created_at")
    private LocalDateTime createdAt;

}
