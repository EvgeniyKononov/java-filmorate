package ru.yandex.practicum.filmorate.model.film;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MPA {
    @NotBlank
    private Long id;
    @Size(max = 100)
    @NotBlank
    private String name;

    public MPA(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
