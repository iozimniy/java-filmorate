package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
public class User {
    final Set<Long> friends = new HashSet<>();
    Long id;
    @Email
    String email;
    String login;
    String name;
    LocalDate birthday;
}
