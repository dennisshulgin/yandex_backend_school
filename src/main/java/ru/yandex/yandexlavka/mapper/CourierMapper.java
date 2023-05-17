package ru.yandex.yandexlavka.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.yandexlavka.entity.Courier;
import ru.yandex.yandexlavka.entity.CourierType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourierMapper implements RowMapper<Courier> {
    @Override
    public Courier mapRow(ResultSet rs, int rowNum) throws SQLException {
        Courier courier = new Courier();
        courier.setId(rs.getInt("courier_id"));
        CourierType courierType = new CourierType();
        courierType.setId(rs.getInt("courier_type_id"));
        courier.setType(courierType);
        return courier;
    }
}
