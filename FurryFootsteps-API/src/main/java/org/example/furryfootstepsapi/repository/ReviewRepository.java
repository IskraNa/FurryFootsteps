package org.example.furryfootstepsapi.repository;

import org.example.furryfootstepsapi.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByPostId(Long postId);
}
