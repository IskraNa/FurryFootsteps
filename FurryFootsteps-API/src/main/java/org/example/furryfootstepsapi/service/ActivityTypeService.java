package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.ActivityType;

import java.util.List;
import java.util.Optional;

public interface ActivityTypeService {

    List<ActivityType> findAll();

    Optional<ActivityType> findById(Long id);
}
