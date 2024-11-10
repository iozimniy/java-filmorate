package ru.yandex.practicum.filmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.RatingStorage;
import ru.yandex.practicum.filmorate.validations.RatingValidation;

import java.util.Collection;

@Service
@Slf4j
@AllArgsConstructor
public class RatingService {
    RatingStorage ratingStorage;
    RatingValidation ratingValidation;

    public Rating getRating(Long id) {
        ratingValidation.validateRatingId(id);
        return ratingStorage.getById(id).get();
    }

    public Collection<Rating> getAll() {
        return ratingStorage.getAll();
    }
}
