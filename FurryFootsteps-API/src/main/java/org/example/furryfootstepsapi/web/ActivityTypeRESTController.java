package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.ActivityType;
import org.example.furryfootstepsapi.model.PetType;
import org.example.furryfootstepsapi.service.ActivityTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activitytypes")
@CrossOrigin(origins = {"*"})
public class ActivityTypeRESTController {

    private final ActivityTypeService activityTypeService;

    public ActivityTypeRESTController(ActivityTypeService activityTypeService) {
        this.activityTypeService = activityTypeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ActivityType>> getActivityTypes(){
        return ResponseEntity.ok().body(this.activityTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ActivityType>> getActivityType(@PathVariable Long id){
        return ResponseEntity.ok().body(this.activityTypeService.findById(id));
    }
}
