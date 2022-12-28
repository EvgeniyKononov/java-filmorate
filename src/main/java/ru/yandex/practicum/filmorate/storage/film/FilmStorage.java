package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.MPA;

import java.util.List;

public interface FilmStorage {
    Film create(Film film);

    void delete(Film film);

    Film amend(Film film);

    List<Film> findAll();

    Film find(Long id);

    List<Genre> getAllGenres();

    Genre getGenreById(Long id);

    List<MPA> getAllRatings();

    MPA getRatingById(Long id);
}
