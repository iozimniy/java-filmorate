package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

import java.util.Collection;
import java.util.Optional;

@Repository
public class RatingRepository extends BaseRepository<Rating> implements RatingStorage {

    private static final String FIND_ALL_RATINGS = "SELECT * FROM rating";
    private static final String FIND_RATING_BY_ID = "SELECT * FROM rating WHERE rating_id = ?";

    public RatingRepository(JdbcTemplate jdbc, RowMapper<Rating> mapper) {
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
        return rating.isPresent();
    }
}
