package org.example.furryfootstepsapi.repository;

import org.example.furryfootstepsapi.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{
    List<Post> findAllByActivityType_Id(Long activityTypeId);
    List<Post> findAllByUserId(Long userId);
}
