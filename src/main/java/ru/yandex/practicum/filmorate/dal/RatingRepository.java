package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.Collection;
import java.util.Optional;

public class RatingRepository extends BaseRepository implements RatingStorage {

    private static String FIND_ALL_RATINGS = "SELECT * FROM rating";
    private static String FIND_RATING_BY_ID = "SELECT * FROM rating WHERE rating_id = ?";

    public RatingRepository(JdbcTemplate jdbc, RowMapper mapper) {
        super(jdbc, mapper);
    }

    public Collection<Rating> getAll() {
        return findMany(FIND_ALL_RATINGS);
    }

    public Optional<Rating> getById(Long id) {
        return findOne(FIND_RATING_BY_ID, id);
    }

    public boolean isContainsId(Long id) {
        Optional<Rating> rating = findOne(FIND_RATING_BY_ID, id);
        if (rating.isPresent()) {return true;}

        return false;
    }
}
