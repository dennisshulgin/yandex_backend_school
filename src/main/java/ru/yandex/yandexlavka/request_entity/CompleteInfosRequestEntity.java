package ru.yandex.yandexlavka.request_entity;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CompleteInfosRequestEntity {

    @JsonProperty("complete_info")
    CompleteInfoRequestEntity[] completeInfoRequestEntities;

    @JsonGetter("complete_info")
    public CompleteInfoRequestEntity[] getCompleteInfoRequestEntities() {
        return completeInfoRequestEntities;
    }

    @JsonSetter("complete_info")
    public void setCompleteInfoRequestEntities(CompleteInfoRequestEntity[] completeInfoRequestEntities) {
        this.completeInfoRequestEntities = completeInfoRequestEntities;
    }
}
