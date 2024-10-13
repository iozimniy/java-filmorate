package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.Collection;

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
        return filmStorage.getFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody Film newFilm) {
        log.info("Получен запрос на добавление фильма: {}", newFilm);
        return filmStorage.create(newFilm);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film updatedFilm) {
        log.info("Получен запрос на изменения фильма: {}", updatedFilm);
        return filmStorage.update(updatedFilm);
    }

}
