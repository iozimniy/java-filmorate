package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InternalServerException;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.validations.UserValidation;

import java.sql.SQLException;
import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class UserService {
    UserStorage userStorage;
    FriendshipStorage friendshipStorage;
    UserValidation userValidation;

    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User getUser(Long userId) {
        validateUserId(userId);
        return userStorage.getUserById(userId).get();
    }

    public User create(User user) {
        userValidation.validateForCreate(user);

        try {
            User newUser = userStorage.create(user);
            log.info("Создан новый пользователь: {}", newUser);
            return newUser;
        } catch (SQLException e) {
            log.info("Ошибка обращения к DB: {}", e.getMessage());
            throw new InternalServerException("Что-то пошло нет. Повторите запрос позже.");
        }
    }

    public User update(User user) {
        userValidation.validateForUpdate(user);

        if (!(userStorage.contains(user.getId()))) {
            log.warn("Не найден пользователь с id {}", user.getId());
            throw new NotFoundException("Не найден пользователь с id " + user.getId());
        }

        User oldUser = userStorage.getUserById(user.getId()).get();

        if (user.getEmail() == null) {
            user.setEmail(oldUser.getEmail());
        }

        if (user.getLogin() == null) {
            user.setLogin(oldUser.getLogin());
        }

        if (user.getName() == null) {
            user.setName(oldUser.getName());
        }

        if (user.getBirthday() == null) {
            user.setBirthday(oldUser.getBirthday());
        }

        log.info("Пользователь изменён: {}", user);
        try {
            return userStorage.update(user);
        } catch (SQLException e) {
            log.info("Ошибка обращения к DB: {}", e.getMessage());
            throw new InternalServerException("Что-то пошло нет. Повторите запрос позже.");
        }
    }

    public void addFriend(Long userId, Long friendId) {
        validateUserId(userId);
        validateUserId(friendId);

        if (friendshipStorage.getIdFriends(userId).contains(friendId)) {
            return;
        }

        try {
            friendshipStorage.addFriend(userId, friendId);
        } catch (SQLException e) {
            log.info("Ошибка обращения к DB: {}", e.getMessage());
            throw new InternalServerException("Что-то пошло нет. Повторите запрос позже.");
        }
        log.info("Пользователь {} добавлен в список друзей пользователя {}", friendId, userId);

        //addFriend(friendId, userId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        validateUserId(userId);
        validateUserId(friendId);

        if (!friendshipStorage.getIdFriends(userId).contains(friendId)) {
            return;
        }

        try {
            friendshipStorage.deleteFriend(userId, friendId);
        } catch (SQLException e) {
            log.info("Ошибка обращения к DB: {}", e.getMessage());
            throw new InternalServerException("Что-то пошло нет. Повторите запрос позже.");
        }
        log.info("Пользователь {} удалён из друзей пользователя {}", friendId, userId);

        //deleteFriend(friendId, userId);
    }

    public Collection<User> getFriends(Long userId) {
        validateUserId(userId);
        return friendshipStorage.getFriends(userId);
    }

    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        validateUserId(userId);
        validateUserId(otherUserId);

        return friendshipStorage.getCommonFriends(userId, otherUserId);
    }

    //вспомогательные методы
    public void validateUserId(Long userId) {
        if (!userStorage.contains(userId)) {
            log.warn("Не найден пользователь с id {}", userId);
            throw new NotFoundException("Не найден пользователь с id" + userId);
        }
    }
}
