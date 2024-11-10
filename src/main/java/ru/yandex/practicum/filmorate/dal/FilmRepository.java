package ru.yandex.practicum.filmorate.dal;


import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Optional;

@Repository
@Slf4j
public class FilmRepository extends BaseRepository<Film> implements FilmStorage {

    //Так сделано из-за того, что в запрос не передаётся параметр.
    //Почему так просходит, не понятно.
    private static final String FIND_FILM_BY_ID = "SELECT * FROM films WHERE film_id = ";
    private static final String FIND_ALL_FILMS = "SELECT * FROM films;";
    private static final String CREATE_FILM = "INSERT INTO films(film_name, description, rating_id, release_date, duration)" +
            "VALUES(?, ?, ?, ?, ?);";
    private static final String UPDATE_FILM = "UPDATE films SET film_name = ?, " +
            "description = ?, rating_id = ?, release_date = ?, duration = ? WHERE film_id = ?;";

    public FilmRepository(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }


    @Override
    public Collection<Film> getFilms() {
        return findMany(FIND_ALL_FILMS);
    }

    @Override
    public Film create(Film film) {
        Long id = insert(
                CREATE_FILM,
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                film.getDuration()
        );

        film.setId(id);
        return film;
    }

    @Override
    public Film update(Film film) {
        update(
                UPDATE_FILM,
                film.getName(),
                film.getDescription(),
                film.getMpa().getId(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId()
        );

        return film;
    }

    @Override
    public boolean contains(Long id) {
        Optional<Film> film = getFilmById(id);
        return film.isPresent();
    }

    @Override
    public Optional<Film> getFilmById(Long filmId) {
        return findOne(FIND_FILM_BY_ID + filmId);
    }
}
