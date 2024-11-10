package ru.yandex.practicum.filmorate.IntegrationTests;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.dal.RatingRepository;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase
@Import(RatingRepository.class)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@ComponentScan(basePackages = "ru.yandex.practicum.filmorate")
public class RatingRepositoryTests {

    @Autowired
    RatingRepository ratingRepository;
    private static Long FIRST_RATING_ID_IN_DATA = 1L;
    private static Integer COUNT_RATINGS_IN_DATA = 5;

    @Test
    public void getAllRatingTest() {
        Collection<Rating> ratings = ratingRepository.getAll();

        assertSame(COUNT_RATINGS_IN_DATA, ratings.size());
    }

    @Test
    public void getRatingByIdTest() {
        Optional<Rating> rating = ratingRepository.getById(FIRST_RATING_ID_IN_DATA);

        assertTrue(rating.isPresent());
    }

    @Test
    public void isContainsRatingIdTest() {
        assertTrue(ratingRepository.isContainsId(FIRST_RATING_ID_IN_DATA));
    }
}
