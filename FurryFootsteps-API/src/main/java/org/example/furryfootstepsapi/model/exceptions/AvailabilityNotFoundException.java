package org.example.furryfootstepsapi.model.exceptions;

public class AvailabilityNotFoundException extends RuntimeException {

    public AvailabilityNotFoundException(Long postId) {
        super("Availability not found for post with ID: " + postId);
    }
}