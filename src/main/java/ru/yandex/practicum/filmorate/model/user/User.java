package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    @NotBlank
    @Email
    private String email;
    @UserLoginConstraint
    private String login;
    @NotNull
    @Size(max = 100)
    private String name;
    @Past
    private LocalDate birthday;
    private Map<Long, Boolean> friends = new HashMap<>();

    public User(Long id, String email, String login, String name, LocalDate birthday, Map<Long, Boolean> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = friends;
    }

    public User(String email, String login, String name, LocalDate birthday) {
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }

}
