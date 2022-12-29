package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.service.user.UserService;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> findAll() {
        return userService.getUserStorage().findAll();
    }

    @GetMapping("/users/{id}")
    public User find(@PathVariable Long id) {
        return userService.getUserStorage().findById(id);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> findFriends(@PathVariable Long id) {
        return userService.getFriends(userService.getUserStorage().findById(id).getFriends());
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> findCommonFriends(@PathVariable Long id, @PathVariable Long otherId) {
        return userService.getCommonFriends(id, otherId);
    }

    @PostMapping(value = "/users")
    public User create(@Valid @RequestBody User user) {
        return userService.getUserStorage().create(user);
    }

    @PutMapping(value = "/users")
    public User amend(@Valid @RequestBody User user) {
        return userService.getUserStorage().amend(user);
    }

    @PutMapping(value = "/users/{id}/friends/{friendId}")
    public void amend(@PathVariable Long id, @PathVariable Long friendId) {
        userService.addFriends(id, friendId);
    }

    @DeleteMapping(value = "/users/{id}/friends/{friendId}")
    public void delete(@PathVariable Long id, @PathVariable Long friendId) {
        userService.deleteFriends(id, friendId);
    }
}