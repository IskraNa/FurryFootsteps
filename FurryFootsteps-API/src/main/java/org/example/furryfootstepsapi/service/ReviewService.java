package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Review;
import org.example.furryfootstepsapi.model.dto.ReviewDto;
import org.example.furryfootstepsapi.model.requests.ReviewRequest;

import java.util.List;
import java.util.Optional;


public interface ReviewService {
    List<ReviewDto> findAll();

    Optional<ReviewDto> findById(Long id);

    ReviewDto create(ReviewRequest reviewRequest);

    void delete(Long id);

    ReviewDto update(Long id, ReviewRequest reviewRequest);
}
