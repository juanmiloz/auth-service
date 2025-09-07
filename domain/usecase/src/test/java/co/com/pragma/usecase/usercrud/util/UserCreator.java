package co.com.pragma.usecase.usercrud.util;

import co.com.pragma.model.user.User;

import java.util.UUID;

public class UserCreator {

    public static User createValidUser() {
        return new User(
                null,
                "Juan",
                "Perez",
                "juan.perez@mail.com",
                "123",
                "555-1234",
                5_000_000d,
                "password"
        );
    }

    public static User createUserWithBlankFirstName() {
        return new User(
                null,
                "   ",
                "Perez",
                "juan.perez@mail.com",
                "123",
                "555-1234",
                5_000_000d,
                "password"
        );
    }

    public static User createUserWithBlankLastName() {
        return new User(
                null,
                "Juan",
                "   ",
                "juan.perez@mail.com",
                "123",
                "555-1234",
                5_000_000d,
                "password"
        );
    }

    public static User createUserWithBlankEmail() {
        return new User(
                null,
                "Juan",
                "Perez",
                "   ",
                "123",
                "555-1234",
                5_000_000d,
                "password"
        );
    }

    public static User createUserWithInvalidEmail() {
        return new User(
                null,
                "Juan",
                "Perez",
                "not-an-email",
                "123",
                "555-1234",
                5_000_000d,
                "password"
        );
    }

    public static User createUserWithNullSalary() {
        return new User(
                null,
                "Juan",
                "Perez",
                "juan.perez@mail.com",
                "123",
                "555-1234",
                null,
                "password"
        );
    }

    public static User createUserWithNegativeSalary() {
        return new User(
                null,
                "Juan",
                "Perez",
                "juan.perez@mail.com",
                "123",
                "555-1234",
                -1000d,
                "password"
        );
    }

    public static User createUserWithTooHighSalary() {
        return new User(
                null,
                "Juan",
                "Perez",
                "juan.perez@mail.com",
                "123",
                "555-1234",
                20_000_000d,
                "password"
        );
    }

    public static User createUserWithDuplicatedEmail() {
        return new User(
                null,
                "Juan",
                "Perez",
                "duplicate@mail.com",
                "123",
                "555-1234",
                5_000_000d,
                "password"
        );
    }

    public static User createUserWithSpacesAndUppercaseEmail() {
        return new User(
                null,
                "  Juan  ",
                "  Perez  ",
                "TEST@MAIL.COM",  // válido, pasa regex
                "   123   ",
                "   555-1234   ",
                3_000_000d,
                "password"
        );
    }

}
