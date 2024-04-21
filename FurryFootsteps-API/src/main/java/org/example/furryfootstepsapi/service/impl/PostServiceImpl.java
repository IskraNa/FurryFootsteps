package org.example.furryfootstepsapi.service.impl;

import jakarta.transaction.Transactional;
import org.example.furryfootstepsapi.model.*;
import org.example.furryfootstepsapi.model.dto.PostDto;
import org.example.furryfootstepsapi.model.exceptions.ActivityTypeNotFound;
import org.example.furryfootstepsapi.model.exceptions.PetTypeNotFound;
import org.example.furryfootstepsapi.model.exceptions.PostNotFound;
import org.example.furryfootstepsapi.model.exceptions.UserNotFound;
import org.example.furryfootstepsapi.model.requests.AvailabilityRequest;
import org.example.furryfootstepsapi.model.requests.PostRequest;
import org.example.furryfootstepsapi.repository.*;
import org.example.furryfootstepsapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final PetTypeRepository petTypeRepository;
    private final ActivityTypeRepository activityTypeRepository;
    private final UserRepository userRepository;
    private final AvailabilityRepository availabilityRepository;

    private final ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, PetTypeRepository petTypeRepository, ActivityTypeRepository activityTypeRepository, UserRepository userRepository, AvailabilityRepository availabilityRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.petTypeRepository = petTypeRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.userRepository = userRepository;
        this.availabilityRepository = availabilityRepository;
        this.modelMapper = modelMapper;
    }

    public List<PostDto> findAll() {
        List<PostDto> postDtos = this.postRepository.findAll()
                .stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toList());

        for (PostDto postDto : postDtos) {
            setAvailabilitiesToPostDto(postDto.getId(), postDto);
        }
        return postDtos;
    }

    @Override
    public Optional<PostDto> findById(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new PostNotFound(id));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        setAvailabilitiesToPostDto(id, postDto);
        return Optional.of(postDto);
    }

    @Override
    @Transactional
    public PostDto create(PostRequest postRequest) {
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
        PostDto postDto = modelMapper.map(post, PostDto.class);
        setAvailabilitiesToPostDto(postDto.getId(), postDto);
        return postDto;
    }

    @Override
    @Transactional
    public PostDto update(Long id, PostRequest postRequest) {
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

        this.postRepository.save(post);
        PostDto postDto = modelMapper.map(post, PostDto.class);
        setAvailabilitiesToPostDto(postDto.getId(), postDto);
        return postDto;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound(id));
        this.availabilityRepository.deleteByPostId(id);
        this.postRepository.delete(post);
    }

    private void setAvailabilitiesToPostDto(Long postId, PostDto postDto) {
        List<Availability> availabilities = this.availabilityRepository.findAllByPostId(postId);
        postDto.setAvailabilities(availabilities
                .stream()
                .map(availability -> modelMapper.map(availability, AvailabilityRequest.class))
                .collect(Collectors.toList())
        );
    }

}
