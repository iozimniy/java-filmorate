package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validations.UserValidation;

import java.util.Collection;

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
}
