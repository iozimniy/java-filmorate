package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

@AllArgsConstructor
@Service
public class FilmGenreService {
    private FilmGenreStorage filmGenreStorage;

    public void create(Film film) {
        film.getGenres().stream().forEach(genre -> {
            filmGenreStorage.create(film.getId(), genre.getId());
        });
    }

    public void update(Film film) {
        delete(film.getId());
        create(film);
    }

    public void delete(Long filmId) {
        filmGenreStorage.delete(filmId);
    }
}
