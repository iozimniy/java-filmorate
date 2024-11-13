package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.validations.GenreValidation;

import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class GenreService {
    GenreStorage genreStorage;
    GenreValidation genreValidation;

    public Genre getGenre(Long id) {
        genreValidation.validateGenreId(id);
        return genreStorage.getById(id).get();
    }

    public Collection<Genre> getAll() {
        return genreStorage.getAll();
    }
}
