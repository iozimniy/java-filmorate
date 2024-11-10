package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.GenreService;

import java.util.Collection;

@RestController
@RequestMapping("/genres")
@Slf4j
@AllArgsConstructor
public class GenreController {
    GenreService genreService;

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable("id") Long genreId) {
        log.info("Получен запрос на возврат информации о жанре с id {}", genreId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(genreService.getGenre(genreId));
    }

    @GetMapping
    public Collection<Genre> getGenre() {
        log.info("Получен запрос на возврат информации по всем жанрам");
        return genreService.getAll();
    }


}
