package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.*;

@RestController
public class UserController {
    private final HashMap<Integer, User> users = new HashMap<>();
    private Integer id = 1;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public List<User> findAll() {
        List<User> userList = new ArrayList<>(users.values());
        log.debug("Текущее количесвто пользователей: {}", users.size());
        return userList;
    }

    @PostMapping(value = "/users")
    public User create(@RequestBody User user) {
        if (validateUser(user)) {
            user.setName(checkAndReturnName(user));
            user.setId(id);
            users.put(id, user);
            id++;
            log.debug("Добавлен пользователь: {}", user);
        }
        return user;
    }

    @PutMapping(value = "/users")
    public User amend(@RequestBody User user) {
        if (validateUser(user)) {
            User oldUser = new User();
            user.setName(checkAndReturnName(user));
            if (users.containsKey(user.getId())) {
                oldUser = users.get(user.getId());
                users.put(user.getId(), user);
            }
            log.debug("Пользователь {} изменен на {}", oldUser, user);
        }
        return user;
    }

    private boolean validateUser(User user) {
        boolean isValid = true;
        validateEmail(user.getEmail());
        validateLogin(user.getLogin());
        validateBirthday(user.getBirthday());
        return isValid;
    }

    private String checkAndReturnName(User user) {
        if (user.getName().isBlank()) {
            return user.getLogin();
        } else {
            return user.getName();
        }
    }

    private void validateEmail(String email) {
        if (email.isBlank()) {
            throw new ValidationException("электронная почта не может быть пустой");
        } else if (!email.contains("@")) {
            throw new ValidationException("электронная почта должна содержать символ '@'");
        }
    }

    private void validateLogin(String login) {
        if (login.isBlank()) {
            throw new ValidationException("логин не может быть пустым");
        } else if (login.contains(" ")) {
            throw new ValidationException("логин не может содержать пробелы");
        }
    }

    private void validateBirthday(LocalDate date) {
        LocalDate today = LocalDate.now();
        if (date.isAfter(today)) {
            throw new ValidationException("дата рождения не может быть в будущем");
        }
    }

}