package org.example.furryfootstepsapi.web;

import org.example.furryfootstepsapi.model.PetType;
import org.example.furryfootstepsapi.service.PetTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pettypes")
@CrossOrigin(origins = {"*"})
public class PetTypeRESTController {

    private final PetTypeService petTypeService;

    public PetTypeRESTController(PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<PetType>> getPetTypes(){
        return ResponseEntity.ok().body(this.petTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<PetType>> getPetType(@PathVariable Long id){
        return ResponseEntity.ok().body(this.petTypeService.findById(id));
    }
}
