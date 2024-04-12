package org.example.furryfootstepsapi.model;
import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reviewid")
    private Long id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "rating")
    private double rating;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    public Review() {
    }

    public Review(Long id, String comment, double rating, User user, Post post) {
        this.id = id;
        this.comment = comment;
        this.rating = rating;
        this.user = user;
        this.post = post;
    }
}
