package ru.yandex.practicum.filmorate.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.InternalServerException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.util.Collection;
import java.util.List;

@Slf4j
@Repository
public class FilmGenreRepository extends BaseRepository<Genre> implements FilmGenreStorage {
    private final String CREATE_FILM_GENRE = "INSERT INTO film_genre(film_id, genre_id) VALUES(?, ?)";
    private final String DELETE_FILM_GENRE = "DELETE FROM film_genre WHERE film_id = ?";
    private final String FIND_FILM_GENRES = "SELECT DISTINCT g.* FROM film_genre as fg " +
            "LEFT JOIN genre AS g ON fg.genre_id = g.genre_id WHERE film_id = ?";

    public FilmGenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
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

    @Override
    public Collection<Genre> getFilmGenres(Long id) {
        return findMany(FIND_FILM_GENRES, id);
    }
}
