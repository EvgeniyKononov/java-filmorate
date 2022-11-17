package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {
    private Long id;
    @NotBlank
    @Email
    private String email;
    @UserLoginConstraint
    private String login;
    private String name;
    @Past
    private LocalDate birthday;
}
