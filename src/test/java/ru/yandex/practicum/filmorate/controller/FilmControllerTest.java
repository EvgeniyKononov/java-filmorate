package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.film.Film;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private Film film;
    private static Validator validator;

    @BeforeAll
    public static void init() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    public void beforeEach() {
        film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setReleaseDate(LocalDate.of(2022, 01, 01));
        film.setDuration(90);
    }

    @Test
    void nameValidationTest() {
        film.setName("");
        Set<ConstraintViolation<Film>> validate = validator.validate(film);
        Set<String> errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertEquals(1, errorMessages.size());
    }

    @Test
    void descriptionValidationTest() {
        film.setDescription("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        Set<ConstraintViolation<Film>> validate = validator.validate(film);
        Set<String> errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertEquals(1, errorMessages.size());
    }

    @Test
    void dateValidationTest() {
        film.setReleaseDate(LocalDate.of(1895, 12, 27));
        Set<ConstraintViolation<Film>> validate = validator.validate(film);
        Set<String> errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertTrue(errorMessages.contains("дата релиза — не раньше 28 декабря 1895 года"));
    }

    @Test
    void durationValidationTest() {
        film.setDuration(0);
        Set<ConstraintViolation<Film>> validate = validator.validate(film);
        Set<String> errorMessages = validate.stream().map(ConstraintViolation::getMessage).collect(Collectors.toSet());
        assertEquals(1, errorMessages.size());
    }
}