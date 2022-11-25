package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private UserController userController;
    private User user = new User();
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

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
        user.setEmail("");
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        Set<String> errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertTrue(errorMessages.contains("не должно быть пустым"));
        user.setEmail("mail");
        validate = validator.validate(user);
        errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertTrue(errorMessages.contains("должно иметь формат адреса электронной почты"));
    }

    @Test
    void loginValidationTest() {
        user.setLogin("");
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        Set<String> errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertTrue(errorMessages.contains("логин не может быть пустым и не должен содержать пробелы"));
        user.setLogin(" login ");
        validate = validator.validate(user);
        errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertTrue(errorMessages.contains("логин не может быть пустым и не должен содержать пробелы"));
    }

    @Test
    void birthdayValidationTest() {
        user.setBirthday(LocalDate.of(2095, 12, 27));
        Set<ConstraintViolation<User>> validate = validator.validate(user);
        Set<String> errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertTrue(errorMessages.contains("должно содержать прошедшую дату"));
    }


}
