package co.com.pragma.model.role;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    @Test
    @DisplayName("Builder crea Role con todos los campos")
    void builderCreatesRole() {
        UUID id = UUID.randomUUID();

        Role role = Role.builder()
                .roleId(id)
                .name("ADMIN")
                .description("Admin role")
                .build();

        assertThat(role.getRoleId()).isEqualTo(id);
        assertThat(role.getName()).isEqualTo("ADMIN");
        assertThat(role.getDescription()).isEqualTo("Admin role");
    }

    @Test
    @DisplayName("No-args + setters funcionan y getters devuelven valores")
    void noArgsAndSetters() {
        Role role = new Role();
        UUID id = UUID.randomUUID();

        role.setRoleId(id);
        role.setName("USER");
        role.setDescription("Standard user");

        assertThat(role.getRoleId()).isEqualTo(id);
        assertThat(role.getName()).isEqualTo("USER");
        assertThat(role.getDescription()).isEqualTo("Standard user");
    }

    @Test
    @DisplayName("toBuilder permite clonar y cambiar un campo")
    void toBuilderCloneAndModify() {
        Role original = Role.builder()
                .roleId(UUID.randomUUID())
                .name("MANAGER")
                .description("Manages stuff")
                .build();

        Role changed = original.toBuilder().name("LEAD").build();

        assertThat(changed.getName()).isEqualTo("LEAD");
        assertThat(changed.getDescription()).isEqualTo(original.getDescription());
        assertThat(changed.getRoleId()).isEqualTo(original.getRoleId());
        assertThat(changed).isNotSameAs(original);
    }

    @Test
    @DisplayName("equals/hashCode por @Data (mismos campos => iguales)")
    void equalsAndHashCode() {
        UUID id = UUID.randomUUID();

        Role a = Role.builder().roleId(id).name("QA").description("Quality").build();
        Role b = Role.builder().roleId(id).name("QA").description("Quality").build();

        assertThat(a).isEqualTo(b);
        assertThat(a).hasSameHashCodeAs(b);
    }

    @Test
    @DisplayName("toString incluye campos relevantes")
    void toStringContainsFields() {
        Role role = Role.builder().name("DEV").description("Developer").build();
        assertThat(role.toString()).contains("DEV").contains("Developer");
    }

}