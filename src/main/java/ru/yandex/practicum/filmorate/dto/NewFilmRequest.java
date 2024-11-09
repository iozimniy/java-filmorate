package ru.yandex.practicum.filmorate.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Data
public class NewFilmRequest {
    String name;
    String description;
    LocalDate releaseDate;
    Integer duration;
    Long ratingId;
    Collection<Long> genresId;
}
