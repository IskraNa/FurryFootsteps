package org.example.furryfootstepsapi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PetTypeNotFound extends RuntimeException {
    public PetTypeNotFound(Long id) {
        super(String.format("Pet Type with id %d not found", id));
    }
}