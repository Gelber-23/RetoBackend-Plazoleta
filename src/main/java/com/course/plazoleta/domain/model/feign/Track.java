package com.course.plazoleta.domain.model.feign;


public class Track {

    private Long idOrder;

    private Long idClient;

    private Long idEmployee;

    private String previousState;

    private String newState;

    public Track() {
    }

    public Track(Long idOrder, Long idClient, Long idEmployee, String previousState, String newState) {
        this.idOrder = idOrder;
        this.idClient = idClient;
        this.idEmployee = idEmployee;
        this.previousState = previousState;
        this.newState = newState;
    }

    public Long getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Long idOrder) {
        this.idOrder = idOrder;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public Long getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(Long idEmployee) {
        this.idEmployee = idEmployee;
    }

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    public String getNewState() {
        return newState;
    }

    public void setNewState(String newState) {
        this.newState = newState;
    }
}
