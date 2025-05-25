package com.course.plazoleta.domain.model;

import java.util.Date;
import java.util.List;

public class Order {

    private Long id ;
    private Long idClient;
    private Long idRestaurant;
    private String state ;
    private Date date;
    private Long idChef ;
    private List<OrderDish> dishes ;
    private String pin ;

    public Order() {
    }

    public Order(Long id, Long idClient, Long idRestaurant, String state, Date date, Long idChef, List<OrderDish> dishes, String pin) {
        this.id = id;
        this.idClient = idClient;
        this.idRestaurant = idRestaurant;
        this.state = state;
        this.date = date;
        this.idChef = idChef;
        this.dishes = dishes;
        this.pin = pin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Long idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getIdChef() {
        return idChef;
    }

    public void setIdChef(Long idChef) {
        this.idChef = idChef;
    }

    public List<OrderDish> getDishes() {
        return dishes;
    }

    public void setDishes(List<OrderDish> dishes) {
        this.dishes = dishes;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
