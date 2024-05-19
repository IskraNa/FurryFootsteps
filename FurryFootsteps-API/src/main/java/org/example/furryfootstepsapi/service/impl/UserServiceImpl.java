package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Post;

import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.dto.PostDto;
import org.example.furryfootstepsapi.model.dto.RequestDto;
import org.example.furryfootstepsapi.model.exceptions.*;
import org.example.furryfootstepsapi.model.requests.UserRequest;
import org.example.furryfootstepsapi.repository.AvailabilityRepository;
import org.example.furryfootstepsapi.repository.PostRepository;
import org.example.furryfootstepsapi.repository.RequestRepository;
import org.example.furryfootstepsapi.repository.UserRepository;
import org.example.furryfootstepsapi.service.UserService;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;
    private final RequestRepository requestRepository;
    private final AvailabilityRepository availabilityRepository;


    public UserServiceImpl(UserRepository userRepository, PostRepository postRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RequestRepository requestRepository, AvailabilityRepository availabilityRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.requestRepository = requestRepository;
        this.availabilityRepository = availabilityRepository;
    }


    @Override

    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.of(this.userRepository.findById(id).orElseThrow(() -> new UserNotFound(id)));
    }

    @Override
    public User create(UserRequest userRequest) {
        User user = new User();

        emailChecks(userRequest.email);
        phoneCheck(userRequest.phone);

        user.setName(userRequest.name);
        user.setSurname(userRequest.surname);
        user.setEmail(userRequest.email);

        String encodedPassword = passwordEncoder.encode(userRequest.password);
        user.setPassword(encodedPassword);

        user.setPhone(userRequest.phone);
        user.setLocation(userRequest.location);
        user.setBio(userRequest.bio);
        user.setPicture(userRequest.picture);  // TODO: implement Base64 encoder for images
        user.setPetDescription(userRequest.petDescription);

        return this.userRepository.save(user);
    }

    @Override
    public User update(Long id, UserRequest userRequest) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));

        // user.setName(userRequest.name);
        // user.setSurname(userRequest.surname);
        // user.setEmail(userRequest.email);
        phoneCheck(userRequest.phone);

        user.setPassword(userRequest.password);
        user.setPhone(userRequest.phone);
        user.setLocation(userRequest.location);
        user.setBio(userRequest.bio);
        user.setPicture(userRequest.picture);  // TODO: implement Base64 encoder for images
        user.setPetDescription(userRequest.petDescription);

        return this.userRepository.save(user);
    }

    @Override
    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User with email " + email + " not found"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return user;
    }


    @Override
    public void delete(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new PostNotFound(id));
        this.userRepository.delete(user);
    }

    @Override
    public String getName(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));
        return user.getName();
    }

    @Override
    public List<PostDto> findAllUserPosts(Long userId) {
        this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        List<Post> postsByUser = this.postRepository.findAllByUserId(userId);
        List<PostDto> postDtos = postsByUser.stream().map(post -> modelMapper.map(post, PostDto.class)).toList();

        for (PostDto postDto : postDtos) {
            postDto.setUser(getPostUser(postDto.getUserId()));
        }

        return postDtos;
    }

    @Override
    public List<RequestDto> getRequestsByUserPosterId(Long id) {
//        List<Request> requests = new ArrayList<>();
//        List<Post> posts = this.postRepository.findAllByUserId(id);
//
//        for ( Post p : posts){
//            Long temp_p = p.getId();
//            List<Request> rq =  requestRepository.findAllByPostId(temp_p);
//            requests.addAll(rq);
//        }
//        return requests;
        List<Request> request = this.requestRepository.findByUserPosterIdAndStatus(id, Request.RequestStatus.PENDING);
        return getRequestDtos(request);
    }

    @Override
    public List<RequestDto> getRequestsByUserRequesterId(Long id) {
        List<Request> request = this.requestRepository.findAllByUserRequesterId(id);
        return getRequestDtos(request);
    }

    private List<RequestDto> getRequestDtos(List<Request> request) {
        List<RequestDto> requestDtos = request.stream()
                .map(req -> modelMapper.map(req, RequestDto.class)).toList();
        for (RequestDto requestDto: requestDtos) {
            User userRequester = this.userRepository.findById(requestDto.getUserRequesterId())
                    .orElseThrow(() -> new UserNotFound(requestDto.getUserRequesterId()));
            User userPoster = this.userRepository.findById(requestDto.getUserPosterId())
                    .orElseThrow(() -> new UserNotFound(requestDto.getUserPosterId()));
            Post post = this.postRepository.findById(requestDto.getPostId())
                    .orElseThrow(() -> new PostNotFound(requestDto.getPostId()));
            Availability availability = this.availabilityRepository.findById(requestDto.getAvailabilityId())
                            .orElseThrow(() -> new AvailabilityNotFoundException(requestDto.getAvailabilityId()));

            requestDto.setUserRequesterName(userRequester.getName() + " " + userRequester.getSurname());
            requestDto.setUserPosterName(userPoster.getName() + " " + userPoster.getSurname());
            requestDto.setPostName(post.getActivityType().getType());
            requestDto.setAvailabilityTime(availability.getDateTimeFrom().toString() + " - " + availability.getDateTimeTo().toString());
        }
        return requestDtos;
    }

    private String getPostUser(Long userId) {
        User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));
        return user.getName() + " " + user.getSurname();
    }

    public void emailChecks(String email) {
        if (this.userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExists();
        }
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        if (!email.matches(emailRegex)) {
            throw new IncorrectEmailFormat();
        }
    }

    public void phoneCheck(String phoneNumber) {
        String phoneRegex = "^\\d{9}$"; // 9 cifri samo spoeni 000000000
        if (phoneNumber != null && !phoneNumber.matches(phoneRegex)) { //staviv phoneNumber!=null u slucaj ako ne e zadolzitelno telf broj za profil
            throw new InvalidPhoneNumberFormatException();
        }
    }
}
