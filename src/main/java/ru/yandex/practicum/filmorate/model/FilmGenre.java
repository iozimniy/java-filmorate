package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class FilmGenre {
    Long filmId;
    Long genreId;

    @Override
    public String toString() {
        return filmId +
                ", " + genreId;
    }
}
