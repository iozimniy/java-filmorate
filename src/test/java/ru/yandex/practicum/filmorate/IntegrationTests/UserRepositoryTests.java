package ru.yandex.practicum.filmorate.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.UserRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase
@Import(UserRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class UserRepositoryTests {

    private static final Long LAST_USER_ID_IN_DATA = 4L;
    private static final Integer COUNT_USER_IN_DATA = 4;
    @Autowired
    UserRepository userRepository;

    @Test
    public void getUsersTest() {
        Collection<User> users = userRepository.getUsers();

        assertSame(COUNT_USER_IN_DATA, users.size());
    }

    @Test
    public void createUserTest() {
        User user = new User(null, "test3@test.com", "uses3", "Пользователь 3",
                LocalDate.of(2000, 12, 30));

        Long id = userRepository.create(user).getId();

        assertSame(LAST_USER_ID_IN_DATA + 1, id);
    }

    @Test
    public void updateUserTest() {
        User user = new User(LAST_USER_ID_IN_DATA, "change@test.com", "changeUser", "Изменённый пользователь",
                LocalDate.of(1995, 5, 12));
        User changeUser = userRepository.update(user);

        assertAll(
                () -> assertSame(user.getEmail(), changeUser.getEmail()),
                () -> assertSame(user.getLogin(), changeUser.getLogin()),
                () -> assertSame(user.getName(), changeUser.getName()),
                () -> assertEquals(user.getBirthday(), changeUser.getBirthday())
        );

    }

    @Test
    public void containsUserTest() {
        assertTrue(userRepository.contains(LAST_USER_ID_IN_DATA));
    }

    @Test
    public void getUserByIdTest() {
        Optional<User> user = userRepository.getUserById(LAST_USER_ID_IN_DATA);
        assertTrue(user.isPresent());
    }

}
