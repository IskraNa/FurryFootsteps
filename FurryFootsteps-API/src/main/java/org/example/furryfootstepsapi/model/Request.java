package org.example.furryfootstepsapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

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
    @Column(name = "request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_poster_id")
    private User userPoster;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "availability_id")
    private Availability availability;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_requester_id")
    private User userRequester;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private RequestStatus status;

    public Request() {
    }

    public Request(Long id, User userPoster, Post post, Availability availability, User userRequester, RequestStatus status) {
        this.id = id;
        this.userPoster = userPoster;
        this.post = post;
        this.availability = availability;
        this.userRequester = userRequester;
        this.status = status;
    }
}
