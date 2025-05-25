package com.course.plazoleta.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders_dishes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDishEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column( nullable = false)
    private Long id ;

    @ManyToOne
    @JoinColumn(name = "id_order", nullable = false)
    private OrderEntity idOrder ;

    @ManyToOne
    @JoinColumn(name = "id_dish", nullable = false)
    private DishEntity idDish;
    private int quantity;

}
