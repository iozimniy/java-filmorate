package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validations.UserValidation;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserService {
    UserStorage userStorage;

    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    public User create(User user) {
        UserValidation.validateForCreate(user);

        log.info("Создан новый пользователь: {}", user);
        return userStorage.create(user);
    }

    public User update(User user) {
        UserValidation.validateForUpdate(user);

        if (!(userStorage.isContains(user.getId()))) {
            log.warn("Не найден пользователь с id {}", user.getId());
            throw new NotFoundException("Не найден пользователь с id " + user.getId());
        }

        User oldUser = userStorage.getUserById(user.getId());

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
        return userStorage.update(user);
    }

    public void addFriend(Long userId, Long friendId) {
        //проверяем, что пользователь и его будущий друг существуют
        validateUser(userId);
        validateUser(friendId);

        User user = userStorage.getUserById(userId);

        //проверяем, что у этого пользователя такого друга ещё нет.
        if (user.getFriends().contains(friendId)) {
            return;
        }

        user.getFriends().add(friendId);
        log.info("Пользователь {} добавлен в список друзей пользователя {}", friendId, userId);

        addFriend(friendId, userId);

//        Тут решила рекурсией. Мб, сработает.
//        User friend = userStorage.getUserById(friendId);
//        friend.getFriends().add(userId);
//        log.info("Пользователь {} добавлен в список друзей пользователя {}", userId, friendId);
    }

    public void deleteFriend(Long userId, Long friendId) {
        //проверяем, что пользователь и его будущий бывший друг существуют
        validateUser(userId);
        validateUser(friendId);

        User user = userStorage.getUserById(userId);

        //проверяем, что они есть друг у друга в друзьях
        if (!user.getFriends().contains(friendId)) {
            return;
        }

        user.getFriends().remove(friendId);
        log.info("Пользователь {} удалён из друзей пользователя {}", friendId, userId);

        deleteFriend(friendId, userId);
    }

    public Collection<User> getFriends(Long userId) {
        validateUser(userId);
        User user = userStorage.getUserById(userId);
        return user.getFriends().stream().map(id -> userStorage.getUserById(id)).collect(Collectors.toList());
    }

    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        validateUser(userId);
        validateUser(otherUserId);
        User user = userStorage.getUserById(userId);
        User otherUser = userStorage.getUserById(otherUserId);

        return otherUser.getFriends().stream()
                .filter(id -> user.getFriends().contains(id))
                .map(id -> userStorage.getUserById(id))
                .collect(Collectors.toList());
    }

    //вспомогательные методы
    private void validateUser(Long userId) {
        if (!userStorage.isContains(userId)) {
            log.warn("Не найден пользователь с id {}", userId);
            throw new NotFoundException("Не найден пользователь с id" + userId);
        }
    }
}
