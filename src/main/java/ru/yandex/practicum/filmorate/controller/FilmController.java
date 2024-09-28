package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/films")
public class FilmController {
    Map<Integer, Film> films = new HashMap<>();

    @GetMapping
    public Collection<Film> getFilms() {
        return films.values();
    }

    @PostMapping
    public Film create(@RequestBody Film newFilm) {
        //тут должна быть логика создания фильма c валидацией
        return newFilm;
    }

    @PutMapping
    public Film update(@RequestBody Film updatedFilm) {
        //тут должна быть логика апдейта фильма c валидацией
        return updatedFilm;
    }

    // вспомогательные методы

    //метод для id

}
