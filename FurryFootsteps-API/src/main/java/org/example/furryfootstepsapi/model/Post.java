package org.example.furryfootstepsapi.model;

import lombok.Data;

import jakarta.persistence.*;
import org.example.furryfootstepsapi.model.enums.PetSize;


@Data
@Entity
@Table(name = "post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "postid")
    private Long id;

    @Column(name = "description")
    private String description;


    @Column(name = "pet_size")
    @Enumerated(EnumType.STRING)
    private PetSize petSize;

    @Column(name = "price")
    private double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Post() {
    }

    public Post(Long id, String description, PetSize petSize, double price, PetType petType, ActivityType activityType, User user) {
        this.id = id;
        this.description = description;
        this.petSize = petSize;
        this.price = price;
        this.petType = petType;
        this.activityType = activityType;
        this.user = user;
    }
}
