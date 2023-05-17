package ru.yandex.yandexlavka.entity;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private int id;
    private float weight;

    private int cost;
    private LocalDateTime completedTime;
    private Region region;
    private List<DeliveryHours> deliveryHours;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDateTime getCompletedTime() {
        return completedTime;
    }

    public void setCompletedTime(LocalDateTime completedTime) {
        this.completedTime = completedTime;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public List<DeliveryHours> getDeliveryHours() {
        return deliveryHours;
    }

    public void setDeliveryHours(List<DeliveryHours> deliveryHours) {
        this.deliveryHours = deliveryHours;
    }
}