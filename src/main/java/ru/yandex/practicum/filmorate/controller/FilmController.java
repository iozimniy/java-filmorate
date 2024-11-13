package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.NewFilmRequest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.Collection;

@RestController
@RequestMapping("/films")
@Slf4j
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping
    public Collection<Film> getFilms() {
        return filmService.getFilms();
    }

    @PostMapping
    public Film create(@Valid @RequestBody NewFilmRequest newFilm) {
        log.info("Получен запрос на добавление фильма: {}", newFilm);
        return filmService.create(newFilm);
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film updatedFilm) {
        log.info("Получен запрос на изменения фильма: {}", updatedFilm);
        return filmService.update(updatedFilm);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Film> getFilm(@PathVariable("id") Long filmId) {
        log.info("Получен запрос на возврат информации по фильму с id {}", filmId);
        return ResponseEntity.status(HttpStatus.OK).body(filmService.getFilm(filmId));
    }

    @PutMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void likeFilm(@PathVariable("id") Long filmId, @PathVariable Long userId) {
        log.info("Получен запрос на добавление лайка фильму с id {} от пользователя с id {}", filmId, userId);
        filmService.likeFilm(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteLike(@PathVariable("id") Long filmId, @PathVariable Long userId) {
        log.info("Получен запрос на удаление лайка у фильма с id {} от пользователя с id {}", filmId, userId);
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public ResponseEntity<Collection<Film>> getPopularFilms(@RequestParam(required = false) Integer count) {
        log.info("Получен запрос на самые популярные фильмы в количестве {}", count);
        return ResponseEntity.status(HttpStatus.OK).body(filmService.getPopularFilms(count));
    }
}
