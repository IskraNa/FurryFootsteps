package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.exceptions.AvailabilityNotFound;
import org.example.furryfootstepsapi.model.exceptions.PostNotFound;
import org.example.furryfootstepsapi.model.requests.AvailabilityRequest;
import org.example.furryfootstepsapi.repository.AvailabilityRepository;
import org.example.furryfootstepsapi.repository.PostRepository;
import org.example.furryfootstepsapi.repository.UserRepository;
import org.example.furryfootstepsapi.service.AvailabilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    private final PostRepository postRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository, PostRepository postRepository) {
        this.availabilityRepository = availabilityRepository;
        this.postRepository = postRepository;
    }

    @Override
    public List<Availability> findAll() {
        return availabilityRepository.findAll();
    }

    @Override
    public Optional<Availability> findById(Long id) {
        return Optional.of(this.availabilityRepository.findById(id).orElseThrow(() -> new AvailabilityNotFound(id)));
    }

    @Override
    public Availability create(AvailabilityRequest availabilityRequest) {
        Availability availability = new Availability();

        Post post = postRepository.findById(availabilityRequest.postId)
                .orElseThrow(() -> new PostNotFound(availabilityRequest.postId));

        availability.setPost(post);
        availability.setDateTimeFrom(availabilityRequest.dateTimeFrom);
        availability.setDateTimeTo(availabilityRequest.dateTimeTo);
        return this.availabilityRepository.save(availability);
    }

    @Override
    public Availability update(AvailabilityRequest availabilityRequest, Long id) {
        Availability availability = availabilityRepository.findById(id)
                .orElseThrow(() -> new AvailabilityNotFound(id));

        availability.setDateTimeTo(availabilityRequest.dateTimeTo);
        availability.setDateTimeFrom(availabilityRequest.dateTimeFrom);
        return this.availabilityRepository.save(availability);
    }

    @Override
    public void delete(Long id) {
        Availability availability = this.availabilityRepository.findById(id)
                .orElseThrow(() -> new AvailabilityNotFound(id));
        this.availabilityRepository.delete(availability);
    }


}
