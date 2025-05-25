package com.course.plazoleta.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column( nullable = false)
    private Long id ;
    private Long idClient;
    @ManyToOne
    @JoinColumn(name = "id_restaurant", nullable = false)
    private RestaurantEntity idRestaurant;
    private String state ;
    private Date date;
    private Long idChef ;
    private String pin ;
}
