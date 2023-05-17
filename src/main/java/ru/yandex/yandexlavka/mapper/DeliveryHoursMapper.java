package ru.yandex.yandexlavka.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.yandexlavka.entity.DeliveryHours;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryHoursMapper implements RowMapper<DeliveryHours> {
    @Override
    public DeliveryHours mapRow(ResultSet rs, int rowNum) throws SQLException {
        DeliveryHours deliveryHours = new DeliveryHours();

        deliveryHours.setStart(rs.getTime("start_delivery").toLocalTime());
        deliveryHours.setEnd(rs.getTime("end_delivery").toLocalTime());
        deliveryHours.setId(rs.getInt("delivery_hours_id"));

        return deliveryHours;
    }
}
