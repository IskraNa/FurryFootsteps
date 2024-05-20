package org.example.furryfootstepsapi.service.impl;

import jakarta.transaction.Transactional;
import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.exceptions.*;
import org.example.furryfootstepsapi.repository.AvailabilityRepository;
import org.example.furryfootstepsapi.repository.PostRepository;
import org.example.furryfootstepsapi.repository.RequestRepository;
import org.example.furryfootstepsapi.repository.UserRepository;
import org.example.furryfootstepsapi.service.RequestService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AvailabilityRepository availabilityRepository;

    public RequestServiceImpl(RequestRepository requestRepository, UserRepository userRepository,
                              PostRepository postRepository, AvailabilityRepository availabilityRepository) {
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<Request> findAll() {
        return requestRepository.findAll();
    }

    @Override
    public Optional<Request> findById(Long id) {
        return requestRepository.findById(id);
    }

    @Override
    public Request create(Request request, Long availabilityId, Long userId ) {
        // Fetch post and userRequester based on their IDs

        Availability availability = this.availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new AvailabilityNotFoundException(availabilityId));
        Post post = postRepository.findById(availability.getPost().getId())
                .orElseThrow(() -> new PostNotFoundException(availability.getPost().getId()));
        User userRequester = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFound(userId));

        //request.setPost(post);
        request.setAvailability(availability);
        request.setPost(post);
        request.setUserRequester(userRequester);
        request.setUserPoster(post.getUser());
        request.setStatus(Request.RequestStatus.PENDING);

        // Save the request
        return requestRepository.save(request);
    }

    @Override
    public void acceptRequest(Long id, Long availabilityId) {
        Request request = requestRepository.findById(id)
                .orElseThrow(() -> new RequestNotFound(id));
        request.setStatus(Request.RequestStatus.ACCEPTED);

//        Availability availability = this.availabilityRepository.findById(availabilityId)
//                        .orElseThrow(() -> new AvailabilityNotFoundException(availabilityId));

//        this.availabilityRepository.deleteById(availabilityId);
        //this.availabilityRepository.delete(availability);
        requestRepository.save(request);
    }

    @Override
    public void declineRequest(Long requestId) {
        Request request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RequestNotFound(requestId));
        request.setStatus(Request.RequestStatus.DECLINED);
        requestRepository.save(request);
    }

    @Override
    public List<Request> getRequestsByUserId(Long userId) {
        return requestRepository.findByUserPosterId(userId);
    }

}
