package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.model.exceptions.RequestNotFound;
import org.example.furryfootstepsapi.repository.RequestRepository;
import org.example.furryfootstepsapi.service.RequestService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    public List<Request> findAll() {
        return this.requestRepository.findAll();
    }

    @Override
    public Optional<Request> findById(Long id) {
        return Optional.of(this.requestRepository.findById(id).orElseThrow(() -> new RequestNotFound(id)));
    }
}
