package ru.yandex.practicum.filmorate.validations;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.UserController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserValidationTest {

    User testUser;
    UserController userController = new UserController(new InMemoryUserStorage());

    //Валидация реализована через аннотацию @Email
//    @Test
//    public void createUserEmailWithoutDogValidationException() {
//        User user = new User(1L, "email", "ShockDog", "Ivan",
//                LocalDate.of(1990, 10, 26));
//
//        assertThrows(ValidationException.class, () -> UserValidation.validateForCreate(user));
//    }

    @Test
    public void createUserEmailIsBlankValidationException() {
        User user = new User(1L, "   ", "ShockDog", "Ivan",
                LocalDate.of(1990, 10, 26));

        assertThrows(ValidationException.class, () -> UserValidation.validateForCreate(user));
    }

    @Test
    public void createUserLoginIsBlankValidationException() {
        User user = new User(1L, "email@email", "  ", "Ivan",
                LocalDate.of(1990, 10, 26));
        assertThrows(ValidationException.class, () -> UserValidation.validateForCreate(user));
    }

    @Test
    public void createUserLoginContainsWhiteSpaceValidationException() {
        User user = new User(1L, "email@email", "Shock Dog", "Ivan",
                LocalDate.of(1990, 10, 26));
        assertThrows(ValidationException.class, () -> UserValidation.validateForCreate(user));
    }

    @Test
    public void createUserBirthDayInFutureValidationException() {
        User user = new User(1L, "email@email", "ShockDog", "Ivan",
                LocalDate.of(2026, 10, 26));
        assertThrows(ValidationException.class, () -> UserValidation.validateForCreate(user));
    }

    @Test
    public void updateUserIdIsNullValidationException() {
        User user = new User(null, "email@email", "ShockDog", "Ivan",
                LocalDate.of(2026, 10, 26));
        assertThrows(ValidationException.class, () -> UserValidation.validateForUpdate(user));
    }

    @Test
    public void updateUserIdIsAbsentNotFoundException() {
        createUser();
        User user = new User(25L, "email@email", "ShockDog", "Ivan",
                LocalDate.of(1990, 10, 26));
        assertThrows(NotFoundException.class, () -> userController.update(user));
    }

    @Test
    public void updateUserLoginContainsWhiteSpaceValidationException() {
        User user = new User(1L, "email@email", "Shock Dog", "Ivan",
                LocalDate.of(1990, 10, 26));
        assertThrows(ValidationException.class, () -> UserValidation.validateForUpdate(user));
    }


    @Test
    public void updateUserLoginIsBlankValidationException() {
        User user = new User(1L, "email@email", "  ", "Ivan",
                LocalDate.of(1990, 10, 26));
        assertThrows(ValidationException.class, () -> UserValidation.validateForUpdate(user));
    }

    @Test
    public void updateUserLoginBirthDayInFutureSpaceValidationException() {
        User user = new User(1L, "email@email", "ShockDog", "Ivan",
                LocalDate.of(2026, 10, 26));
        assertThrows(ValidationException.class, () -> UserValidation.validateForUpdate(user));
    }

    // вспомогательные методы
    private void createUser() {
        testUser = new User(1L, "email@email", "ShockDog", "Ivan",
                LocalDate.of(1990, 10, 26));
        userController.create(testUser);
    }
}
