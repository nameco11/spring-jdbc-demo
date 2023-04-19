package com.tms.springjdbc.domain;

import com.tms.springjdbc.advantage.annotation.Column;
import com.tms.springjdbc.advantage.annotation.Id;
import com.tms.springjdbc.advantage.annotation.Table;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Table(name = "cars")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Car {
    @Id
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "brand")
    private String brand;

    @Column(name = "groupId")
    private int groupId;

    @Column(name = "color")
    private String color;

    @Column(name = "type")
    private String type;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private BigDecimal price;

    // Constructors, getters, setters
}
