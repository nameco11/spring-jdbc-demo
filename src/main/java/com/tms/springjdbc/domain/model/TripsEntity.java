package com.tms.springjdbc.domain.model;


import com.tms.springjdbc.infrastructure.annotation.Column;
import com.tms.springjdbc.infrastructure.annotation.Id;
import com.tms.springjdbc.infrastructure.annotation.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table("trips")
@Setter
@Getter
@NoArgsConstructor
public class TripsEntity {
    @Id
    private Long tripId;
    @Column(name = "pickup_datetime")
    private java.sql.Timestamp pickupDatetime;
    @Column(name = "dropoff_datetime")
    private java.sql.Timestamp dropoffDatetime;
    @Column(name = "pickup_longitude")
    private Double pickupLongitude;
    @Column(name = "pickup_latitude")
    private Double pickupLatitude;
    @Column(name = "dropoff_longitude")
    private Double dropoffLongitude;
    @Column(name = "dropoff_latitude")
    private Double dropoffLatitude;
    @Column(name = "passenger_count")
    private Long passengerCount;
    @Column(name = "trip_distance")
    private Double tripDistance;
    @Column(name = "fare_amount")
    private Double fareAmount;
    @Column(name = "extra")
    private Double extra;
    @Column(name = "tip_amount")
    private Double tipAmount;
    @Column(name = "tolls_amount")
    private Double tollsAmount;
    @Column(name = "total_amount")
    private Double totalAmount;
    @Column(name = "payment_type")
    private String paymentType;
    @Column(name = "pickup_ntaname")
    private String pickupNtaname;
    @Column(name = "dropoff_ntaname")
    private String dropoffNtaname;
}
