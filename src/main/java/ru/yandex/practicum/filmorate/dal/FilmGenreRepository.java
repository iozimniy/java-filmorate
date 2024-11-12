package ru.yandex.practicum.filmorate.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Repository
public class FilmGenreRepository extends BaseRepository<Genre> implements FilmGenreStorage {
    private static final String CREATE_FILM_GENRE = "INSERT INTO film_genre(film_id, genre_id) VALUES(?, ?)";
    private static final String DELETE_FILM_GENRE = "DELETE FROM film_genre WHERE film_id = ?";
    private static final String FIND_FILM_GENRES = "SELECT DISTINCT g.* FROM film_genre as fg " +
            "LEFT JOIN genre AS g ON fg.genre_id = g.genre_id WHERE film_id = ?";
    private static final String CREATE_MANY_FILM_GENRE = "INSERT INTO film_genre(film_id, genre_id) VALUES (?, ?);";

    public FilmGenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public void create(List<FilmGenre> filmGenres) throws SQLException {
        List<Object[]> batch = new ArrayList<>();
        filmGenres.stream().forEach(filmGenre -> {
            Object[] values = new Object[]{
                    filmGenre.getFilmId(), filmGenre.getGenreId()
            };
            batch.add(values);
        });

        jdbc.batchUpdate(CREATE_MANY_FILM_GENRE, batch);
    }

    @Override
    public void delete(Long filmId) throws SQLException {
        Integer result = jdbc.update(
                DELETE_FILM_GENRE,
                filmId
        );

        if (result == 0) {
            log.error("Не удалось удалить записи с film_id {}", filmId);
            throw new SQLException("Не удалось удалить данные");
        }
    }

    @Override
    public Collection<Genre> getFilmGenres(Long id) {
        return findMany(FIND_FILM_GENRES, id);
    }
}
