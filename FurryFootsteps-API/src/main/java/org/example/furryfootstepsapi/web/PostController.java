package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.example.furryfootstepsapi.service.impl.PostServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = {"*"})
public class PostController {

    private final PostServiceImpl postService;

    public PostController(PostServiceImpl postService) {
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
}
