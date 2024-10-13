package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validations.FilmValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private Map<Long, Film> films = new HashMap<>();

    public Collection<Film> getAll() {
        return films.values();
    }

    public Film create(Film film) {
        FilmValidation.validateForCreate(film);

        film.setId(getNextId());
        films.put(film.getId(), film);

        log.info("Новый фильм добавлен: {}", film);
        return film;
    }

    public Film update(Film film) {
        FilmValidation.validateForUpdate(film);

        if (!(films.containsKey(film.getId()))) {
            log.warn("Не найден фильм с id {}", film.getId());
            throw new NotFoundException("Фильм с id " + film.getId() + " не найден");
        }

        Film oldFilm = films.get(film.getId());

        if (film.getName() != null) {
            oldFilm.setName(film.getName());
        }

        if (film.getDescription() != null) {
            oldFilm.setDescription(film.getDescription());
        }

        if (film.getReleaseDate() != null) {
            oldFilm.setReleaseDate(film.getReleaseDate());
        }

        if (film.getDuration() != null) {
            oldFilm.setDuration(film.getDuration());
        }

        log.info("Фильм изменён: {}", oldFilm);
        return oldFilm;
    }

    // вспомогательные методы

    private Long getNextId() {
        Long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
