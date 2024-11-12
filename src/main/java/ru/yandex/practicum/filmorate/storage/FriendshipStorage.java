package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.SQLException;
import java.util.Collection;

public interface FriendshipStorage {

    Collection<Long> getIdFriends(Long userId);

    void addFriend(Long userId, Long friendId) throws SQLException;

    void deleteFriend(Long userId, Long friendId) throws SQLException;

    Collection<User> getFriends(Long userId);

    Collection<User> getCommonFriends(Long userId, Long otherUserId);
}
