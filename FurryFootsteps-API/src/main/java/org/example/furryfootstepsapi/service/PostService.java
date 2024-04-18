package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.requests.PostRequest;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<Post> findAll();
    Optional<Post> findById(Long id);
    Post create(PostRequest postRequest);
    Post update(Long id, PostRequest postRequest);
    void delete(Long id);
}
