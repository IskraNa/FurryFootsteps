package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.Post;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import org.example.furryfootstepsapi.service.impl.PostServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin(origins = {"*"})
public class PostRESTController {

    private final PostServiceImpl activityTypeService;

    public PostRESTController(PostServiceImpl activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

     @GetMapping("/all")
     public ResponseEntity<List<Post>> getActivityTypes() {
         return ResponseEntity.ok().body(this.activityTypeService.findAll());
     }
}
