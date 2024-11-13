package ru.yandex.practicum.filmorate.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.FilmRepository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase
@Import(FilmRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class FilmRepositoryTests {

    private static final Long LAST_FILM_ID_IN_DATA = 2L;
    private static final Integer COUNT_FILMS_IN_DATA = 2;
    @Autowired
    private final FilmRepository filmRepository;

    @Test
    public void getFilmsTest() {
        Collection<Film> filmCollection = filmRepository.getFilms();

        assertAll(
                () -> assertSame(filmCollection.size(), COUNT_FILMS_IN_DATA)
        );
    }

    @Test
    public void createFilmTest() throws SQLException {
        Film film = new Film(null, "Новый фильм", "Описание нового фильма",
                new Rating(2L, "PG"), LocalDate.of(2017, 02, 19),
                60, List.of(new Genre(1L, "Комедия")));

        Long id = filmRepository.create(film).getId();

        assertSame(LAST_FILM_ID_IN_DATA + 1, id);
    }

    @Test
    public void updateFilmTest() throws SQLException {
        Film film = new Film(LAST_FILM_ID_IN_DATA, "Изменённый фильм фильм", "Описание изменённого фильма",
                new Rating(4L, "R"), LocalDate.of(2010, 11, 15),
                60, List.of(new Genre(6L, "Боевик")));

        filmRepository.update(film);

        assertAll(
                () -> assertSame(filmRepository.getFilmById(LAST_FILM_ID_IN_DATA).get().getName(), film.getName()),
                () -> assertSame(filmRepository.getFilmById(LAST_FILM_ID_IN_DATA).get().getDescription(),
                        film.getDescription()),
                () -> assertEquals(filmRepository.getFilmById(LAST_FILM_ID_IN_DATA).get().getMpa(),
                        film.getMpa()),
                () -> assertEquals(filmRepository.getFilmById(LAST_FILM_ID_IN_DATA).get().getReleaseDate(),
                        film.getReleaseDate()),
                () -> assertSame(filmRepository.getFilmById(LAST_FILM_ID_IN_DATA).get().getDuration(),
                        film.getDuration())
        );
    }

    @Test
    public void getFilmByIdTest() {
        Optional<Film> film = filmRepository.getFilmById(LAST_FILM_ID_IN_DATA);

        assertTrue(film.isPresent());
    }

    @Test
    public void getFilmContainsTest() {
        assertTrue(filmRepository.contains(LAST_FILM_ID_IN_DATA));
    }
}
