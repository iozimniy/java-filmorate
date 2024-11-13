package ru.yandex.practicum.filmorate.dal.mappers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

@Component
@AllArgsConstructor
@Slf4j
public class FilmRowMapper implements RowMapper<Film> {
    RatingStorage ratingStorage;
    FilmGenreStorage filmGenreStorage;

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
        Film film = new Film();
        film.setId(rs.getLong("film_id"));
        film.setName(rs.getString("film_name"));
        film.setDescription(rs.getString("description"));
        film.setReleaseDate(rs.getDate("release_date").toLocalDate());
        film.setDuration(rs.getInt("duration"));

        Long ratingId = rs.getLong("rating_id");
        if (ratingId != null) {
            film.setMpa(ratingStorage.getById(rs.getLong("rating_id")).get());
        }


        Collection<Genre> genres = filmGenreStorage.getFilmGenres(film.getId());
        if (!genres.isEmpty()) {
            film.setGenres(genres);
        }

        return film;
    }
}
