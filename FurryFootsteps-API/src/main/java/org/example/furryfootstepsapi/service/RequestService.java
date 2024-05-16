package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.model.User;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    List<Request> findAll();

    List<Request> findAllByUser(Long userId);

    Optional<Request> findById(Long id);

    Optional<Request> create(Long userId, Availability availability);

    void accept(Long requestId);

    void deny(Long requestId);


}
