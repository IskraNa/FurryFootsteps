package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.repository.UserRepository;
import org.example.furryfootstepsapi.service.UserService;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll() {
        return this.userRepository.findAll();
    }


}
