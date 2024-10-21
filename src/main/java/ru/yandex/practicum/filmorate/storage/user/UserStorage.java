package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

public interface UserStorage {

    Collection<User> getUsers();

    User create(User user);

    User update(User user);

    boolean contains(Long id);

    User getUserById(Long id);

    Collection<User> getCommonFriends(Long userId, Long otherUserId);
    Collection<User> getUserFriends(Long userId);
}
