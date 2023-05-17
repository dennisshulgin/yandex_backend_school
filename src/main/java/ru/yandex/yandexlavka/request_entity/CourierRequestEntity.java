package ru.yandex.yandexlavka.request_entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourierRequestEntity {

    @JsonProperty("courier_id")
    int id;
    @JsonProperty("courier_type")
    String type;

    int[] regions;

    @JsonProperty("working_hours")
    String[] workingHours;

    @JsonGetter("courier_id")
    public int getId() {
        return id;
    }

    @JsonSetter("courier_id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter("courier_type")
    public String getType() {
        return type;
    }

    @JsonSetter("courier_type")
    public void setType(String type) {
        this.type = type;
    }

    public int[] getRegions() {
        return regions;
    }

    public void setRegions(int[] regions) {
        this.regions = regions;
    }

    @JsonGetter("working_hours")
    public String[] getWorkingHours() {
        return workingHours;
    }

    @JsonSetter("working_hours")
    public void setWorkingHours(String[] workingHours) {
        this.workingHours = workingHours;
    }
}
