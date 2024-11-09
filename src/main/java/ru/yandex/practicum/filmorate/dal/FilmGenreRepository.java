package ru.yandex.practicum.filmorate.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.exceptions.InternalServerException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

@Slf4j
public class FilmGenreRepository implements FilmGenreStorage {

    JdbcTemplate jdbc;
    private final String CREATE_FILM_GENRE = "INSERT INTO film_genre(film_id, genre_id) VALUES(?, ?)";
    private final String DELETE_FILM_GENRE = "DELETE FROM film_genre WHERE film_d = ?";

    public FilmGenreRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public void create(Long filmId, Long genreId) {
        Integer result = jdbc.update(
                CREATE_FILM_GENRE,
                filmId,
                genreId
        );

        if (result == 0) {
            log.error("Не удалось добавить запись с film_id {} и genre_id {}", filmId, genreId);
            throw new InternalServerException("Не удалось сохранить данные");
        }
    }

    @Override
    public void delete(Long filmId) {
        Integer result = jdbc.update(
                DELETE_FILM_GENRE,
                filmId
        );

        if (result == 0) {
            log.error("Не удалось удалить записи с film_id {}", filmId);
            throw new InternalServerException("Не удалось удалить данные");
        }
    }
}
