package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.ActivityType;
import org.example.furryfootstepsapi.repository.ActivityTypeRepository;
import org.example.furryfootstepsapi.service.ActivityTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityTypeServiceImpl implements ActivityTypeService {

    private final ActivityTypeRepository activityTypeRepository;

    public ActivityTypeServiceImpl(ActivityTypeRepository activityTypeRepository) {
        this.activityTypeRepository = activityTypeRepository;
    }

    @Override
    public List<ActivityType> findAll() {
        return this.activityTypeRepository.findAll();
    }

    @Override
    public Optional<ActivityType> findById(Long id) {
        return this.findById(id);
    }
}
