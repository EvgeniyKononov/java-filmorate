package ru.yandex.practicum.filmorate.service.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserService {
    @Getter
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriends(Long userId, Long friendId) {
        addFriend(userId, friendId);
        addFriend(friendId, userId);
    }

    public void deleteFriends(Long userId, Long friendId) {
        deleteFriend(userId, friendId);
        deleteFriend(friendId, userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        Set<Long> userFriendsIds = userStorage.find(userId).getFriends();
        Set<Long> otherIdFriendsIds = userStorage.find(otherId).getFriends();
        if (Objects.nonNull(userFriendsIds) && Objects.nonNull(otherIdFriendsIds)) {
            return addCommonFriends(userFriendsIds, otherIdFriendsIds);
        }
        return new ArrayList<>();
    }

    public List<User> getFriends(Set<Long> ids) {
        return userStorage.getUsersByIds(ids);
    }

    private void addFriend(Long userId, Long friendId) {
        User user = userStorage.find(userId);
        userStorage.find(friendId);
        Set<Long> userFriends = user.getFriends();
        userFriends.add(friendId);
        user.setFriends(userFriends);
        userStorage.amend(user);
    }

    private void deleteFriend(Long userId, Long friendId) {
        User user = userStorage.find(userId);
        Set<Long> userFriends = user.getFriends();
        userFriends.remove(friendId);
        user.setFriends(userFriends);
        userStorage.amend(user);
    }

    private List<User> addCommonFriends(Set<Long> userFriendsIds, Set<Long> otherIdFriendsIds) {
        List<User> commonFriends = new ArrayList<>();
        for (Long id : userFriendsIds) {
            if (otherIdFriendsIds.contains(id)) {
                commonFriends.add(userStorage.find(id));
            }
        }
        return commonFriends;
    }
}
