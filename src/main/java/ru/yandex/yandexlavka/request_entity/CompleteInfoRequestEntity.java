package ru.yandex.yandexlavka.request_entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompleteInfoRequestEntity {

    @JsonProperty("courier_id")
    int courierId;
    @JsonProperty("order_id")
    int orderId;
    @JsonProperty("complete_time")
    LocalDateTime completeTime;

    @JsonGetter("courier_id")
    public int getCourierId() {
        return courierId;
    }

    @JsonSetter("courier_id")
    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }

    @JsonGetter("order_id")
    public int getOrderId() {
        return orderId;
    }

    @JsonSetter("order_id")
    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    @JsonGetter("complete_time")
    public LocalDateTime getCompleteTime() {
        return completeTime;
    }

    @JsonSetter("complete_time")
    public void setCompleteTime(LocalDateTime completedTime) {
        this.completeTime = completedTime;
    }
}
