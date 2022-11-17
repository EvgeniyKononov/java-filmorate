package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.film.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
public class FilmController {
    private final HashMap<Long, Film> films = new HashMap<>();
    private Long id = 1L;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/films")
    public List<Film> findAll() {
        List<Film> filmList = new ArrayList<>(films.values());
        log.debug("Текущее количесвто фильмов: {}", filmList.size());
        return filmList;
    }

    @PostMapping(value = "/films")
    public Film create(@Valid @RequestBody Film film) {
        film.setId(id);
        films.put(id, film);
        id++;
        log.debug("Добавлен фильм: {}", film);
        return film;
    }

    @PutMapping(value = "/films")
    public Film amend(@Valid @RequestBody Film film) {
        Film oldFilm;
        if (films.containsKey(film.getId())) {
            oldFilm = films.get(film.getId());
            films.put(film.getId(), film);
            log.debug("Фильм {} изменен на {}", oldFilm, film);
        } else {
            throw new ValidationException("нет фильма с таким id");
        }
        return film;
    }
}
