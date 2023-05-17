package ru.yandex.yandexlavka.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.yandexlavka.entity.CourierType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourierTypeMapper implements RowMapper<CourierType> {

    @Override
    public CourierType mapRow(ResultSet rs, int rowNum) throws SQLException {
        CourierType courierType = new CourierType();

        courierType.setId(rs.getInt("courier_type_id"));
        courierType.setName(rs.getString("courier_type_name"));
        courierType.setEarningFactor(rs.getInt("earning_factor"));
        courierType.setRatingFactor(rs.getInt("rating_factor"));
        return courierType;
    }
}
