package com.sideproject.cafe_cok.cafe.domain;

import com.sideproject.cafe_cok.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@Table(name = "operation_hours")
public class OperationHour extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "date", nullable = false)
    private DayOfWeek date;

    @Column(name = "opening_time")
    private LocalTime openingTime;

    @Column(name = "closing_time")
    private LocalTime closingTime;

    @Column(name = "is_closed")
    private boolean isClosed;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "cafes_id")
    private Cafe cafe;

    protected OperationHour() {
    }

    public OperationHour(final DayOfWeek date, final LocalTime openingTime, final LocalTime closingTime,
                         final boolean isClosed, final Cafe cafe) {
        this.date = date;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.isClosed = isClosed;
        this.cafe = cafe;
    }
}
