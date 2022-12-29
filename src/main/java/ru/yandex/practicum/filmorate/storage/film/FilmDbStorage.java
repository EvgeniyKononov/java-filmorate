package ru.yandex.practicum.filmorate.storage.film;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.film.Film;
import ru.yandex.practicum.filmorate.model.film.Genre;
import ru.yandex.practicum.filmorate.model.film.MPA;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;


import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.*;

@Component
@Qualifier("FilmDbStorage")
public class FilmDbStorage implements FilmStorage {

    private final Logger log = LoggerFactory.getLogger(UserDbStorage.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        String sqlQuery = "INSERT INTO FILM (FIlM_NAME, DESCRIPTION, RELEASE_DATE, DURATION, RATING_ID) " +
                "VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        //  checkMPA(film.getMpa().getId());
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(sqlQuery, new String[]{"FILM_ID"});
            stmt.setString(1, film.getName());
            stmt.setString(2, film.getDescription());
            stmt.setDate(3, java.sql.Date.valueOf(film.getReleaseDate()));
            stmt.setInt(4, film.getDuration());
            stmt.setInt(5, film.getMpa().getId().intValue());
            return stmt;
        }, keyHolder);
        Long filmId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        insertFilmGenre(filmId, getGenreIdList(film.getGenres()));
        log.info("Создан фильм с идентефикатором {}", filmId);
        return findById(filmId);
    }

    @Override
    public void delete(Film film) {
        deleteFromFilmUsersLikes(film);
        deleteFromFilmGenre(film);
        deleteFromFilm(film);
        log.info("Удалён фильм с идентефикатором {}", film.getId());
    }

    @Override
    public Film amend(Film film) {
        SqlRowSet userRS = jdbcTemplate.queryForRowSet("SELECT * FROM FILM WHERE FILM_ID = ?", film.getId());
        if (userRS.next()) {
            String sqlQuery = "UPDATE FILM set FILM_NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?, DURATION = ?, " +
                    "RATING_ID = ? where FILM_ID = ?";
            //  checkMPA(film.getMpa().getId());
            jdbcTemplate.update(sqlQuery, film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(),
                    film.getMpa().getId(), film.getId());
            updateFilmGenre(film);
            updateFilmUsersLikes(film);
            log.info("Изменён фильм {}", film.getId());
            return findById(film.getId());
        } else throw new NotFoundException("Нет фильма с таким id");

    }

    @Override
    public List<Film> findAll() {
        List<Film> films = new ArrayList<>();
        SqlRowSet filmRows = jdbcTemplate.queryForRowSet("SELECT * FROM FILM");
        while (filmRows.next()) {
            Film film = makeFilm(filmRows);
            log.info("Найден фильм: {} {}", film.getId(), film.getName());
            films.add(film);
        }
        return films;
    }

    @Override
    public Film findById(Long id) {
        SqlRowSet userRows = jdbcTemplate.queryForRowSet("SELECT * FROM FILM WHERE FILM_ID = ?", id);
        if (userRows.next()) {
            Film film = makeFilm(userRows);
            log.info("Найден фильм: {} {}", film.getId(), film.getName());
            return film;
        } else {
            log.info("Фильм с идентификатором {} не найден.", id);
            throw new NotFoundException("Не найден фильм с таким номером");
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        List<Genre> filmGenres = new ArrayList<>();
        SqlRowSet genreRows = jdbcTemplate.queryForRowSet("SELECT * FROM GENRE ORDER BY GENRE_ID");
        while (genreRows.next()) {
            Genre genre = makeGenre(genreRows);
            filmGenres.add(genre);
        }
        return filmGenres;
    }

    @Override
    public Genre getGenreById(Long id) {
        SqlRowSet genreRows =
                jdbcTemplate.queryForRowSet("SELECT *  FROM GENRE WHERE GENRE_ID = ? ORDER BY GENRE_ID", id);
        if (genreRows.next()) {
            return makeGenre(genreRows);
        } else
            throw new NotFoundException("Не найден жанр с таким номером");
    }

    @Override
    public List<MPA> getAllRatings() {
        List<MPA> filmRatings = new ArrayList<>();
        SqlRowSet ratingRows = jdbcTemplate.queryForRowSet("SELECT * FROM RATING ORDER BY RATING_ID");
        while (ratingRows.next()) {
            MPA mpa = makeMPA(ratingRows);
            filmRatings.add(mpa);
        }
        return filmRatings;
    }

    @Override
    public MPA getRatingById(Long id) {
        SqlRowSet ratingRows = jdbcTemplate.queryForRowSet("SELECT * FROM RATING WHERE RATING_ID = ?", id);
        if (ratingRows.next()) {
            return makeMPA(ratingRows);
        } else
            throw new NotFoundException("Не найден рейтинг с таким номером");
    }

    private void updateFilmUsersLikes(Film film) {
        deleteFromFilmUsersLikes(film);
        String sqlQuery = "INSERT INTO FILM_USERS_LIKES (FILM_ID, USER_ID) VALUES (?, ?)";
        for (Long userId : film.getLikes()) {
            jdbcTemplate.update(sqlQuery, film.getId(), userId);
        }
    }

    private void updateFilmGenre(Film film) {
        deleteFromFilmGenre(film);
        String sqlQuery = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
        Set<Genre> genres = new HashSet<>(film.getGenres());
        for (Genre genre : genres) {
            jdbcTemplate.update(sqlQuery, film.getId(), genre.getId());
        }
    }

    private void deleteFromFilm(Film film) {
        String sqlQuery = "DELETE FROM FILM WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    private void deleteFromFilmGenre(Film film) {
        String sqlQuery = "DELETE FROM FILM_GENRE WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    private void deleteFromFilmUsersLikes(Film film) {
        String sqlQuery = "DELETE FROM FILM_USERS_LIKES WHERE FILM_ID = ?";
        jdbcTemplate.update(sqlQuery, film.getId());
    }

    private Film makeFilm(SqlRowSet rs) {
        Long filmId = rs.getLong("FILM_ID");
        String name = rs.getString("FILM_NAME");
        String description = rs.getString("DESCRIPTION");
        LocalDate releaseDate = Objects.requireNonNull(rs.getDate("RELEASE_DATE")).toLocalDate();
        Integer duration = rs.getInt("DURATION");
        Set<Long> likes = getLikes(filmId);
        List<Genre> genre = getGenres(filmId);
        MPA mpa = getRatingById(rs.getLong("RATING_ID"));
        return new Film(filmId, name, description, releaseDate, duration, likes, genre, mpa);
    }

    private MPA makeMPA(SqlRowSet rs) {
        Long ratingId = rs.getLong("RATING_ID");
        String ratingName = rs.getString("RATING_NAME");
        return new MPA(ratingId, ratingName);
    }

    private Genre makeGenre(SqlRowSet rs) {
        Long genreId = rs.getLong("GENRE_ID");
        String genreName = rs.getString("GENRE_NAME");
        return new Genre(genreId, genreName);
    }

    private void insertFilmGenre(Long filmId, Set<Long> genreIdList) {
        String sqlQuery = "INSERT INTO FILM_GENRE (FILM_ID, GENRE_ID) VALUES (?, ?)";
        for (Long id : genreIdList) {
            jdbcTemplate.update(sqlQuery, filmId, id);
        }
    }

    private Set<Long> getLikes(Long filmId) {
        String sql = "SELECT USER_ID FROM FILM_USERS_LIKES WHERE FILM_ID = ?";
        List<Long> likes = jdbcTemplate.queryForList(sql, Long.class, filmId);
        return new HashSet<>(likes);
    }

    private List<Genre> getGenres(Long filmId) {
        SqlRowSet genreRows =
                jdbcTemplate.queryForRowSet("SELECT g.GENRE_ID, g.GENRE_NAME FROM FILM_GENRE AS f " +
                        "LEFT JOIN GENRE AS g ON g.GENRE_ID = f.GENRE_ID" +
                        " WHERE FILM_ID = ?" +
                        "ORDER BY g.GENRE_ID", filmId);
        List<Genre> genres = new ArrayList<>();
        while (genreRows.next()) {
            Genre genre = makeGenre(genreRows);
            genres.add(genre);
        }
        return genres;
    }

    private Set<Long> getGenreIdList(List<Genre> genreList) {
        Set<Long> genreIdList = new HashSet<>();
        if (Objects.isNull(genreList)) {
            return genreIdList;
        } else if (genreList.size() != 0) {
            for (Genre genre : genreList) {
                genreIdList.add(genre.getId());
            }
        }
        return genreIdList;
    }


}