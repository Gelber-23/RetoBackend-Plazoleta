package com.course.plazoleta.infraestructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DishEntity {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column( nullable = false)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "idCategory", nullable = false)
    private CategoryEntity idCategory;
    private String description;
    private int price;
    private String urlImage;
    private int idRestaurant;
    private Boolean active;
}
