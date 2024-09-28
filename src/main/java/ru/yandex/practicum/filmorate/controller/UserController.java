package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    Map<Integer, User> users = new HashMap<>();

    @GetMapping
    public Collection<User> getUsers() {
        return users.values();
    }

    @PostMapping
    public User create(@RequestBody User newUser) {
        //тут должна быть логика по созданию пользователя с валидацией
        return newUser;
    }

    @PutMapping
    public User update(@RequestBody User updatedUser) {
        //тут должна быть логика апдейта пользователя с валидацией
        return updatedUser;
    }
}
