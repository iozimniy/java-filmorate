package ru.yandex.practicum.filmorate.mapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public final class FilmMapper {
    RatingStorage ratingStorage;
    GenreStorage genreStorage;

    public Film mapToFilm(NewFilmRequest request) {
        Film film = new Film();

        film.setName(request.getName());
        film.setDescription(request.getDescription());
        film.setReleaseDate(request.getReleaseDate());
        film.setDuration(request.getDuration());

        //проверяем, есть ли в запросе рейтинг
        if (request.getMpa() != null) {
            Rating rating = ratingStorage.getById(request.getMpa().getId()).get();
            film.setMpa(rating);
        }

        //проверяем, если ли в запросе жанры, и если есть, прикручиваем к фильму
        if (request.getGenres() != null) {
            Collection<Genre> genresList = genreStorage.getGenresById(request.getGenres().stream()
                    .map(genre -> genre.getId()).collect(Collectors.toList()));
            film.setGenres(genresList);
        }

        log.info("Запрос на добавление фильма прошёл стадию обработки: {}", film);
        return film;
    }
}
