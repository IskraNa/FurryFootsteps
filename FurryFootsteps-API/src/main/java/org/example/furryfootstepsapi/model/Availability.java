package org.example.furryfootstepsapi.model;

import lombok.Data;
import net.sf.jsqlparser.expression.DateTimeLiteralExpression;

import jakarta.persistence.*;


@Data
@Entity
@Table(name = "availability")
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "availabilityid")
    private Long id;

    @Column(name = "datetime_from")
    private DateTimeLiteralExpression.DateTime dateTimeFrom;

    @Column(name = "datetime_to")
    private DateTimeLiteralExpression.DateTime dateTimeTo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public Availability() {
    }

    public Availability(Long id, DateTimeLiteralExpression.DateTime dateTimeFrom, DateTimeLiteralExpression.DateTime dateTimeTo, Post post) {
        this.id = id;
        this.dateTimeFrom = dateTimeFrom;
        this.dateTimeTo = dateTimeTo;
        this.post = post;
    }
}
