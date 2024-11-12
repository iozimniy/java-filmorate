package ru.yandex.practicum.filmorate.validations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

@Service
@Slf4j
@AllArgsConstructor
public class GenreValidation {
    GenreStorage genreStorage;

    public void validateGenreId(Long id) {
        if (!genreStorage.contains(id)) {
            log.warn("Не существует жанра с id {}", id);
            throw new NotFoundException("Не существует жанра с id " + id);
        }
    }
}
