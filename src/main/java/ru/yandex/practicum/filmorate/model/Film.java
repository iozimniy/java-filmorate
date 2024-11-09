package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {
    //final Set<Long> likes = new HashSet<>();
    Long id;
    @NotBlank String name;
    String description;
    Long ratingId;
    LocalDate releaseDate;
    Integer duration;
    Collection<Long> genres = new HashSet<>(){
    };
}
