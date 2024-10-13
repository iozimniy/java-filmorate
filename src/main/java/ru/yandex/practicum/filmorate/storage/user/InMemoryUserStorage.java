package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validations.UserValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    Map<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public User create(User user) {
        UserValidation.validateForCreate(user);

        user.setId(getNextId());
        users.put(user.getId(), user);

        log.info("Создан новый пользователь: {}", user);
        return user;
    }

    public User update(User user) {
        UserValidation.validateForUpdate(user);

        if (!(users.containsKey(user.getId()))) {
            log.warn("Не найден пользователь с id {}", user.getId());
            throw new NotFoundException("Не найден пользователь с id " + user.getId());
        }

        User oldUser = users.get(user.getId());

        if (user.getEmail() != null) {
            oldUser.setEmail(user.getEmail());
        }

        if (user.getLogin() != null) {
            oldUser.setLogin(user.getLogin());
        }

        if (user.getName() != null) {
            oldUser.setName(user.getName());
        }

        if (user.getBirthday() != null) {
            oldUser.setBirthday(user.getBirthday());
        }

        log.info("Пользователь изменён: {}", oldUser);
        return oldUser;
    }

    // вспомогательные методы
    private Long getNextId() {
        Long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
