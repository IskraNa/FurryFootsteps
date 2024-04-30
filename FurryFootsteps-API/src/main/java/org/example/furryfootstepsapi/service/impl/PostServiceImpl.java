package org.example.furryfootstepsapi.service.impl;

import jakarta.transaction.Transactional;
import org.example.furryfootstepsapi.model.*;
import org.example.furryfootstepsapi.model.dto.PostDto;
import org.example.furryfootstepsapi.model.dto.PostWithReviewsDto;
import org.example.furryfootstepsapi.model.dto.ReviewDto;
import org.example.furryfootstepsapi.model.enums.PetSize;
import org.example.furryfootstepsapi.model.exceptions.*;
import org.example.furryfootstepsapi.model.requests.AvailabilityRequest;
import org.example.furryfootstepsapi.model.requests.PostRequest;
import org.example.furryfootstepsapi.repository.*;
import org.example.furryfootstepsapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private final ReviewRepository reviewRepository;

    public PostServiceImpl(PostRepository postRepository, PetTypeRepository petTypeRepository, ActivityTypeRepository activityTypeRepository, UserRepository userRepository, AvailabilityRepository availabilityRepository, ModelMapper modelMapper, ReviewRepository reviewRepository) {
        this.postRepository = postRepository;
        this.petTypeRepository = petTypeRepository;
        this.activityTypeRepository = activityTypeRepository;
        this.userRepository = userRepository;
        this.availabilityRepository = availabilityRepository;
        this.modelMapper = modelMapper;
        this.reviewRepository = reviewRepository;
    }

    @Override
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
    public Optional<PostWithReviewsDto> findById(Long id) {
        Post post = this.postRepository.findById(id).orElseThrow(() -> new PostNotFound(id));
        PostDto postDto = modelMapper.map(post, PostDto.class);
        setAvailabilitiesToPostDto(id, postDto);

        PostWithReviewsDto postWithReviewsDto = modelMapper.map(postDto, PostWithReviewsDto.class);
        List<ReviewDto> reviews = reviewRepository.findAllByPostId(postDto.getId())
                .stream()
                .map(review -> modelMapper.map(review, ReviewDto.class))
                .collect(Collectors.toList());
        postWithReviewsDto.setReviews(reviews);

        return Optional.of(postWithReviewsDto);
        //return Optional.of(postDto);
    }

    @Override
    @Transactional
    public PostDto create(PostRequest postRequest) {
        Post post = new Post();

        PetType petType = petTypeRepository.findById(postRequest.petTypeId)
                .orElseThrow(() -> new PetTypeNotFound(postRequest.petTypeId));

        ActivityType activityType = activityTypeRepository.findById(postRequest.activityTypeId)
                .orElseThrow(() -> new ActivityTypeNotFound(postRequest.activityTypeId));

        User user = userRepository.findById(postRequest.userId)
                .orElseThrow(() -> new UserNotFound(postRequest.userId));

        PetSize petSizeEnum;
        try {
            petSizeEnum = PetSize.valueOf(postRequest.petSize.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IncorrectPetSize();
        }

        post.setDescription(postRequest.description);
        post.setPetSize(petSizeEnum);
        post.setPrice(postRequest.price);
        post.setPetType(petType);
        post.setActivityType(activityType);
        post.setUser(user);

        processAvailabilities(post, postRequest.availabilities);

        this.postRepository.save(post);

        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setPetSize(petSizeEnum.toString());
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

        PetSize petSizeEnum;
        try {
            petSizeEnum = PetSize.valueOf(postRequest.petSize.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IncorrectPetSize();
        }

        post.setDescription(postRequest.description);
        post.setPetSize(petSizeEnum);
        post.setPrice(postRequest.price);
        post.setPetType(petType);
        post.setActivityType(activityType);

        processAvailabilities(post, postRequest.availabilities);

        this.postRepository.save(post);
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postDto.setPetSize(petSizeEnum.toString());
        setAvailabilitiesToPostDto(postDto.getId(), postDto);
        return postDto;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFound(id));
        this.availabilityRepository.deleteByPostId(id);
        this.reviewRepository.deleteByPostId(id);
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

    private void processAvailabilities(Post post, List<AvailabilityRequest> availabilityRequests) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        LocalDateTime currentDateTime = LocalDateTime.now();

        this.availabilityRepository.deleteByPostId(post.getId());

        List<Availability> updatedAvailabilities = new ArrayList<>();
        for (AvailabilityRequest availabilityRequest : availabilityRequests) {
            LocalDateTime localDateTimeFromParsed;
            LocalDateTime localDateTimeToParsed;
            try {
                localDateTimeFromParsed = LocalDateTime.parse(availabilityRequest.dateTimeFrom, formatter);
                localDateTimeToParsed = LocalDateTime.parse(availabilityRequest.dateTimeTo, formatter);
            } catch (DateTimeParseException e) {
                throw new IncorrectDateTimeFormat("Invalid date time format provided");
            }

            if (localDateTimeFromParsed.isAfter(localDateTimeToParsed)) {
                throw new IncorrectDateTimeFormat("Date Time From cannot be after Date Time To");
            }

            if (localDateTimeFromParsed.isBefore(currentDateTime) || localDateTimeToParsed.isBefore(currentDateTime)) {
                throw new IncorrectDateTimeFormat("Date Time parameters cannot be in the past");
            }

            Availability availability = new Availability();
            availability.setDateTimeFrom(localDateTimeFromParsed);
            availability.setDateTimeTo(localDateTimeToParsed);
            availability.setPost(post);

            updatedAvailabilities.add(availability);
        }

        this.availabilityRepository.saveAll(updatedAvailabilities);
    }

}
