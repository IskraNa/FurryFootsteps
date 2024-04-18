package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.PetType;
import org.example.furryfootstepsapi.repository.PetTypeRepository;
import org.example.furryfootstepsapi.service.PetTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetTypeServiceImpl implements PetTypeService {

    private final PetTypeRepository petTypeRepository;

    public PetTypeServiceImpl(PetTypeRepository petTypeRepository) {
        this.petTypeRepository = petTypeRepository;
    }

    @Override
    public List<PetType> findAll() {
        return this.petTypeRepository.findAll();
    }

    @Override
    public Optional<PetType> findById(Long id) {
        return this.petTypeRepository.findById(id);
    }
}
