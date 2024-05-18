package org.example.furryfootstepsapi.repository;

import org.example.furryfootstepsapi.model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long>{
    List<Request> findByUserId(Long userId);
    List<Request> findAllByPostId(Long postId);

}
