package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;
import ru.yandex.practicum.filmorate.validations.UserValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Slf4j
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public Collection<User> getUsers() {
        log.info("Получен запрос на возврат информации по всем пользователям");
        return userService.getUsers();
    }

    @PostMapping
    public User create(@Valid @RequestBody User newUser) {
        log.info("Получен запрос на создание нового пользователя: {}", newUser);
        return userService.create(newUser);
    }

    @PutMapping
    public User update(@Valid @RequestBody User updatedUser) {
        log.info("Получен запрос на изменение пользователя {}", updatedUser);
        return userService.update(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long userId) {
        log.info("Получен запрос на возврат информации о пользоватале с id {}", userId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userService.getUser(userId));
    }

    @PutMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Получен запрос на добавление пользователя с id {} в друзья пользователя с id {}", friendId, userId);
        userService.addFriend(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        log.info("Получен запрос на удаление друга с id {} у пользователя с id {}", friendId, userId);
        userService.addFriend(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public ResponseEntity<Collection<User>> getFriends(@PathVariable Long userId) {
        log.info("Получен запрос за возврат информации о друзьях пользователя с id {}", userId);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getFriends(userId));
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public ResponseEntity<Collection<User>> getCommonFriends(@PathVariable Long userId, @PathVariable Long otherUserId) {
        log.info("Получен запрос на возврат информации по общим друзьям пользователей c id {} и {}", userId, otherUserId);
        return ResponseEntity.status(HttpStatus.OK).body(userService.getCommonFriends(userId, otherUserId));
    }
}
