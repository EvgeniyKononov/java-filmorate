package ru.yandex.practicum.filmorate.service.film;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FilmService {
    @Getter
    private final FilmStorage filmStorage;
    private final int MAX_QUANTITY_POPULAR_FILMS = 10;
    private final String NO_SUCH_LIKE = "Нет лайка от такого пользователя";

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public void addLike(Long filmId, Long userId) {
        Film film = filmStorage.find(filmId);
        Set<Long> likes = film.getLikes();
        likes.add(userId);
        film.setLikes(likes);
        filmStorage.amend(film);
    }

    public void deleteLike(Long filmId, Long userId) {
        Film film = filmStorage.find(filmId);
        Set<Long> likes = film.getLikes();
        if (likes.contains(userId)) {
            likes.remove(userId);
            film.setLikes(likes);
            filmStorage.amend(film);
        } else {
            throw new NotFoundException(NO_SUCH_LIKE);
        }
    }

    public Set<Film> getPopularFilms(Integer filmQuantity) {
        Comparator<Film> filmLikeComparator = (film1, film2) -> {
            if (film1.getLikes().size() == film2.getLikes().size()) {
                return (int) (film1.getId() - film2.getId());
            } else {
                return film1.getLikes().size() - film2.getLikes().size();
            }
        };
        Set<Film> popularFilms = new TreeSet<>(filmLikeComparator.reversed());
        List<Film> films = filmStorage.findAll();
        popularFilms.addAll(films);
        if (Objects.isNull(filmQuantity)) {
            filmQuantity = MAX_QUANTITY_POPULAR_FILMS;
        }
        return popularFilms.stream().limit(filmQuantity).collect(Collectors.toSet());
    }
}
