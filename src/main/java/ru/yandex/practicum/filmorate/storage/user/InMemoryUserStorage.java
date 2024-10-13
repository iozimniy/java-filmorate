package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
        log.info("Создан новый пользователь: {}", user);
        return user;
    }

    public User update(User user) {
        users.remove(user.getId());
        users.put(user.getId(), user);
        return getUserById(user.getId());
    }

    // вспомогательные методы

    public boolean isContains(Long id) {
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
