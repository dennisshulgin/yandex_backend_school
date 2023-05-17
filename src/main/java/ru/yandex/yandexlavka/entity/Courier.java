package ru.yandex.yandexlavka.entity;

import java.util.List;

public class Courier {
    private int id;

    private CourierType type;

    private List<WorkingHours> workingHoursList;

    private List<Region> regions;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CourierType getType() {
        return type;
    }

    public void setType(CourierType type) {
        this.type = type;
    }

    public List<WorkingHours> getWorkingHoursList() {
        return workingHoursList;
    }

    public void setWorkingHoursList(List<WorkingHours> workingHoursList) {
        this.workingHoursList = workingHoursList;
    }

    public List<Region> getRegions() {
        return regions;
    }

    public void setRegions(List<Region> regions) {
        this.regions = regions;
    }
}
