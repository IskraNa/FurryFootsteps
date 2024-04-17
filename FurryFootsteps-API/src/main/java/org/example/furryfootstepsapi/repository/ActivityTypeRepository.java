package org.example.furryfootstepsapi.repository;

import org.example.furryfootstepsapi.model.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityTypeRepository extends JpaRepository<ActivityType, Long> {

    List<ActivityType> findAllByTypeContains(String filter);
}
