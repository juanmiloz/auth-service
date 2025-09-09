package co.com.pragma.model.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class UserTest {

    @Test
    @DisplayName("NoArgsConstructor + setters + getters should work")
    void noArgs_setters_getters() {
        User u = new User();
        UUID id = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        u.setUserId(id);
        u.setFirstName("Juan");
        u.setLastName("Perez");
        u.setEmail("juan.perez@mail.com");
        u.setId("123");
        u.setPhoneNumber("555-1234");
        u.setBaseSalary(5_000_000d);
        u.setPassword("password");
        u.setRoleId(roleId);

        assertThat(u.getUserId()).isEqualTo(id);
        assertThat(u.getFirstName()).isEqualTo("Juan");
        assertThat(u.getLastName()).isEqualTo("Perez");
        assertThat(u.getEmail()).isEqualTo("juan.perez@mail.com");
        assertThat(u.getId()).isEqualTo("123");
        assertThat(u.getPhoneNumber()).isEqualTo("555-1234");
        assertThat(u.getBaseSalary()).isEqualTo(5_000_000d);
        assertThat(u.getPassword()).isEqualTo("password");
        assertThat(u.getRoleId()).isEqualTo(roleId);
    }

    @Test
    @DisplayName("AllArgsConstructor should assign all fields")
    void allArgsConstructor() {
        UUID id = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();

        User u = new User(
                id, "Juan", "Perez", "jp@mail.com",
                "123", "555-1234", 1_000_000d, "password", roleId
        );

        assertThat(u.getUserId()).isEqualTo(id);
        assertThat(u.getFirstName()).isEqualTo("Juan");
        assertThat(u.getLastName()).isEqualTo("Perez");
        assertThat(u.getEmail()).isEqualTo("jp@mail.com");
        assertThat(u.getId()).isEqualTo("123");
        assertThat(u.getPhoneNumber()).isEqualTo("555-1234");
        assertThat(u.getBaseSalary()).isEqualTo(1_000_000d);
        assertThat(u.getPassword()).isEqualTo("password");
        assertThat(u.getRoleId()).isEqualTo(roleId);
    }

    @Test
    @DisplayName("equals/hashCode should consider all fields (Lombok @Data)")
    void equals_hashCode() {
        UUID id = UUID.randomUUID();
        UUID roleId = UUID.randomUUID();
        UUID otherRole = UUID.randomUUID();

        User a = new User(id, "Juan", "Perez", "jp@mail.com", "123", "555-1234", 1_000_000d, "password", roleId);
        User b = new User(id, "Juan", "Perez", "jp@mail.com", "123", "555-1234", 1_000_000d, "password", roleId);
        User c = new User(id, "Juan", "Perez", "otro@mail.com", "123", "555-1234", 1_000_000d, "password", roleId);
        User d = new User(id, "Juan", "Perez", "jp@mail.com", "123", "555-1234", 1_000_000d, "password", otherRole);

        assertThat(a).isEqualTo(b);
        assertThat(a).hasSameHashCodeAs(b);

        assertThat(a).isNotEqualTo(c);
        assertThat(a.hashCode()).isNotEqualTo(c.hashCode());

        assertThat(a).isNotEqualTo(d);
        assertThat(a.hashCode()).isNotEqualTo(d.hashCode());
    }

    @Test
    @DisplayName("toString should not be null or blank and must contain class name")
    void toString_ok() {
        User u = new User();
        String s = u.toString();
        assertThat(s).isNotNull().isNotBlank();
        assertThat(s).contains("User");
    }
}