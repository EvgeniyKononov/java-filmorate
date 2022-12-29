package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserDbStorage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UsersDbStorageTest {
    @Autowired
    @Qualifier("UserDbStorage")
    private final UserDbStorage userStorage;
    private User user;

    @BeforeEach
    public void beforeEach() {
        user = new User("ivan@ya.ru", "ivbest", "Ivan",
                LocalDate.of(1988, 12, 26));
    }

    @Test
    public void dtoUsersPublicMethodsTest() {
        userStorage.create(user);
        User userFromBd = userStorage.findById(1L);
        assertEquals("Ivan", userFromBd.getName());
        user.setName("Evgeniy");
        user.setId(1L);
        userStorage.amend(user);
        userFromBd = userStorage.findById(1L);
        assertEquals("Evgeniy", userFromBd.getName());
        User user2 = new User("sergey@ya.ru", "serg", "Sergey",
                LocalDate.of(1986, 5, 31));
        userStorage.create(user2);
        List<User> users = new ArrayList<>(userStorage.findAll());
        assertEquals(2, users.size());
        userStorage.delete(user);
        users = new ArrayList<>(userStorage.findAll());
        assertEquals(1, users.size());
    }
}
