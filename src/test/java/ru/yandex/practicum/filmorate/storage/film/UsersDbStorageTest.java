package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final UserDbStorage userStorage;
    private User user = new User();

    @BeforeEach
    public void beforeEach(){
        user.setEmail("ivan@ya.ru");
        user.setLogin("ivbest");
        user.setName("Ivan");
        user.setBirthday(LocalDate.of(1988, 12, 26));
    }

    @Test
    public void dtoUsersPublicMethodsTest() {
        userStorage.create(user);
        User userFromBd = userStorage.find(1L);
        assertEquals("Ivan", userFromBd.getName());
        user.setName("Evgeniy");
        user.setId(1L);
        userStorage.amend(user);
        userFromBd = userStorage.find(1L);
        assertEquals("Evgeniy", userFromBd.getName());
        User user2 = new User();
        user2.setEmail("sergey@ya.ru");
        user2.setLogin("serg");
        user2.setName("Sergey");
        user2.setBirthday(LocalDate.of(1986, 5, 31));
        userStorage.create(user2);
        List<User> users = new ArrayList<>(userStorage.findAll());
        assertEquals(2, users.size());
        userStorage.delete(user);
        users = new ArrayList<>(userStorage.findAll());
        assertEquals(1, users.size());
    }
}
