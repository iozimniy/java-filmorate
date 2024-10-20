package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validations.FilmValidation;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FilmService {
    FilmStorage filmStorage;
    UserService userService;

    public FilmService(FilmStorage filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(Long filmId) {
        validateFilmId(filmId);
        return filmStorage.getFilmById(filmId);
    }

    public Film create(Film film) {
        FilmValidation.validateForCreate(film);

        log.info("Новый фильм добавлен: {}", film);
        return filmStorage.create(film);
    }

    public Film update(Film film) {
        FilmValidation.validateForUpdate(film);

        if (!(filmStorage.contains(film.getId()))) {
            log.warn("Не найден фильм с id {}", film.getId());
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден");
        }

        Film oldFilm = filmStorage.getFilmById(film.getId());

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

        log.info("Фильм изменён: {}", film);
        return filmStorage.update(film);
    }

    public void likeFilm(Long filmId, Long userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);

        Film film = filmStorage.getFilmById(filmId);
        film.getLikes().add(userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);

        Film film = filmStorage.getFilmById(filmId);
        film.getLikes().remove(userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        if (count == null) {
            count = 10;
        }

        Comparator<Film> filmLikesComparator = Comparator.comparingInt(o -> o.getLikes().size());

        return filmStorage.getFilms().stream()
                .sorted(filmLikesComparator.reversed())
                .limit(count)
                .collect(Collectors.toList());
    }

    //вспомогательные методы
    private void validateFilmId(Long filmId) {
        if (!filmStorage.contains(filmId)) {
            log.warn("Не найден фильм с id {}", filmId);
            throw new NotFoundException("Не найден фильм с id" + filmId);
        }
    }
}
