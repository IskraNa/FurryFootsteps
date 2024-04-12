package org.example.furryfootstepsapi.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "pettype")
public class PetType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pettypeid")
    private Long id;

    @Column(name = "type")
    private String type;

    public PetType() {
    }

    public PetType(Long id, String type) {
        this.id = id;
        this.type = type;
    }
}
