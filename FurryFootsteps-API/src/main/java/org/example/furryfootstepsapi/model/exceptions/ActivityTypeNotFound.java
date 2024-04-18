package org.example.furryfootstepsapi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class ActivityTypeNotFound extends RuntimeException {
    public ActivityTypeNotFound(Long id) {
        super(String.format("Activity Type with id %d not found", id));
    }
}