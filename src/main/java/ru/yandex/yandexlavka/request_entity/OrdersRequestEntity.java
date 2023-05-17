package ru.yandex.yandexlavka.request_entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OrdersRequestEntity {

    @JsonProperty("orders")
    OrderRequestEntity[] orderJsonEntities;

    @JsonGetter("orders")
    public OrderRequestEntity[] getOrderJsonEntities() {
        return orderJsonEntities;
    }

    @JsonSetter("orders")
    public void setOrderJsonEntities(OrderRequestEntity[] orderJsonEntities) {
        this.orderJsonEntities = orderJsonEntities;
    }
}
