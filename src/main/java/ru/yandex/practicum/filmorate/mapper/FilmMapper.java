package ru.yandex.practicum.filmorate.mapper;

import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Set;

public final class FilmMapper {
    public static Film mapToFilm(NewFilmRequest request) {
        Film film = new Film();

        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());

        //проверяем, есть ли в запросе рейтинг
        //если нет, то G по умолчанию.
        if (request.getRatingId() != null) {
            film.setRatingId(request.getRatingId());
        } else {
            film.setRatingId(0L);
        }

        //проверяем, если ли в запросе жанры, и если есть, прикручиваем к фильму
        if (request.getGenresId() != null) {
            film.setGenres(request.getGenresId());
        }

        return film;
    }
}
