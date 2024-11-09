package ru.yandex.practicum.filmorate.dal;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.Collection;
import java.util.Optional;

@Repository
public class FilmRepository extends BaseRepository<Film> implements FilmStorage {

    private static final String FIND_FILM_BY_ID = "SELECT * FROM films WHERE film_id = ?";
    private static final String FIND_ALL_FILMS = "SELECT * FROM films";
    private static final String CREATE_FILM = "INSERT INTO films(film_name, description, rating_id, release_date, duration)" +
            "VALUES(?, ?, ?, ?, ?) returning film_id";
    private static final String UPDATE_FILM = "UPDATE films SET film_name = ?, " +
            "description = ?, rating_id = ?, release_date = ?, duration = ? WHERE film_id = ?";

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
                film.getRatingId(),
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
                film.getRatingId(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getId()
        );

        return film;
    }

    @Override
    public boolean contains(Long id) {
        Optional<Film> film = getFilmById(id);
        if (film.isPresent()) {
            return true;
        }

        return false;
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        return findOne(FIND_FILM_BY_ID, id);
    }
}
