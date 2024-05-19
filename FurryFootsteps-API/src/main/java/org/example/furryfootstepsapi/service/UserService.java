package org.example.furryfootstepsapi.service;

import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.dto.PostDto;
import org.example.furryfootstepsapi.model.dto.RequestDto;
import org.example.furryfootstepsapi.model.requests.UserRequest;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();
    Optional<User> findById(Long id);
    User create(UserRequest userRequest);
    User update(Long id, UserRequest userRequest);
    User authenticate(String email, String password);
    void delete(Long id);
    String getName(Long id);
    List<PostDto> findAllUserPosts(Long userId);
    List<RequestDto> getRequestsByUserPosterId(Long id);

    List<RequestDto> getRequestsByUserRequesterId(Long id);
}
