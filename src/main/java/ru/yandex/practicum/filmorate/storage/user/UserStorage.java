package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.user.User;

import java.util.List;
import java.util.Set;

public interface UserStorage {
    User create(User user);

    void delete(User user);

    User amend(User user);

    List<User> findAll();

    List<User> getUsersByIds(Set<Long> ids);

    User findById(Long id);
}
