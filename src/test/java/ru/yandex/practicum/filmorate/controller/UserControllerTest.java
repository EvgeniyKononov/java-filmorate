package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private UserController userController;
    private User user = new User();

    @BeforeEach
    public void beforeEach() {
        userController = new UserController();
        user.setLogin("login");
        user.setEmail("name@ya.ru");
        user.setName("name");
        user.setBirthday(LocalDate.of(2000, 01, 01));
    }

    @Test
    void emailValidationTest() {
        assertEquals(0, userController.findAll().size());
        userController.create(user);
        assertEquals(1, userController.findAll().size());
        user.setEmail("");
        assertThrows(ValidationException.class, () -> userController.create(user));
        user.setEmail("mail");
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void loginValidationTest() {
        assertEquals(0, userController.findAll().size());
        userController.create(user);
        assertEquals(1, userController.findAll().size());
        user.setLogin("");
        assertThrows(ValidationException.class, () -> userController.create(user));
        user.setLogin(" login ");
        assertThrows(ValidationException.class, () -> userController.create(user));
    }

    @Test
    void birthdayValidationTest() {
        assertEquals(0, userController.findAll().size());
        userController.create(user);
        assertEquals(1, userController.findAll().size());
        user.setBirthday(LocalDate.of(2095, 12, 27));
        assertThrows(ValidationException.class, () -> userController.create(user));
    }


}
