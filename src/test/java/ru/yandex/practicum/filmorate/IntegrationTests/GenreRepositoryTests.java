package ru.yandex.practicum.filmorate.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase
@Import(GenreRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class GenreRepositoryTests {

    @Autowired
    GenreRepository genreRepository;
    private static Long FIRST_GENRE_ID_IN_DATA = 1L;
    private static Integer COUNT_GENRE_IN_DATA = 6;

    @Test
    public void getAllRatingTest() {
        Collection<Genre> ratings = genreRepository.getAll();

        assertSame(COUNT_GENRE_IN_DATA, ratings.size());
    }

    @Test
    public void getRatingByIdTest() {
        Optional<Genre> rating = genreRepository.getById(FIRST_GENRE_ID_IN_DATA);

        assertTrue(rating.isPresent());
    }

    @Test
    public void isContainsRatingIdTest() {
        assertTrue(genreRepository.isContainsId(FIRST_GENRE_ID_IN_DATA));
    }
}
