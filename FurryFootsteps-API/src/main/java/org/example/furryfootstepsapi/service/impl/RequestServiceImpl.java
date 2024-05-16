package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.model.exceptions.RequestNotFound;
import org.example.furryfootstepsapi.repository.AvailabilityRepository;
import org.example.furryfootstepsapi.repository.PostRepository;
import org.example.furryfootstepsapi.repository.RequestRepository;
import org.example.furryfootstepsapi.service.RequestService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;
    private final PostRepository postRepository;
    private final AvailabilityRepository availabilityRepository;

    public RequestServiceImpl(RequestRepository requestRepository, PostRepository postRepository, AvailabilityRepository availabilityRepository) {
        this.requestRepository = requestRepository;
        this.postRepository = postRepository;
        this.availabilityRepository = availabilityRepository;
    }

    public List<Request> findAll() {
        return this.requestRepository.findAll();
    }

    @Override
    public List<Request> findAllByUser(Long userId) {
        return this.requestRepository.findAllByUserId(userId);
    }

    @Override
    public Optional<Request> findById(Long id) {
        return Optional.of(this.requestRepository.findById(id).orElseThrow(() -> new RequestNotFound(id)));
    }

    @Override
    public Optional<Request> create(Long userId, Availability availability) {
        Post post = this.postRepository.findById(availability.getPost().getId()).get();
        Request request = new Request(post, userId);
        this.requestRepository.save(request);
        return Optional.of(request);
    }

    @Override
    public void accept(Long requestId) {
        Optional<Request> optionalRequest = requestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            Request request = optionalRequest.get();
            Long postId = request.getPost().getId();
            Optional<Availability> optionalAvailability = availabilityRepository.findByPostId(postId);
            if (optionalAvailability.isPresent()) {
                Availability availability = optionalAvailability.get();
                availabilityRepository.delete(availability);
            }
        }
    }

    @Override
    public void deny(Long requestId) {

    }
}
