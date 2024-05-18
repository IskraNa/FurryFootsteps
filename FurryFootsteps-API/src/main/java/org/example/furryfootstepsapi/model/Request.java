package org.example.furryfootstepsapi.model;

import lombok.Data;

import jakarta.persistence.*;
import org.springframework.security.core.parameters.P;

@Data
@Entity
@Table(name = "request")
public class Request {

    public enum RequestStatus {
        PENDING,
        ACCEPTED,
        DECLINED
    }

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    public Request() {
    }

    public Request(Long id, Post post, User user, RequestStatus status) {
        this.id = id;
        this.post = post;
        this.user = user;
        this.status = status;
    }
}
