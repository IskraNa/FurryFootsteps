package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Request;
import org.example.furryfootstepsapi.service.AvailabilityService;
import org.example.furryfootstepsapi.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = {"*"})
public class RequestController {

    private final RequestService requestService;
    private final AvailabilityService availabilityService;

    public RequestController(RequestService requestService, AvailabilityService availabilityService) {
        this.requestService = requestService;
        this.availabilityService = availabilityService;
    }

    @GetMapping
    public ResponseEntity<List<Request>> getRequests() {
        return ResponseEntity.ok().body(this.requestService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Request> getRequestById(@PathVariable Long id) {
        return this.requestService.findById(id).map(request -> ResponseEntity.ok().body(request))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("{userId}")
    public ResponseEntity<List<Request>> getRequestsByUser(@PathVariable Long userId){
        return ResponseEntity.ok().body(this.requestService.findAllByUser(userId));
    }

    @GetMapping("/apply{userId}/{availability}")
    public ResponseEntity<Request> generateRequest(@PathVariable Long userId, @PathVariable Availability availability){
        this.requestService.create(userId, availability);
        Optional<Request> request = this.requestService.findById(userId);
        return ResponseEntity.ok().body(request.get());
    }

    @PostMapping("/accept/{requestId}")
    public ResponseEntity<String> acceptRequest(@PathVariable Long requestId) {
        this.requestService.accept(requestId);
        return ResponseEntity.ok().body("");
    }

    @PostMapping("/deny/{requestId}")
    public ResponseEntity<String> denyRequest(@PathVariable Long requestId) {
        return ResponseEntity.ok("Request denied successfully");
    }

}
