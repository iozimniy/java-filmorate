package ru.yandex.practicum.filmorate.dal;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.Collection;
import java.util.Optional;

@Repository
public class UserRepository extends BaseRepository<User> implements UserStorage {

    private static final String FIND_ALL_USERS = "SELECT * FROM users";
    private static final String FIND_USER_BY_ID = "SELECT * FROM users WHERE user_id = ?";
    private static final String CREATE_USER = "INSERT INTO users(email, login, user_name, birthday) " +
            "VALUES(?, ?, ?, ?)";
    private static final String UPDATE_USER = "UPDATE users SET email = ?, login = ?, user_name = ?, birthday = ?" +
            "WHERE user_id = ?";

    public UserRepository(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    @Override
    public Collection<User> getUsers() {
        return findMany(FIND_ALL_USERS);
    }

    @Override
    public User create(User user) {
        Long id = insert(
                CREATE_USER,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );

        user.setId(id);
        return user;
    }

    @Override
    public User update(User user) {
        update(
                UPDATE_USER,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        return user;
    }

    @Override
    public boolean contains(Long id) {
        Optional<User> user = getUserById(id);
        return user.isPresent();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return findOne(FIND_USER_BY_ID, id);
    }
}
