package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmControllerTest {
    private FilmController filmController;
    private Film film;

    @BeforeEach
    public void beforeEach() {
        filmController = new FilmController();
        film = new Film();
        film.setName("name");
        film.setDescription("description");
        film.setDate(LocalDate.of(2022, 01, 01));
        film.setDuration(90);
    }

    @Test
    void nameValidationTest() {
        assertEquals(0, filmController.findAll().size());
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
        film.setName("");
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
    }

    @Test
    void descriptionValidationTest() {
        assertEquals(0, filmController.findAll().size());
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
        film.setDescription("111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111" +
                "111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111111");
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
    }

    @Test
    void dateValidationTest(){
        assertEquals(0, filmController.findAll().size());
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
        film.setDate(LocalDate.of(1895, 12, 27));
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
    }

    @Test
    void durationValidationTest() {
        assertEquals(0, filmController.findAll().size());
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
        film.setDuration(0);
        filmController.create(film);
        assertEquals(1, filmController.findAll().size());
    }

}