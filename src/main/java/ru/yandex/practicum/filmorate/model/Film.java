package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Film.
 */
@Data
public class Film {
    Integer id;
    String name;
    String description;
    LocalDateTime releaseDate;
    Duration duration;
}
