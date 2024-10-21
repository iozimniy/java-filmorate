package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    Map<Long, User> users = new HashMap<>();

    public Collection<User> getUsers() {
        return users.values();
    }

    public User create(User user) {
//        UserValidation.validateForCreate(user);

        user.setId(getNextId());
        users.put(user.getId(), user);
        log.info("Новый пользователь добавлен в хранилище {}", user);
        return user;
    }

    public User update(User user) {
        users.remove(user.getId());
        users.put(user.getId(), user);
        return getUserById(user.getId());
    }

    @Override
    public Collection<User> getUserFriends(Long userId) {
        return users.get(userId).getFriends().stream().map(id -> users.get(id)).collect(Collectors.toList());
    }

    @Override
    public Collection<User> getCommonFriends(Long userId, Long otherUserId) {
        return users.get(otherUserId).getFriends().stream()
                .filter(id -> users.get(userId).getFriends().contains(id))
                .map(id -> users.get(id))
                .collect(Collectors.toList());
    }

    // вспомогательные методы

    public boolean contains(Long id) {
        return users.containsKey(id);
    }

    public User getUserById(Long id) {
        return users.get(id);
    }

    private Long getNextId() {
        Long currentMaxId = users.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }

}
