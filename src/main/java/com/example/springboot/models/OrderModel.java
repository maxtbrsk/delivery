package com.example.springboot.models;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.UUID;
import java.util.List;

@Entity
@Table(name = "TB_ORDERS")
public class OrderModel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private UUID idOrder;
    private UUID userId;
    @ElementCollection
    private List<UUID> itemIds;
    @ElementCollection
    private List<Integer> quantities;
    private String street;
    private String number;
    private String district;
    private String city;

    public UUID getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(UUID idOrder) {
        this.idOrder = idOrder;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<UUID> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<UUID> itemIds) {
        this.itemIds = itemIds;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<Integer> quantities) {
        this.quantities = quantities;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}