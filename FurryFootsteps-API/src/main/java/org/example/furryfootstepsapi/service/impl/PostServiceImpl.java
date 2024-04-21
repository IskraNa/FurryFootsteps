package org.example.furryfootstepsapi.service.impl;

import jakarta.transaction.Transactional;
import org.example.furryfootstepsapi.model.*;
import org.example.furryfootstepsapi.model.exceptions.ActivityTypeNotFound;
import org.example.furryfootstepsapi.model.exceptions.PetTypeNotFound;
import org.example.furryfootstepsapi.model.exceptions.PostNotFound;
import org.example.furryfootstepsapi.model.exceptions.UserNotFound;
import org.example.furryfootstepsapi.model.requests.AvailabilityRequest;
import org.example.furryfootstepsapi.model.requests.PostRequest;
import org.example.furryfootstepsapi.repository.*;
import org.example.furryfootstepsapi.service.PostService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PetTypeRepository petTypeRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final UserRepository userRepository;
    private final AvailabilityRepository availabilityRepository;

    public PostServiceImpl(PostRepository postRepository, PetTypeRepository petTypeRepository, ActivityTypeRepository activityTypeRepository, UserRepository userRepository, AvailabilityRepository availabilityRepository) {
        this.postRepository = postRepository;
        this.petTypeRepository = petTypeRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.userRepository = userRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public List<Post> findAll() {
        return this.postRepository.findAll();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return Optional.of(this.postRepository.findById(id).orElseThrow(() -> new PostNotFound(id)));
    }

    @Override
    @Transactional
    public Post create(PostRequest postRequest) {
        Post post = new Post();

        PetType petType = petTypeRepository.findById(postRequest.petTypeId)
                .orElseThrow(() -> new PetTypeNotFound(postRequest.petTypeId));

        ActivityType activityType = activityTypeRepository.findById(postRequest.activityTypeId)
                .orElseThrow(() -> new ActivityTypeNotFound(postRequest.activityTypeId));

        // TODO: get the user id from user that is logged in
        User user = userRepository.findById(postRequest.userId)
                .orElseThrow(() -> new UserNotFound(postRequest.userId));

        post.setDescription(postRequest.description);
        post.setPetSize(postRequest.petSize);
        post.setPrice(postRequest.price);
        post.setPetType(petType);
        post.setActivityType(activityType);
        post.setUser(user);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        if (!postRequest.availabilities.isEmpty()) {
            for (AvailabilityRequest availabilityRequest : postRequest.availabilities) {
                Availability availability = new Availability();
                LocalDateTime localDateTimeFromParsed = LocalDateTime
                        .parse(availabilityRequest.dateTimeFrom, formatter);
                LocalDateTime localDateTimeToParsed = LocalDateTime
                        .parse(availabilityRequest.dateTimeTo, formatter);
                availability.setDateTimeFrom(localDateTimeFromParsed);
                availability.setDateTimeTo(localDateTimeToParsed);
                availability.setPost(post);

                this.availabilityRepository.save(availability);
            }
        }

        this.postRepository.save(post);
        return post;
    }

    @Override
    @Transactional
    public Post update(Long id, PostRequest postRequest) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new PostNotFound(id));
        PetType petType = petTypeRepository.findById(postRequest.petTypeId)
                .orElseThrow(() -> new PetTypeNotFound(postRequest.petTypeId));

        ActivityType activityType = activityTypeRepository.findById(postRequest.activityTypeId)
                .orElseThrow(() -> new ActivityTypeNotFound(postRequest.activityTypeId));
        post.setDescription(postRequest.description);
        post.setPetSize(postRequest.petSize);
        post.setPrice(postRequest.price);
        post.setPetType(petType);
        post.setActivityType(activityType);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        this.availabilityRepository.deleteByPostId(id);

        List<Availability> updatedAvailabilities = new ArrayList<>();
        if (!postRequest.availabilities.isEmpty()) {
            for (AvailabilityRequest availabilityRequest : postRequest.availabilities) {
                LocalDateTime localDateTimeFromParsed = LocalDateTime.parse(availabilityRequest.dateTimeFrom, formatter);
                LocalDateTime localDateTimeToParsed = LocalDateTime.parse(availabilityRequest.dateTimeTo, formatter);

                Availability availability = new Availability();
                availability.setDateTimeFrom(localDateTimeFromParsed);
                availability.setDateTimeTo(localDateTimeToParsed);
                availability.setPost(post);

                updatedAvailabilities.add(availability);
            }

            this.availabilityRepository.saveAll(updatedAvailabilities);
        }

        return this.postRepository.save(post);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound(id));
        this.availabilityRepository.deleteByPostId(id);
        this.postRepository.delete(post);
    }


}
