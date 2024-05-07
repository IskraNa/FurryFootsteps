package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.dto.PostDto;
import org.example.furryfootstepsapi.model.dto.PostWithReviewsDto;
import org.example.furryfootstepsapi.model.requests.PostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PostService {

    List<PostDto> findAll();

    Optional<PostWithReviewsDto> findById(Long id);

    PostDto create(PostRequest postRequest);

    PostDto update(Long id, PostRequest postRequest);

    void delete(Long id);

    Page<PostDto> findAllPaginated(Long activityTypeId, Pageable pageable);
}
