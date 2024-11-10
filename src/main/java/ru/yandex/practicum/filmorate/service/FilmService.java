package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.FilmLikesStorage;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;
import ru.yandex.practicum.filmorate.validations.FilmValidation;

import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class FilmService {
    FilmStorage filmStorage;
    UserService userService;
    FilmValidation filmValidation;
    FilmGenreService filmGenreService;
    FilmLikesStorage filmLikesStorage;
    RatingStorage ratingStorage;
    FilmMapper filmMapper;
    FilmGenreStorage filmGenreStorage;

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(Long filmId) {
        validateFilmId(filmId);
        Film film = filmStorage.getFilmById(filmId).get();
        film.setGenres(filmGenreStorage.getFilmGenres(filmId));
        log.info("Жанры {}", filmGenreStorage.getFilmGenres(filmId));
        return film;
    }

    public Film create(NewFilmRequest request) {
        filmValidation.validateForCreate(request);
        Film newFilm = filmMapper.mapToFilm(request);

        Film film = filmStorage.create(newFilm);
        log.info("Новый фильм добавлен: {}", newFilm);

        if (film.getGenres() != null) {
            film.getGenres().stream().forEach(genre -> filmGenreStorage.create(film.getId(), genre.getId()));
        }
        return film;
    }

    public Film update(Film film) {
        filmValidation.validateForUpdate(film);

        if (!filmStorage.contains(film.getId())) {
            log.warn("Не найден фильм с id {}", film.getId());
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден");
        }

        Film oldFilm = filmStorage.getFilmById(film.getId()).get();

        if (film.getName() == null) {
            film.setName(oldFilm.getName());
        }

        if (film.getDescription() == null) {
            film.setDescription(oldFilm.getDescription());
        }

        if (film.getReleaseDate() == null) {
            film.setReleaseDate(oldFilm.getReleaseDate());
        }

        if (film.getDuration() == null) {
            film.setDuration(oldFilm.getDuration());
        }

        if (film.getMpa() == null) {
            film.setMpa(oldFilm.getMpa());
        }

        if (film.getGenres() != null) {
            filmGenreService.update(film);
        }

        log.info("Фильм изменён: {}", film);
        return filmStorage.update(film);
    }

    public void likeFilm(Long filmId, Long userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);

        filmLikesStorage.addLikes(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);

        filmLikesStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        return filmLikesStorage.getPopularFilms(count);
    }

    //вспомогательные методы
    private void validateFilmId(Long filmId) {
        if (!filmStorage.contains(filmId)) {
            log.warn("Не найден фильм с id {}", filmId);
            throw new NotFoundException("Не найден фильм с id" + filmId);
        }
    }
}
