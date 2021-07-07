package com.adtimokhin.repositories.user;

import com.adtimokhin.models.user.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author adtimokhin
 * 07.07.2021
 **/

public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findByName(String name);

    Rating findByMinRating(long minRating);

    List<Rating> findAllByMinRatingBetween(long minRating, long maxRating);
}
