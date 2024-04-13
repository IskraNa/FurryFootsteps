package org.example.furryfootstepsapi.repository;

import org.example.furryfootstepsapi.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{

}
