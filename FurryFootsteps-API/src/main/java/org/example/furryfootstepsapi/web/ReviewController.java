package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.Review;
import org.example.furryfootstepsapi.model.requests.ReviewRequest;
import org.example.furryfootstepsapi.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/reviews")
@CrossOrigin(origins = {"*"})

public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping
    public ResponseEntity<List<Review>> getReviews() {
        return ResponseEntity.ok().body(this.reviewService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Review> getReviewById(@PathVariable Long id) {
        return this.reviewService.findById(id).map(post -> ResponseEntity.ok().body(post))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Review> createReview(@RequestBody ReviewRequest review) {
        Review newReview = this.reviewService.create(review);
        return new ResponseEntity<>(newReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable Long id) {
        this.reviewService.delete(id);
        return new ResponseEntity<>("Review deleted successfully!", HttpStatus.OK);
    }
}
