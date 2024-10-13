package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.validations.FilmValidation;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    //Map<Long, Film> films = new HashMap<>();

    private FilmStorage filmStorage;

    public FilmController(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmStorage.getAll();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film newFilm) {
        log.info("Получен запрос на добавление фильма: {}", newFilm);

        //FilmValidation.validateForCreate(newFilm);

//        newFilm.setId(getNextId());
//        films.put(newFilm.getId(), newFilm);

//        log.info("Новый фильм добавлен: {}", newFilm);
        return filmStorage.create(newFilm);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film updatedFilm) {
        log.info("Получен запрос на изменения фильма: {}", updatedFilm);

//        FilmValidation.validateForUpdate(updatedFilm);
//
//        if (!(films.containsKey(updatedFilm.getId()))) {
//            log.warn("Не найден фильм с id {}", updatedFilm.getId());
//            throw new NotFoundException("Фильм с id " + updatedFilm.getId() + " не найден");
//        }
//
//        Film oldFilm = films.get(updatedFilm.getId());
//
//        if (updatedFilm.getName() != null) {
//            oldFilm.setName(updatedFilm.getName());
//        }
//
//        if (updatedFilm.getDescription() != null) {
//            oldFilm.setDescription(updatedFilm.getDescription());
//        }
//
//        if (updatedFilm.getReleaseDate() != null) {
//            oldFilm.setReleaseDate(updatedFilm.getReleaseDate());
//        }
//
//        if (updatedFilm.getDuration() != null) {
//            oldFilm.setDuration(updatedFilm.getDuration());
//        }
//
//        log.info("Фильм изменён: {}", oldFilm);
        return filmStorage.update(updatedFilm);
    }

}
