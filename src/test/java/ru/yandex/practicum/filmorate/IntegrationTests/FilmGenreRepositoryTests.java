package ru.yandex.practicum.filmorate.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FilmGenreRepository;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.SQLException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertSame;

@JdbcTest
@AutoConfigureTestDatabase
@Import(FilmGenreRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class FilmGenreRepositoryTests {
    private static final Long FILM_WITH_GENRES = 1L;
    private static final Integer COUNT_GENRES_OF_FILM = 2;
    private static final Long GENRE_ID_TO_ADD = 5L;
    @Autowired
    FilmGenreRepository filmGenreRepository;
    //private static final Long GENRE_ID_TO_DELETE = 3L;

    @Test
    public void createFilmGenreTest() throws SQLException {
        filmGenreRepository.create(FILM_WITH_GENRES, GENRE_ID_TO_ADD);
        Integer countGenres = filmGenreRepository.getFilmGenres(FILM_WITH_GENRES).size();
        assertSame(COUNT_GENRES_OF_FILM + 1, countGenres);
    }

    @Test
    public void deleteFilmGenreTest() throws SQLException {
        filmGenreRepository.delete(FILM_WITH_GENRES);
        Integer countGenres = filmGenreRepository.getFilmGenres(FILM_WITH_GENRES).size();
        assertSame(0, countGenres);
    }

    @Test
    public void getFilmGenresTest() {
        Collection<Genre> genres = filmGenreRepository.getFilmGenres(FILM_WITH_GENRES);
        assertSame(COUNT_GENRES_OF_FILM, genres.size());
    }
}
