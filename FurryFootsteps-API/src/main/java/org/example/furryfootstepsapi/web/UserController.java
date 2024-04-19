package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.User;
import org.example.furryfootstepsapi.model.requests.UserRequest;
import org.example.furryfootstepsapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"*"})
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok().body(this.userService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return this.userService.findById(id).map(user -> ResponseEntity.ok().body(user))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
        User newUser = this.userService.create(userRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id,
                                           @RequestBody UserRequest userRequest) {
        User updatedUser = this.userService.update(id, userRequest);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        this.userService.delete(id);
        return new ResponseEntity<>("User deleted successfully!", HttpStatus.OK);
    }
}
