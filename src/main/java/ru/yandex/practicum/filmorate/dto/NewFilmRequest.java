package ru.yandex.practicum.filmorate.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.Collection;

@Data
public class NewFilmRequest {
    @NotBlank String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
    Rating mpa;
    Collection<Genre> genres;
}

