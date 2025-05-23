package com.course.plazoleta.domain.model;

public class Restaurant {

    private Long id;
    private String name;
    private String address;
    private Long id_owner;
    private String phone;
    private String urlLogo;
    private String  nit;

    public Restaurant() {
    }

    public Restaurant(Long id, String name, String address, Long id_owner, String phone, String urlLogo, String nit) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.id_owner = id_owner;
        this.phone = phone;
        this.urlLogo = urlLogo;
        this.nit = nit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getId_owner() {
        return id_owner;
    }

    public void setId_owner(Long id_owner) {
        this.id_owner = id_owner;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrlLogo() {
        return urlLogo;
    }

    public void setUrlLogo(String urlLogo) {
        this.urlLogo = urlLogo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }
}
