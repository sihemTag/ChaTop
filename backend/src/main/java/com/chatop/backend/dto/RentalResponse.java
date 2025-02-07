package com.chatop.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;

public class RentalResponse {
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("surface")
    private Float surface;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("picture")
    private String picture;
    @JsonProperty("description")
    private String description;
    @JsonProperty("created_at")
    private LocalDate createdAt;
    @JsonProperty("updated_at")
    private LocalDate updatedAt;
    @JsonProperty("owner_id")
    private Integer ownerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getSurface() {
        return surface;
    }

    public void setSurface(Float surface) {
        this.surface = surface;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
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

    public Integer getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Integer ownerId) {
        this.ownerId = ownerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
