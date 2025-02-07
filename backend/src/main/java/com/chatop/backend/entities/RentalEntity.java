package com.chatop.backend.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "rentals")
public class RentalEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "surface")
    private Float surface;
    @Column(name = "price")
    private Float price;
    @Column(name = "picture")
    private String picture;
    @Column(name = "description")
    private String description;
    @JsonProperty("created_at")
    @Column(name = "created_at")
    private LocalDate createdAt;
    @JsonProperty("updated_at")
    @Column(name = "updated_at")
    private LocalDate updatedAt;
    @JsonProperty("owner_id")
    @ManyToOne
    @JoinColumn(name = "owner_id" , nullable = false)
    private UserEntity owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSurface() {
        return surface;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserEntity getOwner() {
        return owner;
    }

    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    public void setSurface(Float surface) {
        this.surface = surface;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
