package org.example.furryfootstepsapi.model.exceptions;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long postId) {
        super("Post not found with ID: " + postId);
    }
}