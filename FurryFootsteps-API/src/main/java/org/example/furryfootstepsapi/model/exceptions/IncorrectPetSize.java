package org.example.furryfootstepsapi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class IncorrectPetSize extends RuntimeException {
    public IncorrectPetSize() {
        super("Invalid Pet Size provided");
    }
}
