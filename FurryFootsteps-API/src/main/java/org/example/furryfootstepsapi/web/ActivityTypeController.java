package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.ActivityType;
import org.example.furryfootstepsapi.service.ActivityTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activity-types")
@CrossOrigin(origins = {"*"})
public class ActivityTypeController {

    private final ActivityTypeService activityTypeService;

    public ActivityTypeController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ActivityType>> getActivityTypes(){
        return ResponseEntity.ok().body(this.activityTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ActivityType>> getActivityType(@PathVariable Long id){
        return ResponseEntity.ok().body(this.activityTypeService.findById(id));
    }
}
