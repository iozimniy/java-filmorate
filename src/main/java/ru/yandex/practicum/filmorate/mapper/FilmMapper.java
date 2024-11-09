package ru.yandex.practicum.filmorate.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

@Component
@AllArgsConstructor
public final class FilmMapper {
    RatingStorage ratingStorage;
    public Film mapToFilm(NewFilmRequest request) {
        Film film = new Film();

        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());

        //проверяем, есть ли в запросе рейтинг
        if (request.getMpa() != null) {
            Rating rating = ratingStorage.getById(request.getMpa().getId()).get();
            request.setMpa(rating);
        }

        //проверяем, если ли в запросе жанры, и если есть, прикручиваем к фильму
        if (request.getGenresId() != null) {
            film.setGenres(request.getGenresId());
        }

        return film;
    }
}
