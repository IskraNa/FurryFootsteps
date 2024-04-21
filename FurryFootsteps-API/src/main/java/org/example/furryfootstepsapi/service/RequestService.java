package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Request;

import java.util.List;
import java.util.Optional;

public interface RequestService {

    List<Request> findAll();
    Optional<Request> findById(Long id);
}
