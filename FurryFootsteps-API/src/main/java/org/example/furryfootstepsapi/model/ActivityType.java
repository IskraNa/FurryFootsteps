package org.example.furryfootstepsapi.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "activitytype")
public class ActivityType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activityid")
    private Long id;

    private String type;

    public ActivityType() {
    }

    public ActivityType(Long id, String type) {
        this.id = id;
        this.type = type;
    }

    public ActivityType(String type) {
        this.type = type;
    }
}
