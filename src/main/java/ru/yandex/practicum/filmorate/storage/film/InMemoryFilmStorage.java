package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.MPA;

import java.util.*;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Long, Film> films = new HashMap<>();

    private Long id = 1L;
    private static final Logger log = LoggerFactory.getLogger(InMemoryFilmStorage.class);

    private static final String WRONG_ID = "нет фильма с таким id";

    @Override
    public List<Film> findAll() {
        List<Film> filmList = new ArrayList<>(films.values());
        log.debug("Текущее количесвто фильмов: {}", filmList.size());
        return filmList;
    }

    @Override
    public Film findById(Long id) {
        if (films.containsKey(id)) {
            return films.get(id);
        } else {
            throw new NotFoundException(WRONG_ID);
        }
    }

    @Override
    public Film create(Film film) {
        film.setId(id);
        films.put(id, film);
        id++;
        log.debug("Добавлен фильм: {}", film);
        return film;
    }

    @Override
    public Film amend(Film film) {
        Film oldFilm;
        if (films.containsKey(film.getId())) {
            oldFilm = films.get(film.getId());
            films.put(film.getId(), film);
            log.debug("Фильм {} изменен на {}", oldFilm, film);
        } else {
            throw new NotFoundException(WRONG_ID);
        }
        return film;
    }

    @Override
    public void delete(Film film) {
        if (films.containsKey(film.getId())) {
            films.remove(film.getId());
            log.debug("Фильм {} удалён", film);
        } else {
            throw new NotFoundException(WRONG_ID);
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Film> films = findAll();
        List<Genre> genres = new ArrayList<>();
        for (Film film : films) {
            genres.addAll(film.getGenres());
        }
        return genres;
    }

    @Override
    public Genre getGenreById(Long id) {
        List<Film> films = findAll();
        for (Film film : films) {
            for (Genre genre : film.getGenres()) {
                if (genre.getId() == id.intValue()) {
                    return genre;
                }
            }
        }
        throw new NotFoundException("Нет рейтинга с таким id");
    }


    @Override
    public List<MPA> getAllRatings() {
        List<Film> films = findAll();
        List<MPA> mpas = new ArrayList<>();
        for (Film film : films) {
            mpas.add(film.getMpa());
        }
        return mpas;
    }

    @Override
    public MPA getRatingById(Long id) {
        List<Film> films = findAll();
        for (Film film : films) {
            if (film.getMpa().getId() == id.intValue()) {
                return film.getMpa();
            }
        }
        throw new NotFoundException("Нет рейтинга с таким id");
    }
}
