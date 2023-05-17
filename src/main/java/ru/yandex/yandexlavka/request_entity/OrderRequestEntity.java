package ru.yandex.yandexlavka.request_entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequestEntity {

    @JsonProperty("order_id")
    int id;

    @JsonProperty("weight")
    float weight;

    @JsonProperty("regions")
    int regions;

    @JsonProperty("cost")
    int cost;

    @JsonProperty("delivery_hours")
    String[] deliveryHours;

    @JsonGetter("order_id")
    public int getId() {
        return id;
    }

    @JsonSetter("order_id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("weight")
    public float getWeight() {
        return weight;
    }

    @JsonSetter("weight")
    public void setWeight(float weight) {
        this.weight = weight;
    }

    @JsonGetter("regions")
    public int getRegions() {
        return regions;
    }

    @JsonSetter("regions")
    public void setRegions(int regions) {
        this.regions = regions;
    }

    @JsonGetter("cost")
    public int getCost() {
        return cost;
    }

    @JsonSetter("cost")
    public void setCost(int cost) {
        this.cost = cost;
    }

    @JsonGetter("delivery_hours")
    public String[] getDeliveryHours() {
        return deliveryHours;
    }

    @JsonSetter("delivery_hours")
    public void setDeliveryHours(String[] deliveryHours) {
        this.deliveryHours = deliveryHours;
    }
}