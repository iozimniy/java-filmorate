package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    //final Set<Long> friends = new HashSet<>();
    Long id;
    @Email
    String email;
    String login;
    String name;
    LocalDate birthday;
}
