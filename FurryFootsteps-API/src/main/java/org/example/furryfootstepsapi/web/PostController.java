package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.dto.PostDto;
import org.example.furryfootstepsapi.model.dto.PostWithReviewsDto;
import org.example.furryfootstepsapi.model.requests.PostRequest;
import org.example.furryfootstepsapi.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = {"*"})
public class PostController {

    private final PostService postService;
    private final static int PAGE_SIZE = 6;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<PostDto>> getPaginatedPosts(
            @RequestParam(required = false) Long activityTypeId,
            @RequestParam(defaultValue = "0") int page
    ) {
        Pageable pageable = PageRequest.of(page, PAGE_SIZE);
        return ResponseEntity.ok().body(this.postService.findAllPaginated(activityTypeId, pageable));
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getPosts() {
        return ResponseEntity.ok().body(this.postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostWithReviewsDto> getPostById(@PathVariable Long id) {
        return this.postService.findById(id).map(post -> ResponseEntity.ok().body(post))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<PostDto> createPost(@RequestBody PostRequest post) {
        PostDto newPost = this.postService.create(post);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long id,
                                              @RequestBody PostRequest postRequest) {
        PostDto updatedPost = this.postService.update(id, postRequest);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        this.postService.delete(id);
        return new ResponseEntity<>("Post deleted successfully!", HttpStatus.OK);
    }
}
