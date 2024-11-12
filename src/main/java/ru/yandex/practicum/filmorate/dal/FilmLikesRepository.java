package ru.yandex.practicum.filmorate.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.exceptions.InternalServerException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmLikesStorage;

import java.sql.SQLException;
import java.util.Collection;

@Repository
@Slf4j
public class FilmLikesRepository extends BaseRepository<Film> implements FilmLikesStorage {
    private static final String CREATE_FILM_LIKE = "INSERT INTO film_likes(film_id, user_id) VALUES(?, ?)";
    private static final String DELETE_FILM_LIKE = "DELETE FROM film_likes WHERE film_id = ? AND user_id = ?";
    private static final String GET_FILM_COUNT_LIKES = "SELECT COUNT(*) FROM film_likes WHERE film_id = ?";
    private static final String FIND_POPULAR_FILMS = "SELECT f.* FROM films as f " +
            "LEFT JOIN film_likes fl ON f.film_id = fl.film_id GROUP BY f.film_id ORDER BY COUNT(fl.user_id) DESC LIMIT ?;";

    public FilmLikesRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void addLikes(Long filmId, Long userId) throws SQLException {
        Integer result = jdbc.update(
                CREATE_FILM_LIKE,
                filmId,
                userId
        );

        if (result == 0) {
            log.error("Не удалось добавить лайк фильму с id {} от пользователя с iв {}", filmId, userId);
            throw new SQLException("Не удалось сохранить данные");
        }
    }

    @Override
    public void deleteLike(Long filmId, Long userId) throws SQLException {
        Integer result = jdbc.update(
                DELETE_FILM_LIKE,
                filmId,
                userId
        );

        if (result == 0) {
            log.error("Не удалось удалить лайк у фильма с id {} от пользователя с id {}", filmId, userId);
            throw new SQLException("Не удалось удалить данные");
        }
    }

    public Integer getCountFilmLikes(Long filmId) {
        return jdbc.queryForObject(GET_FILM_COUNT_LIKES, Integer.class, filmId);
    }

    @Override
    public Collection<Film> getPopularFilms(Integer count) {
        return findMany(FIND_POPULAR_FILMS, count);
    }
}
