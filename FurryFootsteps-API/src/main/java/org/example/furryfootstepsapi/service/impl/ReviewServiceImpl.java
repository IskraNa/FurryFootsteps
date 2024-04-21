package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.Review;
import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.exceptions.PostNotFound;
import org.example.furryfootstepsapi.model.exceptions.ReviewNotFound;
import org.example.furryfootstepsapi.model.exceptions.UserNotFound;
import org.example.furryfootstepsapi.model.requests.ReviewRequest;
import org.example.furryfootstepsapi.repository.PostRepository;
import org.example.furryfootstepsapi.repository.ReviewRepository;
import org.example.furryfootstepsapi.repository.UserRepository;
import org.example.furryfootstepsapi.service.ReviewService;
import org.hibernate.query.results.PositionalSelectionsNotAllowedException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, PostRepository postRepository){
        this.reviewRepository=reviewRepository;
        this.userRepository=userRepository;
        this.postRepository=postRepository;
    }

    @Override
    public List<Review> findAll() {
        return reviewRepository.findAll();
    }

    @Override
    public Optional<Review> findById(Long id) {
        return reviewRepository.findById(id);
    }

    @Override
    public Review create(ReviewRequest reviewRequest) {
        Review review = new Review();

        User user = userRepository.findById(reviewRequest.userId)
                .orElseThrow(() -> new UserNotFound(reviewRequest.userId));

        Post post = postRepository.findById(reviewRequest.postId)
                .orElseThrow(() -> new PostNotFound(reviewRequest.postId));
        review.setUser(user);
        review.setPost(post);
        review.setRating(reviewRequest.rating);
        review.setComment(reviewRequest.comment);

        return this.reviewRepository.save(review);
    }

    @Override
            public void delete(Long id) {
        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFound(id));
        this.reviewRepository.delete(review);
    }
    @Override
    public Review update(Long id, ReviewRequest reviewRequest) {
        Review review = this.reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFound(id));

        review.setRating(reviewRequest.rating);
        review.setComment(reviewRequest.comment);

        return this.reviewRepository.save(review);
    }
}
