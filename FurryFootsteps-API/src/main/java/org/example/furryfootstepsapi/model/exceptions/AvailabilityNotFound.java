package org.example.furryfootstepsapi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class AvailabilityNotFound extends RuntimeException{
    public AvailabilityNotFound(Long id) {
        super(String.format("Availability with id %d not found", id));
    }

}
