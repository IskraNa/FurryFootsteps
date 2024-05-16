package org.example.furryfootstepsapi.model.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class PostNotFound extends RuntimeException {

    public PostNotFound(Long id) {
        super(String.format("Post with id %d not found", id));
    }
}
