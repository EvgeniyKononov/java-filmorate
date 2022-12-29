package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.MPA;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FilmDbStorageTest {
    @Autowired
    @Qualifier("FilmDbStorage")
    private final FilmDbStorage filmStorage;
    private Film film = new Film();

    @BeforeEach
    public void beforeEach(){
        film.setName("The Rock");
        film.setDescription("The Film about the situation with hostages in the prison Alcatraz");
        film.setReleaseDate(LocalDate.of(1996, 6, 3));
        film.setDuration(136);
        film.setMpa(new MPA(1L, null));
        film.setGenres(List.of(new Genre(1L, null), new Genre(2L, null)));
    }

    @Test
    public void dtoFilmPublicMethodsTest() {
        filmStorage.create(film);
        Film filmFromBd = filmStorage.findById(1L);
        assertEquals("The Rock", filmFromBd.getName());
        film.setName("Rock");
        film.setId(1L);
        filmStorage.amend(film);
        filmFromBd = filmStorage.findById(1L);
        assertEquals("Rock", filmFromBd.getName());
        Film film2 = new Film();
        film2.setName("Titanic");
        film2.setDescription("The movie is about the 1912 sinking of the RMS Titanic");
        film2.setReleaseDate(LocalDate.of(1997, 12, 19));
        film2.setDuration(194);
        film2.setMpa(new MPA(2L, null));
        film2.setGenres(List.of(new Genre(3L, null)));
        filmStorage.create(film2);
        List<Film> films = new ArrayList<>(filmStorage.findAll());
        assertEquals(2, films.size());
        filmStorage.delete(film);
        films = new ArrayList<>(filmStorage.findAll());
        assertEquals(1, films.size());
    }

}
