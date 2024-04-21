package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.Availability;
import org.example.furryfootstepsapi.model.Post;
import org.example.furryfootstepsapi.model.requests.AvailabilityRequest;
import org.example.furryfootstepsapi.model.requests.PostRequest;
import org.example.furryfootstepsapi.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availabilities")
@CrossOrigin(origins = {"*"})
public class AvailabilityController {
    AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService){
        this.availabilityService=availabilityService;
    }

    @GetMapping
    public ResponseEntity<List<Availability>> getAvailabilities() {
        return ResponseEntity.ok().body(this.availabilityService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Availability> getAvailabilityById(@PathVariable Long id) {
        return this.availabilityService.findById(id).map(availability -> ResponseEntity.ok().body(availability))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Availability> createAvailability(@RequestBody AvailabilityRequest availability) {
        Availability newAvailability = this.availabilityService.create(availability);
        return new ResponseEntity<>(newAvailability, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Availability> updateAvailability(@PathVariable Long id,
                                           @RequestBody AvailabilityRequest availabilityRequest) {
        Availability updatedAvailability = this.availabilityService.update(availabilityRequest, id);
        return new ResponseEntity<>(updatedAvailability, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAvailability(@PathVariable Long id) {
        this.availabilityService.delete(id);
        return new ResponseEntity<>("Availability deleted successfully!", HttpStatus.OK);
    }
}
