package org.example.furryfootstepsapi.model;

import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "request")
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "requestid")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Request() {
    }

    public Request(Long id, Post post, User user) {
        this.id = id;
        this.post = post;
        this.user = user;
    }
}
