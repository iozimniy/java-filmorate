package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.validations.FilmValidation;

import java.util.Collection;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class FilmService {
    FilmStorage filmStorage;
    UserService userService;
    FilmValidation filmValidation;
    FilmGenreService filmGenreService;

    public Collection<Film> getFilms() {
        return filmStorage.getFilms();
    }

    public Film getFilm(Long filmId) {
        validateId(filmStorage.getFilmById(filmId), filmId);
        return filmStorage.getFilmById(filmId).get();
    }

    public Film create(NewFilmRequest request) {
        filmValidation.validateForCreate(request);
        Film film = FilmMapper.mapToFilm(request);


        filmStorage.create(film);
        log.info("Новый фильм добавлен: {}", film);

        if (film.getGenres() != null) {
            filmGenreService.create(film);
        }

        return film;
    }

    public Film update(Film film) {
        FilmValidation.validateForUpdate(film);

        if (!(filmStorage.contains(film.getId()))) {
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

        log.info("Фильм изменён: {}", film);
        return filmStorage.update(film);
    }

    public void likeFilm(Long filmId, Long userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);

        filmStorage.addLikes(filmId, userId);
    }

    public void deleteLike(Long filmId, Long userId) {
        validateFilmId(filmId);
        userService.validateUserId(userId);

        filmStorage.deleteLike(filmId, userId);
    }

    public Collection<Film> getPopularFilms(Integer count) {
        if (count == null) {
            count = 10;
        }

        //Comparator<Film> filmLikesComparator = Comparator.comparingInt(o -> o.getLikes().size());
        return filmStorage.getSortedFilms(count);
    }

    //вспомогательные методы
    private void validateFilmId(Long filmId) {
        if (!filmStorage.contains(filmId)) {
            log.warn("Не найден фильм с id {}", filmId);
            throw new NotFoundException("Не найден фильм с id" + filmId);
        }
    }

    private void validateId(Optional<Film> mayBeFilm, Long filmId) {
        if (mayBeFilm.isEmpty()) {
            log.warn("Не найден фильм с id {}", filmId);
            throw new NotFoundException("Не найден фильм с id" + filmId);
        }
    }
}
