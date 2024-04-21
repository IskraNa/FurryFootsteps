package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Review;
import org.example.furryfootstepsapi.model.requests.ReviewRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface ReviewService {
    List<Review> findAll();

    Optional<Review> findById(Long id);

    Review create(ReviewRequest reviewRequest);

    void delete(Long id);

    Review update(Long id, ReviewRequest reviewRequest);
}
