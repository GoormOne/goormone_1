package com.profect.delivery.global.entity;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Time;
import java.util.UUID;

@Entity
@Table(name = "p_stores_business_hr")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreBusinessHour {

    @Id
    @Column(name = "business_hr_id", nullable = false)
    private UUID businessHrId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @Column(name = "day_of_week", length = 3, nullable = false)
    private String dayOfWeek; // 예: MON, TUE, WED

    @Column(name = "open_time", nullable = false)
    private Time openTime;

    @Column(name = "close_time", nullable = false)
    private Time closeTime;
}