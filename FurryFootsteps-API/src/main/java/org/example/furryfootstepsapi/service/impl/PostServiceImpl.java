package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.ActivityType;
import org.example.furryfootstepsapi.model.PetType;
import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.exceptions.ActivityTypeNotFound;
import org.example.furryfootstepsapi.model.exceptions.PetTypeNotFound;
import org.example.furryfootstepsapi.model.exceptions.PostNotFound;
import org.example.furryfootstepsapi.model.exceptions.UserNotFound;
import org.example.furryfootstepsapi.model.requests.PostRequest;
import org.example.furryfootstepsapi.repository.ActivityTypeRepository;
import org.example.furryfootstepsapi.repository.PetTypeRepository;
import org.example.furryfootstepsapi.repository.PostRepository;
import org.example.furryfootstepsapi.repository.UserRepository;
import org.example.furryfootstepsapi.service.PostService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PetTypeRepository petTypeRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final UserRepository userRepository;

    public PostServiceImpl(PostRepository postRepository, PetTypeRepository petTypeRepository, ActivityTypeRepository activityTypeRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.petTypeRepository = petTypeRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.userRepository = userRepository;
    }

    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.of(this.postRepository.findById(id).orElseThrow(() -> new PostNotFound(id)));
    }

    @Override
    public Post create(PostRequest postRequest) {
        Post post = new Post();

        PetType petType = petTypeRepository.findById(postRequest.petTypeId)
                .orElseThrow(() -> new PetTypeNotFound(postRequest.petTypeId));

        ActivityType activityType = activityTypeRepository.findById(postRequest.activityTypeId)
                .orElseThrow(() -> new ActivityTypeNotFound(postRequest.activityTypeId));

        User user = userRepository.findById(postRequest.userId)
                .orElseThrow(() -> new UserNotFound(postRequest.userId));

        post.setDescription(postRequest.description);
        post.setPetSize(postRequest.petSize);
        post.setPrice(postRequest.price);
        post.setPetType(petType);
        post.setActivityType(activityType);
        post.setUser(user);

        return this.postRepository.save(post);
    }


}
