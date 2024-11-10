package ru.yandex.practicum.filmorate.validations;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.NotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.storage.RatingStorage;

@Component
@AllArgsConstructor
@Slf4j
public class RatingValidation {
    RatingStorage ratingStorage;
    public void validateRatingId(Long id) {
        if (!ratingStorage.isContainsId(id)) {
            log.warn("Не существует рейтинга с id {}", id);
            throw new NotFoundException("Не существует рейтинга с id " + id);
        }
    }
}
