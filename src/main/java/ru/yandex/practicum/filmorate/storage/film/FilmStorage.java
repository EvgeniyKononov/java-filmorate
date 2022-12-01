package ru.yandex.practicum.filmorate.storage.film;

import ru.yandex.practicum.filmorate.model.film.Film;

import java.util.List;

public interface FilmStorage {
    Film create(Film film);

    void delete(Film film);

    Film amend(Film film);

    List<Film> findAll();

    Film find(Long id);

}
