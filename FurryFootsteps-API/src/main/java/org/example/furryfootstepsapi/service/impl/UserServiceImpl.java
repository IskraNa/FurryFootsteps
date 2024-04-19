package org.example.furryfootstepsapi.service.impl;

import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.exceptions.PostNotFound;
import org.example.furryfootstepsapi.model.exceptions.UserNotFound;
import org.example.furryfootstepsapi.model.requests.UserRequest;
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

    @Override
    public Optional<User> findById(Long id) {
        return Optional.of(this.userRepository.findById(id).orElseThrow(() -> new UserNotFound(id)));
    }

    @Override
    public User create(UserRequest userRequest) {
        User user = new User();

        // TODO: implement restrictions when creating a user - if name, surname and email exist, do not create

        user.setName(userRequest.name);
        user.setSurname(userRequest.surname);
        user.setEmail(userRequest.email);
        user.setPassword(userRequest.password);
        user.setPhone(userRequest.phone);
        user.setLocation(userRequest.location);
        user.setBio(userRequest.bio);
        user.setPicture(userRequest.picture);  // TODO: implement Base64 encoder for images
        user.setPetDescription(userRequest.petDescription);

        return this.userRepository.save(user);
    }

    @Override
    public User update(Long id, UserRequest userRequest) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new UserNotFound(id));

        // user.setName(userRequest.name);
        // user.setSurname(userRequest.surname);
        // user.setEmail(userRequest.email);
        user.setPassword(userRequest.password);
        user.setPhone(userRequest.phone);
        user.setLocation(userRequest.location);
        user.setBio(userRequest.bio);
        user.setPicture(userRequest.picture);  // TODO: implement Base64 encoder for images
        user.setPetDescription(userRequest.petDescription);

        return this.userRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        User user = this.userRepository.findById(id).orElseThrow(() -> new PostNotFound(id));
        this.userRepository.delete(user);
    }
}
