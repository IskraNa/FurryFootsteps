package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.repository.AvailabilityRepository;
import org.example.furryfootstepsapi.service.AvailabilityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;

    public AvailabilityServiceImpl(AvailabilityRepository availabilityRepository) {
        this.availabilityRepository = availabilityRepository;
    }

    @Override
    public List<Availability> listAll() {
        return this.availabilityRepository.findAll();
    }

    @Override
    public Optional<Availability> findById(Long id) {
        return this.availabilityRepository.findById(id);
    }

    @Override
    public Optional<Availability> findByPostId(Long id) {
        return this.availabilityRepository.findByPostId(id);
    }

    @Override
    public void delete(Availability availability) {
        this.availabilityRepository.delete(availability);
    }
}
