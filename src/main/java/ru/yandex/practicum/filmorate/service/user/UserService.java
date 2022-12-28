package ru.yandex.practicum.filmorate.service.user;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.user.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.*;

@Service
public class UserService {
    @Getter
    private final UserStorage userStorage;

    @Autowired
    public UserService(@Qualifier("userDbStorage") UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public void addFriends(Long userId, Long friendId) {
        addFriend(userId, friendId);
        // addFriend(friendId, userId);
    }

    public void deleteFriends(Long userId, Long friendId) {
        deleteFriend(userId, friendId);
        deleteFriend(friendId, userId);
    }

    public List<User> getCommonFriends(Long userId, Long otherId) {
        HashMap<Long, Boolean> userFriendsIds = userStorage.find(userId).getFriends();
        HashMap<Long, Boolean> otherIdFriendsIds = userStorage.find(otherId).getFriends();
        if (Objects.nonNull(userFriendsIds) && Objects.nonNull(otherIdFriendsIds)) {
            return addCommonFriends(userFriendsIds, otherIdFriendsIds);
        }
        return new ArrayList<>();
    }

    public List<User> getFriends(HashMap<Long, Boolean> ids) {
        List<User> friends = new ArrayList<>();
        for (Map.Entry<Long, Boolean> entry : ids.entrySet()) {
            friends.add(userStorage.find(entry.getKey()));
        }
        return friends;
    }

    private void addFriend(Long userId, Long friendId) {
        User user = userStorage.find(userId);
        userStorage.find(friendId);
        HashMap<Long, Boolean> userFriends = user.getFriends();

        userFriends.put(friendId, false);
        user.setFriends(userFriends);
        userStorage.amend(user);
    }

    private void deleteFriend(Long userId, Long friendId) {
        User user = userStorage.find(userId);
        HashMap<Long, Boolean> userFriends = user.getFriends();
        userFriends.remove(friendId);
        user.setFriends(userFriends);
        userStorage.amend(user);
    }

    private List<User> addCommonFriends
            (HashMap<Long, Boolean> userFriendsIds, HashMap<Long, Boolean> otherIdFriendsIds) {
        List<User> commonFriends = new ArrayList<>();
        for (Map.Entry<Long, Boolean> entry : userFriendsIds.entrySet()) {
            if (otherIdFriendsIds.containsKey(entry.getKey())) {
                commonFriends.add(userStorage.find(entry.getKey()));
            }
        }
        return commonFriends;
    }


}
