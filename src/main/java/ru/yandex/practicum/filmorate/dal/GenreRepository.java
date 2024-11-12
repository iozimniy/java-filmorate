package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class GenreRepository extends BaseRepository<Genre> implements GenreStorage {

    private static final String FIND_ALL_GENRES = "SELECT * FROM genre";
    private static final String FIND_ALL_GENRES_ID = "SELECT genre_id FROM genre";
    private static final String FIND_GENRE_BY_ID = "SELECT * FROM genre WHERE genre_id = ?";
    private static final String EXIST_GENRES = "SELECT EXISTS(SELECT * FROM genre WHERE genre_id = ?)";
    private static final String FIND_MANY_GENRES_BY_LIST_ID = "SELECT * FROM genre WHERE genre_id IN (%s)";

    public GenreRepository(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public Collection<Genre> getAll() {
        return findMany(FIND_ALL_GENRES);
    }

    public Optional<Genre> getById(Long id) {
        return findOne(FIND_GENRE_BY_ID, id);
    }

    public List<Long> getAllId() {
        return jdbc.queryForList(FIND_ALL_GENRES_ID, Long.class);
    }

    public Collection<Genre> getGenresById(List<Long> ids) {
        String inParams = String.join(",", Collections.nCopies(ids.size(), "?"));
        String query = String.format(FIND_MANY_GENRES_BY_LIST_ID, inParams);
        return findMany(query, ids.toArray());
    }

    public Boolean contains(Long id) {
        return jdbc.queryForObject(EXIST_GENRES, Boolean.class, id);
    }
}
