package ru.yandex.yandexlavka.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.yandexlavka.entity.Order;
import ru.yandex.yandexlavka.entity.Region;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;

public class OrderMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
        Order order = new Order();

        Region region = new Region();
        region.setId(rs.getInt("region_id"));
        order.setRegion(region);

        order.setId(rs.getInt("order_id"));
        Optional<Timestamp> optionalCompletedTime = Optional.ofNullable(rs.getTimestamp("completed_time"));

        optionalCompletedTime.ifPresent(timestamp -> order.setCompletedTime(timestamp.toLocalDateTime()));

        order.setWeight(rs.getFloat("weight"));
        order.setCost(rs.getInt("price"));
        return order;
    }
}
