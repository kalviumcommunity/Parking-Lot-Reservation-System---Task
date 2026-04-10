package com.parking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "floors")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Floor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private int floorNumber;

    @Column(nullable = false)
    private String name;
}
