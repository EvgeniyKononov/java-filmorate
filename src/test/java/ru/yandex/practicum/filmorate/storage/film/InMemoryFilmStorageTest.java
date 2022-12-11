package ru.yandex.practicum.filmorate.storage.film;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class InMemoryFilmStorageTest extends FilmStorageTest<InMemoryFilmStorage> {
    InMemoryFilmStorage filmStorage;

    @BeforeEach
    void beforeEach() {
        filmStorage = new InMemoryFilmStorage();
        super.filmStorage = filmStorage;
    }

    @Test
    void create() {
        super.create();
    }
}
