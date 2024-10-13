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

    public Collection<Film> getFilms() {
        return films.values();
    }

    public Film create(Film film) {
        film.setId(getNextId());
        films.put(film.getId(), film);
        log.info("Новый фильм добавлен в хранилище {}", film);
        return films.get(film.getId());
    }

    public Film update(Film film) {
        films.remove(film.getId());
        films.put(film.getId(), film);
        return getFilmById(film.getId());
    }

    // вспомогательные методы

    public boolean isContains(Long id) {
        return films.containsKey(id);
    }

    public Film getFilmById(Long id) {
        return films.get(id);
    }

    private Long getNextId() {
        Long currentMaxId = films.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
