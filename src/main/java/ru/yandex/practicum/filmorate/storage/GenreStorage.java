package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface GenreStorage {
    Collection<Genre> getAll();

    Optional<Genre> getById(Long id);

    boolean isContainsId(Long id);

    Collection<Genre> getGenresById(List<Long> ids);
}
