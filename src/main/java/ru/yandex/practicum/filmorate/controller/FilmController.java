package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.MPA;
import ru.yandex.practicum.filmorate.service.film.FilmService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@RestController
public class FilmController {
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping("/films")
    public List<Film> findAll() {
        return filmService.getFilmStorage().findAll();
    }

    @GetMapping("/films/popular")
    public Set<Film> getPopularFilms(@RequestParam(required = false) Integer count) {
        return filmService.getPopularFilms(count);
    }

    @GetMapping("/films/{id}")
    public Film find(@PathVariable Long id) {
        return filmService.getFilmStorage().findById(id);
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().create(film);
    }

    @PutMapping(value = "/films")
    public Film amend(@Valid @RequestBody Film film) {
        return filmService.getFilmStorage().amend(film);
    }

    @PutMapping(value = "/films/{id}/like/{userId}")
    public void addLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.addLike(id, userId);
    }

    @DeleteMapping(value = "/films/{id}/like/{userId}")
    public void deleteLike(@PathVariable Long id, @PathVariable Long userId) {
        filmService.deleteLike(id, userId);
    }

    @GetMapping("/genres")
    public List<Genre> getAllGenres() {
        return filmService.getAllGenres();
    }

    @GetMapping("/genres/{id}")
    public Genre getGenresById(@PathVariable Long id) {
        return filmService.getGenreById(id);
    }

    @GetMapping("/mpa")
    public List<MPA> getAllRatings() {
        return filmService.getAllRatings();
    }

    @GetMapping("/mpa/{id}")
    public MPA getRatingById(@PathVariable Long id) {
        return filmService.getRatingById(id);
    }
}
