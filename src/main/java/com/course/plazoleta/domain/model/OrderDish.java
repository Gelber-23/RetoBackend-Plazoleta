package com.course.plazoleta.domain.model;

public class OrderDish {
    private Long id ;
    private Long idOrder ;
    private Long idDish;
    private int quantity;

    public OrderDish() {
    }

    public OrderDish(Long id, Long idOrder, Long idDish, int quantity) {
        this.id = id;
        this.idOrder = idOrder;
        this.idDish = idDish;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Long getIdDish() {
        return idDish;
    }

    public void setIdDish(Long idDish) {
        this.idDish = idDish;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
