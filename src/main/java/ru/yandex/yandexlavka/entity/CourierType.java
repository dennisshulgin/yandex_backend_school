package ru.yandex.yandexlavka.entity;

public class CourierType {
    private int id;
    private String name;

    private int earningFactor;

    private int ratingFactor;

    public CourierType() {
    }

    public CourierType(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEarningFactor() {
        return earningFactor;
    }

    public void setEarningFactor(int earningFactor) {
        this.earningFactor = earningFactor;
    }

    public int getRatingFactor() {
        return ratingFactor;
    }

    public void setRatingFactor(int ratingFactor) {
        this.ratingFactor = ratingFactor;
    }
}
