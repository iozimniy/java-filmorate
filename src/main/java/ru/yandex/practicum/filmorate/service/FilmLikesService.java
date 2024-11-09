package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmLikesStorage;

import java.util.Collection;

@Service
@AllArgsConstructor
public class FilmLikesService {

    FilmLikesStorage filmLikesStorage;

    public Collection<Film> getPopularFilms(Integer count) {
        if (count == null) {
            count = 10;
        }

        return filmLikesStorage.getPopularFilms(count);
    }
}
