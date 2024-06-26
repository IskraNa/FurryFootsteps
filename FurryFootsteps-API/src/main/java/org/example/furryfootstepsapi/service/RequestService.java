package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    List<Request> findAll();
    Optional<Request> findById(Long id);
    void acceptRequest(Long requestId, Long availabilityId);
    Request create(Request request, Long availabilityId, Long userId );
    List<Request> getRequestsByUserId(Long userId);
    public void declineRequest(Long requestId);
}
