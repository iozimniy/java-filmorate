package ru.yandex.practicum.filmorate.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FilmLikesRepository;
import ru.yandex.practicum.filmorate.model.Film;

import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertSame;

@JdbcTest
@AutoConfigureTestDatabase
@Import(FilmRepositoryTests.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class FilmLikesRepositoryTests {
    private static final Long FIRST_FILM_WITH_TWO_LIKES_ID = 1L;
    private static final Integer COUNT_LIKES_OF_FIRST_FILM = 2;
    private static final Long USER_FOR_ADD_LIKE_ID = 4L;
    private static final Long USER_FOR_DELETE_LIKE_ID = 2L;
    private static final Long SECOND_FILM_WITH_ONE_LIKE = 2L;
    @Autowired
    FilmLikesRepository filmLikesRepository;

    @Test
    public void addLikesTest() throws SQLException {
        filmLikesRepository.addLikes(FIRST_FILM_WITH_TWO_LIKES_ID, USER_FOR_ADD_LIKE_ID);
        assertSame(COUNT_LIKES_OF_FIRST_FILM + 1,
                filmLikesRepository.getCountFilmLikes(FIRST_FILM_WITH_TWO_LIKES_ID));
    }

    @Test
    public void deleteLikeTest() throws SQLException {
        filmLikesRepository.deleteLike(FIRST_FILM_WITH_TWO_LIKES_ID, USER_FOR_DELETE_LIKE_ID);
        assertSame(COUNT_LIKES_OF_FIRST_FILM - 1,
                filmLikesRepository.getCountFilmLikes(FIRST_FILM_WITH_TWO_LIKES_ID));
    }

    @Test
    public void getPopularFilmsTest() {
        Collection<Film> popularFilm = filmLikesRepository.getPopularFilms(2);
        Film film = popularFilm.stream().findFirst().get();
        assertSame(FIRST_FILM_WITH_TWO_LIKES_ID, film.getId());
    }
}
