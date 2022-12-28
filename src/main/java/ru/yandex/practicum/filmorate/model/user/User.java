package ru.yandex.practicum.filmorate.model.user;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.*;

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
    private HashMap<Long, Boolean> friends = new HashMap<>();

    public User() {

    }

    public User(Long id, String email, String login, String name, LocalDate birthday, HashMap<Long, Boolean> friends) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
        this.friends = friends;
    }
}
