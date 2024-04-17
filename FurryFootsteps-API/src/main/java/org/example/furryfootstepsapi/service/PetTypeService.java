package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.PetType;

import java.util.List;
import java.util.Optional;

public interface PetTypeService {
    List<PetType> findAll();
    Optional<PetType> findById(Long id);
}
