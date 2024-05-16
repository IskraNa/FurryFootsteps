package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Availability;

import java.util.List;
import java.util.Optional;

public interface AvailabilityService {

    List<Availability> listAll();

    Optional<Availability> findById(Long id);

    Optional<Availability> findByPostId(Long id);


    void delete(Availability availability);
}
