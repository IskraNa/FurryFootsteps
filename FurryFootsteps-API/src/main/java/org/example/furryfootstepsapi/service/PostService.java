package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAll();
    Optional<Post> findById(Long id);
}
