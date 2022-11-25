package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {
    private Long id;
    @NotBlank
    private String name;
    @Size(max=200)
    private String description;
    @FilmReleaseDateConstraint
    private LocalDate releaseDate;
    @Positive
    private Integer duration;
}
