package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.model.film.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public abstract class FilmStorageTest <T extends FilmStorage> {
    protected  T filmStorage;

    @Test
    void create() {
        Film film = new Film();
        film.setId(1L);
        film.setName("The Rock");
        film.setDescription("The Film about the situation with hostages in the prison Alcatraz");
        film.setReleaseDate(LocalDate.of(1996, 6, 3));
        film.setDuration(136);
        filmStorage.create(film);
        assertEquals(film, filmStorage.find(1L));
    }

    @Test
    void delete() {
    }

    @Test
    void amend() {
    }

    @Test
    void findAll() {
    }

    @Test
    void find() {
    }
}