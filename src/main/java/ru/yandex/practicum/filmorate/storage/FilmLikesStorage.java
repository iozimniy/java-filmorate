package ru.yandex.practicum.filmorate.storage;

public interface FilmLikesStorage {
    void addLikes(Long filmId, Long userId);

    void deleteLike(Long filmId, Long userId);
}
