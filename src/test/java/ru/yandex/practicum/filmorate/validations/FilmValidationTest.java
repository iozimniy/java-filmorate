package ru.yandex.practicum.filmorate.validations;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.controller.FilmController;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.Duration;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilmValidationTest {
    Film testFilm;
    FilmController filmController = new FilmController();
    String incorrect201Description = "testdiscriptiontestdiscriptiontestdiscriptiontestdiscriptiontestdiscription" +
            "testdiscriptiontestdiscriptiontestdiscriptiontestdiscriptiontestdiscriptiontestdiscriptiontestdiscription" +
            "testdiscriptiontestdi";
    String correct200Description = "testdiscriptiontestdiscriptiontestdiscriptiontestdiscriptiontestdiscription" +
            "testdiscriptiontestdiscriptiontestdiscriptiontestdiscriptiontestdiscriptiontestdiscriptiontestdiscription" +
            "testdiscriptiontestd";

    @Test
    public void createFilmNameIsNullValidationException() {
        Film film = new Film(1L, null, "Самый крутой фильм",
                LocalDate.of(1985,7, 3), 116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForCreate(film));
    }

    @Test
    public void createFilmNameIsBlankValidationException() {
        Film film = new Film(1L, "   ", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), 166);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForCreate(film));
    }

    @Test
    public void createFilmDescription201ValidationException() {
        Film film = new Film(1L, "Назад в будущее", incorrect201Description,
                LocalDate.of(1985,7, 3), 116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForCreate(film));
    }

    @Test
    public void createFilmDescription200ValidationException() {
        Film film = new Film(1L, "Назад в будущее", correct200Description,
                LocalDate.of(1985,7, 3), 116);
        assertDoesNotThrow(() -> FilmValidation.validateForCreate(film));
    }

    @Test
    public void createFilmReleaseDateIsBeforeMinReleaseDateValidationException() {
        Film film = new Film(1L, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1895,12, 27), 116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForCreate(film));
    }

    @Test
    public void createFilmDurationIsNegativeValidationException() {
        Film film = new Film(1L, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), -116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForCreate(film));
    }

    @Test
    public void createFilmDurationIsZeroValidationException() {
        Film film = new Film(1L, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), 0);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForCreate(film));
    }

    @Test
    public void updateFilmIdIsNullValidationException() {
        Film film = new Film(null, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), 116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForUpdate(film));
    }

    @Test
    public void updateFilmIdIsAbsentNotFoundException() {
        createFilm();
        Film film = new Film(25L, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), 116);
        assertThrows(NotFoundException.class, () -> filmController.update(film));
    }

    @Test
    public void updateFilmNameIsBlankValidationException() {
        Film film = new Film(1L, "  ", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), 116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForUpdate(film));
    }

    @Test
    public void updateFilmDescription201ValidationException() {
        Film film = new Film(1L, "Назад в будущее", incorrect201Description,
                LocalDate.of(1985,7, 3), 116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForUpdate(film));
    }

    @Test
    public void updateFilmDescription200ValidationException() {
        Film film = new Film(1L, "Назад в будущее", correct200Description,
                LocalDate.of(1985,7, 3), 116);
        assertDoesNotThrow(() -> FilmValidation.validateForUpdate(film));
    }

    @Test
    public void updateFilmReleaseDateIsBeforeMinReleaseDateValidationException() {
        Film film = new Film(1L, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1895,12, 27), 116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForUpdate(film));
    }

    @Test
    public void updateFilmDurationIsNegativeValidationException() {
        Film film = new Film(1L, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), -116);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForUpdate(film));
    }

    @Test
    public void updateFilmDurationIsZeroValidationException() {
        Film film = new Film(1L, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), 0);
        assertThrows(ValidationException.class, () -> FilmValidation.validateForUpdate(film));
    }



    //вспомогательные методы
    public void createFilm() {
        testFilm = new Film(1L, "Назад в будущее", "Самый крутой фильм",
                LocalDate.of(1985,7, 3), 116);
        filmController.create(testFilm);
    }

}
