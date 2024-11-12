package ru.yandex.practicum.filmorate.dal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class BaseRepository<T> {
    protected final JdbcTemplate jdbc;
    protected final RowMapper<T> mapper;

    protected Optional<T> findOne(String query, Object... params) {
        try {
            T result = jdbc.queryForObject(query, mapper, params);
            return Optional.ofNullable(result);
        } catch (DataAccessException e) {
            log.info(e.getMessage());
            return Optional.empty();
        }
    }

    protected List<T> findMany(String query, Object... params) {
        return jdbc.query(query, mapper, params);
    }

    protected long insert(String query, Object... params) throws SQLException {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(connection -> {
            PreparedStatement ps = connection
                    .prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int idx = 0; idx < params.length; idx++) {
                ps.setObject(idx + 1, params[idx]);
            }

            return ps;
        }, keyHolder);

        Long id = keyHolder.getKeyAs(Integer.class).longValue();

        if (id != null) {
            return id;
        } else {
            log.error("Не удалось добавить запись в таблицу по запросу {} c параметрами {}", query, params);
            throw new SQLException("Не удалось сохранить данные.");
        }
    }

    protected void update(String query, Object... params) throws SQLException {
        int rowUpdated = jdbc.update(query, params);
        if (rowUpdated == 0) {
            log.error("Не удалось обновить данные по запросу {} c параметрами {}", query, params);
            throw new SQLException("Не удалось обновить данные");
        }
    }
}
