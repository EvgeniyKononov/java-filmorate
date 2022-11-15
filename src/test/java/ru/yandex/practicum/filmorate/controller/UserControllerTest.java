package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private UserController userController;
    private User user;

    @BeforeEach
    public void beforeEach() {
        userController = new UserController();
        user = new User();
        user.setEmail("name@ya.ru");
        user.setLogin("login");
        user.setName("name");
        user.setBirthday(LocalDate.of(2000, 01, 01));
    }

    @Test
    void emailValidationTest() {
        assertEquals(0, userController.findAll().size());
        userController.create(user);
        assertEquals(1, userController.findAll().size());
        user.setEmail("");
        userController.create(user);
        assertEquals(1, userController.findAll().size());
        user.setEmail("mail");
        userController.create(user);
        assertEquals(1, userController.findAll().size());
    }

    @Test
    void loginValidationTest() {
        assertEquals(0, userController.findAll().size());
        userController.create(user);
        assertEquals(1, userController.findAll().size());
        user.setLogin("");
        userController.create(user);
        assertEquals(1, userController.findAll().size());
        user.setLogin(" login ");
        userController.create(user);
        assertEquals(1, userController.findAll().size());
    }

    @Test
    void birthdayValidationTest() {
        assertEquals(0, userController.findAll().size());
        userController.create(user);
        assertEquals(1, userController.findAll().size());
        user.setBirthday(LocalDate.of(2095, 12, 27));
        userController.create(user);
        assertEquals(1, userController.findAll().size());
    }


}
