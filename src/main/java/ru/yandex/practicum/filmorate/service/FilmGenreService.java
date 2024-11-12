package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.InternalServerException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.sql.SQLException;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Slf4j
public class FilmGenreService {
    private FilmGenreStorage filmGenreStorage;

    public void create(Film film) {
        try {
            filmGenreStorage.create(film.getGenres().stream()
                    .map(genre -> new FilmGenre(film.getId(), genre.getId()))
                    .collect(Collectors.toList())
            );
        } catch (SQLException e) {
            log.info("Ошибка обращения к DB: {}", e.getMessage());
            throw new InternalServerException("Что-то пошло нет. Повторите запрос позже.");
        }
    }

    public void update(Film film) {
        delete(film.getId());
        create(film);
    }

    public void delete(Long filmId) {
        try {
            filmGenreStorage.delete(filmId);
        } catch (SQLException e) {
            log.info("Ошибка обращения к DB: {}", e.getMessage());
            throw new InternalServerException("Что-то пошло нет. Повторите запрос позже.");
        }
    }
}
