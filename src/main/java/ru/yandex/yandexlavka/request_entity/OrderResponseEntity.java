package ru.yandex.yandexlavka.request_entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderResponseEntity {

    @JsonProperty("order_id")
    int orderId;

    @JsonProperty("weight")
    float weight;

    @JsonProperty("regions")
    int regions;

    @JsonProperty("delivery_hours")
    String[] deliveryHours;

    @JsonProperty("cost")
    int cost;

    @JsonProperty("completed_time")
    LocalDateTime completedTime;

    @JsonGetter("order_id")
    public int getOrderId() {
        return orderId;
    }

    @JsonSetter("order_id")
    public void setOrderId(int orderId) {
        this.orderId = orderId;
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

    @JsonGetter("delivery_hours")
    public String[] getDeliveryHours() {
        return deliveryHours;
    }

    @JsonSetter("delivery_hours")
    public void setDeliveryHours(String[] deliveryHours) {
        this.deliveryHours = deliveryHours;
    }

    @JsonGetter("cost")
    public int getCost() {
        return cost;
    }

    @JsonSetter("cost")
    public void setCost(int cost) {
        this.cost = cost;
    }

    @JsonGetter("completed_time")
    public LocalDateTime getCompletedTime() {
        return completedTime;
    }

    @JsonSetter("completed_time")
    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }
}
