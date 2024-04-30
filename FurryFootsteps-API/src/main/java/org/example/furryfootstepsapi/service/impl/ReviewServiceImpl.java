package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.Review;
import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.dto.ReviewDto;
import org.example.furryfootstepsapi.model.exceptions.PostNotFound;
import org.example.furryfootstepsapi.model.exceptions.ReviewNotFound;
import org.example.furryfootstepsapi.model.exceptions.UserNotFound;
import org.example.furryfootstepsapi.model.requests.ReviewRequest;
import org.example.furryfootstepsapi.repository.PostRepository;
import org.example.furryfootstepsapi.repository.ReviewRepository;
import org.example.furryfootstepsapi.repository.UserRepository;
import org.example.furryfootstepsapi.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final ModelMapper modelMapper;
    public ReviewServiceImpl(ReviewRepository reviewRepository, UserRepository userRepository, PostRepository postRepository, ModelMapper modelMapper){
        this.reviewRepository=reviewRepository;
        this.userRepository=userRepository;
        this.postRepository=postRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public List<ReviewDto> findAll() {


        return this.reviewRepository.findAll()
                .stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ReviewDto> findById(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFound(id));
        ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);

        return Optional.ofNullable(reviewDto);
        }

    @Override
    public ReviewDto create(ReviewRequest reviewRequest) {
        Review review = new Review();

        User user = userRepository.findById(reviewRequest.userId)
                .orElseThrow(() -> new UserNotFound(reviewRequest.userId));

        Post post = postRepository.findById(reviewRequest.postId)
                .orElseThrow(() -> new PostNotFound(reviewRequest.postId));
        review.setUser(user);
        review.setPost(post);
        review.setRating(reviewRequest.rating); //rating pretpostavuvam ke se dade na izbor kako dzvezdicki (ili shepi ha ha) pa nema da pravam checks za vrednosta
        review.setComment(reviewRequest.comment);

        this.reviewRepository.save(review);

        return modelMapper.map(review, ReviewDto.class);
    }

    @Override
            public void delete(Long id) {
        Review review = this.reviewRepository.findById(id)
                .orElseThrow(() -> new ReviewNotFound(id));
        this.reviewRepository.delete(review);
    }
    @Override
    public ReviewDto update(Long id, ReviewRequest reviewRequest) {
        Review review = this.reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFound(id));

        review.setRating(reviewRequest.rating);
        review.setComment(reviewRequest.comment);

        this.reviewRepository.save(review);

        return modelMapper.map(review, ReviewDto.class);
    }
}
