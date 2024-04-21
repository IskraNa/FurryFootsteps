package org.example.furryfootstepsapi.service;


import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.requests.AvailabilityRequest;

import java.util.List;
import java.util.Optional;

public interface AvailabilityService {
    List<Availability> findAll();

    Optional<Availability> findById(Long id);

    Availability create(AvailabilityRequest availabilityRequest);

    Availability update(AvailabilityRequest availabilityRequest, Long id);

    void delete(Long id);
}
