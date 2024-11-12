package ru.yandex.practicum.filmorate.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.GenreRepository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase
@Import(GenreRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class GenreRepositoryTests {

    private static final Long FIRST_GENRE_ID_IN_DATA = 1L;
    private static final Integer COUNT_GENRE_IN_DATA = 6;
    private static final List<Long> LIST_IDS = List.of(1L, 2L, 3L);
    @Autowired
    GenreRepository genreRepository;

    @Test
    public void getAllRatingTest() {
        Collection<Genre> ratings = genreRepository.getAll();

        assertSame(COUNT_GENRE_IN_DATA, ratings.size());
    }

    @Test
    public void getByIdTest() {
        Optional<Genre> genre = genreRepository.getById(FIRST_GENRE_ID_IN_DATA);
        assertTrue(genre.isPresent());
    }

    @Test
    public void isContainsGenreIdTest() {
        assertTrue(genreRepository.contains(FIRST_GENRE_ID_IN_DATA));
    }

    @Test
    public void getAllIdTest() {
        Collection<Long> genres = genreRepository.getAllId();
        assertSame(COUNT_GENRE_IN_DATA, genres.size());
    }

    @Test
    public void getGenresByTest() {
        Collection<Genre> genres = genreRepository.getGenresById(LIST_IDS);
        assertSame(LIST_IDS.size(), genres.size());
    }
}
