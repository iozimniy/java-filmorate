package ru.yandex.practicum.filmorate.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.Collection;

@RestController
@RequestMapping("/mpa")
@Slf4j
@AllArgsConstructor
public class RatingController {

    RatingService ratingService;

    @GetMapping("/{id}")
    public ResponseEntity<Rating> getRating(@PathVariable("id") Long ratingId) {
        log.info("Получен запрос на возврат информации о рейтинге с id {}", ratingId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ratingService.getRating(ratingId));
    }

    @GetMapping
    public Collection<Rating> getRatings() {
        log.info("Получен запрос на возврат информации по всем рейтингам");
        return ratingService.getAll();
    }
}
