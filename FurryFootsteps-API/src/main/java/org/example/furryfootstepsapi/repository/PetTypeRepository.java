package org.example.furryfootstepsapi.repository;

import org.example.furryfootstepsapi.model.PetType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetTypeRepository extends JpaRepository<PetType, Long> {
}
