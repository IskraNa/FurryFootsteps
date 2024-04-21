package org.example.furryfootstepsapi.repository;

import org.example.furryfootstepsapi.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    void deleteByPostId(Long id);
}
