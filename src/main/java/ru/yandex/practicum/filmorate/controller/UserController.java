package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validations.UserValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    Map<Long, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User newUser) {
        log.info("Получен запрос на создание нового пользователя: {}", newUser);

        UserValidation.validateForCreate(newUser);

        newUser.setId(getNextId());
        users.put(newUser.getId(), newUser);

        log.info("Создан новый пользователь: {}", newUser);
        return newUser;
    }

    @PutMapping
    public User update(@RequestBody User updatedUser) {
        log.info("Получен запрос на изменение пользователя {}", updatedUser);

        UserValidation.validateForUpdate(updatedUser);

        if (!(users.containsKey(updatedUser.getId()))) {
            log.warn("Не найден пользователь с id {}", updatedUser.getId());
            throw new NotFoundException("Не найден пользователь с id " + updatedUser.getId());
        }

        User oldUser = users.get(updatedUser.getId());

        if (updatedUser.getEmail() != null) {
            oldUser.setEmail(updatedUser.getEmail());
        }

        if (updatedUser.getLogin() != null) {
            oldUser.setLogin(updatedUser.getLogin());
        }

        if (updatedUser.getName() != null) {
            oldUser.setName(updatedUser.getName());
        }

        if (updatedUser.getBirthday() != null) {
            oldUser.setBirthday(updatedUser.getBirthday());
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
