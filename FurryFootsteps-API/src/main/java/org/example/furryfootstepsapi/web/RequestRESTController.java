package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = {"*"})
public class RequestRESTController {

    private final RequestService requestService;

    public RequestRESTController(RequestService requestService) {
        this.requestService = requestService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Request>> getRequests() {
        return ResponseEntity.ok().body(this.requestService.findAll());
    }
}