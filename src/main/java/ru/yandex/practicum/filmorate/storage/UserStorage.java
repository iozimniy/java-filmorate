package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

public interface UserStorage {

    Collection<User> getUsers();

    User create(User user) throws SQLException;

    User update(User user) throws SQLException;

    boolean contains(Long id);

    Optional<User> getUserById(Long id);
}
