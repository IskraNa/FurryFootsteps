package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.requests.PostRequest;
import org.example.furryfootstepsapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = {"*"})
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        return ResponseEntity.ok().body(this.postService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable Long id) {
        return this.postService.findById(id).map(post -> ResponseEntity.ok().body(post))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Post> createPost(@RequestBody PostRequest post) {
        Post newPost = this.postService.create(post);
        return new ResponseEntity<>(newPost, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable Long id,
                                           @RequestBody PostRequest postRequest) {
        Post updatedPost = this.postService.update(id, postRequest);
        return new ResponseEntity<>(updatedPost, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        this.postService.delete(id);
        return new ResponseEntity<>("Post deleted successfully!", HttpStatus.OK);
    }
}
