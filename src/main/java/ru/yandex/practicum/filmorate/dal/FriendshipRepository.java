package ru.yandex.practicum.filmorate.dal;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.FriendshipStorage;

import java.sql.SQLException;
import java.util.Collection;

@Slf4j
@Repository
public class FriendshipRepository extends BaseRepository<User> implements FriendshipStorage {
    private static final String FIND_USER_FRIENDS_ID = "SELECT friend_id FROM friendship WHERE user_id = ?";
    private static final String FIND_ALL_USER_FRIENDS = "SELECT u.* FROM friendship as f " +
            "JOIN users as u ON f.friend_id = u.user_id WHERE f.user_id = ?;";
    private static final String FIND_COMMON_FRIENDS = "SELECT u.* FROM Users as u " +
            "JOIN friendship as f1 ON u.user_id = f1.friend_id " +
            "JOIN friendship as f2 ON u.user_id = f2.friend_id " +
            "WHERE f1.user_id = ? AND f2.user_id = ?;";
    private static final String CREATE_FRIENDSHIP = "INSERT INTO friendship(user_id, friend_id) VALUES(?, ?)";
    private static final String DELETE_FRIEND = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";

    public FriendshipRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<Long> getIdFriends(Long userId) {
        return jdbc.queryForList(FIND_USER_FRIENDS_ID, Long.class, userId);
    }

    @Override
    public void addFriend(Long userId, Long friendId) throws SQLException {
        Integer result = jdbc.update(CREATE_FRIENDSHIP, userId, friendId);
        if (result == 0) {
            log.error("Не удалось добавить запись с user_id {} и friend_id {}", userId, friendId);
            throw new SQLException("Не удалось сохранить данные");
        }
    }

    @Override
    public void deleteFriend(Long userId, Long friendId) throws SQLException {
        Integer result = jdbc.update(DELETE_FRIEND, userId, friendId);
        if (result == 0) {
            log.error("Не удалось удалить запись с user_id {} и friend_id {}", userId, friendId);
            throw new SQLException("Не удалось сохранить данные");
        }
    }

    @Override
    public Collection<User> getFriends(Long userId) {
        return findMany(FIND_ALL_USER_FRIENDS, userId);
    }

    @Override
    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        return findMany(FIND_COMMON_FRIENDS, userId, otherUserId);
    }
}
