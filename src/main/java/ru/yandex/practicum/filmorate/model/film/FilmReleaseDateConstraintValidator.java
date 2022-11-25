package ru.yandex.practicum.filmorate.model.film;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class FilmReleaseDateConstraintValidator implements ConstraintValidator<FilmReleaseDateConstraint, LocalDate> {
    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext constraintValidatorContext){
        LocalDate firstFilmRelease = LocalDate.of(1895, 12, 27);
        return releaseDate.isAfter(firstFilmRelease);
    }
}
