package org.example.furryfootstepsapi.model;

import lombok.Data;

import jakarta.persistence.*;


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
    private String petSize;

    @Column(name = "price")
    private double price;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pet_type_id")
    private PetType petType;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_type_id")
    private ActivityType activityType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Post() {
    }

    public Post(Long id, String description, String petSize, double price, PetType petType, ActivityType activityType, User user) {
        this.id = id;
        this.description = description;
        this.petSize = petSize;
        this.price = price;
        this.petType = petType;
        this.activityType = activityType;
        this.user = user;
    }
}
