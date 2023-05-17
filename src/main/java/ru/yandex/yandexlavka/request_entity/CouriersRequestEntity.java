package ru.yandex.yandexlavka.request_entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CouriersRequestEntity {
    @JsonProperty("couriers")
    CourierRequestEntity[] courierRequestEntities;

    @JsonGetter("couriers")
    public CourierRequestEntity[] getCourierRequestEntities() {
        return courierRequestEntities;
    }

    @JsonSetter("couriers")
    public void setCourierRequestEntities(CourierRequestEntity[] courierRequestEntities) {
        this.courierRequestEntities = courierRequestEntities;
    }
}
