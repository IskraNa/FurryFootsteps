package org.example.furryfootstepsapi.model;

import lombok.Data;

import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availabilityid")
    private Long id;

    @Column(name = "datetime_from")
    private LocalDateTime dateTimeFrom;

    @Column(name = "datetime_to")
    private LocalDateTime dateTimeTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Availability() {
    }

    public Availability(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo)
    {
        this.dateTimeFrom=dateTimeFrom;
        this.dateTimeTo=dateTimeTo;
    }

    public Availability(Long id, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo, Post post) {
        this.id = id;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.post = post;
    }
}
