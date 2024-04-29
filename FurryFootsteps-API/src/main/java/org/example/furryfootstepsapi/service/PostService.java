package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.dto.PostDto;
import org.example.furryfootstepsapi.model.dto.PostWithReviewsDto;
import org.example.furryfootstepsapi.model.requests.PostRequest;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostWithReviewsDto> findAll();
    Optional<PostWithReviewsDto> findById(Long id);
    PostDto create(PostRequest postRequest);
    PostDto update(Long id, PostRequest postRequest);
    void delete(Long id);
}
