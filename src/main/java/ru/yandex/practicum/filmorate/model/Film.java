package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Film {
    Long id;
    @NotBlank String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
}
