package ru.yandex.practicum.filmorate.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.user.User;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {
    private final HashMap<Long, User> users = new HashMap<>();
    private Long id = 1L;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    public List<User> findAll() {
        List<User> userList = new ArrayList<>(users.values());
        log.debug("Текущее количесвто пользователей: {}", users.size());
        return userList;
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        user.setName(checkAndReturnName(user));
        user.setId(id);
        users.put(id, user);
        id++;
        log.debug("Добавлен пользователь: {}", user);
        return user;
    }

    @PutMapping(value = "/users")
    public User amend(@Valid @RequestBody User user) {
        User oldUser;
        user.setName(checkAndReturnName(user));
        if (users.containsKey(user.getId())) {
            oldUser = users.get(user.getId());
            users.put(user.getId(), user);
            log.debug("Пользователь {} изменен на {}", oldUser, user);
        } else {
            throw new ValidationException("неверный номер ID");
        }
        return user;
    }

    private String checkAndReturnName(User user) {
        if (Objects.equals(null, user.getName())) {
            return user.getLogin();
        } else {
            return user.getName();
        }
    }

}