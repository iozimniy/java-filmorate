package ru.yandex.practicum.filmorate.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FriendshipRepository;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase
@Import(FriendshipRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class FriendshipRepositoryTests {

    @Autowired
    FriendshipRepository friendshipRepository;
    private static Integer COUNT_FRIENDS_FIRST_USER = 2;
    private static final Long FIRST_USER_ID_IN_DATA = 1L;
    private static final Long FRIEND_ID_TO_ADD = 4L;
    private static final Long FRIEND_ID_TO_DELETE = 2L;
    private static final Long USER_ID_FOR_COMMON_FRIENDS = 2L;
    private static final Integer COMMON_FRIENDS_COUNT = 1;

    @Test
    public void getIdFriendsTest() {
        Collection<Long> friends = friendshipRepository.getIdFriends(FIRST_USER_ID_IN_DATA);

        assertSame(COUNT_FRIENDS_FIRST_USER, friends.size());
    }

    @Test
    public void addFriendTest() {
        friendshipRepository.addFriend(FIRST_USER_ID_IN_DATA, FRIEND_ID_TO_ADD);
        Collection<Long> friends = friendshipRepository.getIdFriends(FIRST_USER_ID_IN_DATA);
        assertSame(COUNT_FRIENDS_FIRST_USER + 1, friends.size());
    }

    @Test
    public void deleteFriendTest() {
        friendshipRepository.deleteFriend(FIRST_USER_ID_IN_DATA, FRIEND_ID_TO_DELETE);
        Collection<Long> friends = friendshipRepository.getIdFriends(FIRST_USER_ID_IN_DATA);
        assertSame(COUNT_FRIENDS_FIRST_USER - 1, friends.size());
    }

    @Test
    public void getFriendsTest() {
        Collection<User> friends = friendshipRepository.getFriends(FIRST_USER_ID_IN_DATA);
        assertSame(COUNT_FRIENDS_FIRST_USER, friends.size());
    }

    @Test
    public void getCommonFriendsTest() {
        Collection<User> commonFriends = friendshipRepository.getCommonFriends(FIRST_USER_ID_IN_DATA,
                USER_ID_FOR_COMMON_FRIENDS);
        assertSame(COMMON_FRIENDS_COUNT, commonFriends.size());
    }
}
