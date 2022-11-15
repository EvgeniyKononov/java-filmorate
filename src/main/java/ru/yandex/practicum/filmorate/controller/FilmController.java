package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.*;

@RestController
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private Integer id = 1;
    private static final Logger log = LoggerFactory.getLogger(FilmController.class);

    @GetMapping("/films")
    public List<Film> findAll() {
        List<Film> filmList = new ArrayList<>(films.values());
        log.debug("Текущее количесвто фильмов: {}", filmList.size());
        return filmList;
    }

    @PostMapping(value = "/films")
    public Film create(@RequestBody Film film) {
        if (!validateFilm(film)) {
            return null;
        } else {
            film.setId(id);
            films.put(id, film);
            id++;
            log.debug("Добавлен фильм: {}", film);
            return film;
        }
    }

    @PutMapping(value = "/films")
    public Film amend(@RequestBody Film film) {
        if (!validateFilm(film)) {
            return null;
        } else {
            Film oldFilm = new Film();
            if (films.containsKey(film.getId())) {
                oldFilm = films.get(film.getId());
                films.put(film.getId(), film);
            }
            log.debug("Фильм {} изменен на {}", oldFilm, film);
            return film;
        }
    }

    private boolean validateFilm(Film film) {
        boolean isValid = true;
        try {
            validateName(film.getName());
            validateDescription(film.getDescription());
            validateDate(film.getDate());
            validateDuration(film.getDuration());
        } catch (ValidationException e) {
            log.debug("Неверно указаны данные, а именно {}", e.getMsg());
            isValid = false;
        }
        return isValid;
    }

    private void validateName(String name) {
        if (name.isBlank()) {
            throw new ValidationException("название не может быть пустым");
        }
    }

    private void validateDescription(String description) {
        if (description.length() > 200) {
            throw new ValidationException("максимальная длина описания — 200 символов");
        }
    }

    private void validateDate(LocalDate date) {
        LocalDate firstFilm = LocalDate.of(1895, 12, 28);
        if (date.isBefore(firstFilm)) {
            throw new ValidationException("дата релиза — не раньше 28 декабря 1895 года");
        }
    }

    private void validateDuration(Integer duration) {
        if (duration < 1) {
            throw new ValidationException("продолжительность фильма должна быть положительной");
        }
    }
}
