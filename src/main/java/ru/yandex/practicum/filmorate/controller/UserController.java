package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validations.UserValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    //Map<Long, User> users = new HashMap<>();
    UserStorage userStorage;

    public UserController(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    @GetMapping
    public Collection<User> getUsers() {
        return userStorage.getUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User newUser) {
        log.info("Получен запрос на создание нового пользователя: {}", newUser);
        return userStorage.create(newUser);
    }

    @PutMapping
    public User update(@Valid @RequestBody User updatedUser) {
        log.info("Получен запрос на изменение пользователя {}", updatedUser);
        return userStorage.update(updatedUser);
    }
}
