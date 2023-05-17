package ru.yandex.yandexlavka.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.entity.*;
import ru.yandex.yandexlavka.exception.OrderAssigmentException;
import ru.yandex.yandexlavka.exception.SaveException;
import ru.yandex.yandexlavka.mapper.DeliveryHoursMapper;
import ru.yandex.yandexlavka.mapper.OrderMapper;
import ru.yandex.yandexlavka.mapper.RegionMapper;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl implements OrderRepository{

    private JdbcTemplate jdbcTemplate;

    public OrderRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Order findOrderById(int id) {
        String sqlOrderByIdQuery = "SELECT * FROM find_order_by_id(?)";

        Optional<Order> orderOptional = jdbcTemplate.query(sqlOrderByIdQuery, new Object[]{id}, new OrderMapper())
                .stream().findAny();

        if(orderOptional.isEmpty()) {
            throw new NoSuchElementException("Order not found.");
        }

        Order order = orderOptional.get();

        String sqlRegionByRegionIdQuery = "SELECT * FROM find_region_by_id(?)";

        Region region = jdbcTemplate.query(sqlRegionByRegionIdQuery, new Object[]{order.getRegion().getId()}, new RegionMapper())
                .stream()
                .findAny()
                .orElse(null);
        order.setRegion(region);

        String sqlDeliveryHoursByOrderIdQuery = "SELECT * FROM find_delivery_hours_by_order_id(?)";

        List<DeliveryHours> deliveryHoursList = jdbcTemplate
                .query(sqlDeliveryHoursByOrderIdQuery, new Object[] {order.getId()}, new DeliveryHoursMapper());
        order.setDeliveryHours(deliveryHoursList);

        return order;
    }

    @Override
    @Transactional
    public Order save(Order order) throws SaveException {

        Optional<Region> optionalRegion = Optional.ofNullable(order.getRegion());

        if(optionalRegion.isEmpty()) {
            throw new SaveException("Region is null");
        }

        String sqlInsertRegionQuery = "SELECT * FROM create_region(?)";
        jdbcTemplate.queryForObject(sqlInsertRegionQuery, new Object[]{order.getRegion().getId()}, Void.class);

        String sqlInsertOrderQuery = "SELECT * FROM create_or_replace_order(?, ?, ?, ?)";

        Optional<Integer> id = Optional.ofNullable(
                jdbcTemplate.queryForObject(sqlInsertOrderQuery, new Object[] {order.getId(), order.getWeight(),
                        order.getCost(), order.getRegion().getId()},
                        Integer.class)
        );

        if(id.isEmpty()) {
            throw new NoSuchElementException("Order not found");
        }

        order.setId(id.get());

        List<DeliveryHours> deliveryHoursList = Optional.ofNullable(order.getDeliveryHours()).orElse(new ArrayList<>());

        for(DeliveryHours deliveryHours : deliveryHoursList) {
            String sqlInsertDeliveryHoursQuery = "SELECT * FROM create_delivery_hours(?,?,?)";

            Optional<LocalTime> startDelivery = Optional.ofNullable(deliveryHours.getStart());
            Optional<LocalTime> endDelivery = Optional.ofNullable(deliveryHours.getStart());

            if(startDelivery.isPresent() && endDelivery.isPresent()) {
                Integer workingHoursId = jdbcTemplate.queryForObject(sqlInsertDeliveryHoursQuery, new Object[] {deliveryHours.getStart(), deliveryHours.getEnd(), order.getId()}, Integer.class);
                Optional<Integer> optionalDeliveryHours = Optional.ofNullable(workingHoursId);

                if(optionalDeliveryHours.isEmpty()) {
                    throw new IllegalStateException("Error create delivery hours");
                }
                deliveryHours.setId(optionalDeliveryHours.get());
            } else {
                throw new SaveException("Incorrect order delivery time.");
            }
        }

        return order;
    }

    @Override
    @Transactional
    public boolean deleteOrderById(int id) {
        String sqlDeleteOrderByOrderIdReturningOrderIdQuery = "SELECT * FROM delete_order_by_id(?)";

        Optional<Integer> deletedId = Optional.ofNullable(
                jdbcTemplate.queryForObject(sqlDeleteOrderByOrderIdReturningOrderIdQuery, new Object[] {id}, Integer.class));

        return deletedId.isPresent();
    }

    @Override
    @Transactional
    public List<Order> findAllOrders(int offset, int limit) {
        String sqlOrderIds = "SELECT * FROM find_all_orders(?,?)";

        List<Integer> ids = jdbcTemplate.query(sqlOrderIds, new Object[]{offset, limit}, (rs, rowNum) -> rs.getInt("order_id"));
        List<Order> orders = new ArrayList<>(ids.size());

        for(int id : ids) {
            Order order = findOrderById(id);
            orders.add(order);
        }

        return orders;
    }

    @Override
    @Transactional
    public List<Order> completeOrders(List<CompleteOrder> completeOrders) throws OrderAssigmentException{
        String sqlCompleteOrderQuery = "SELECT * FROM complete_order(?,?,?)";

        List<Order> orders = new ArrayList<>();

        for (CompleteOrder completeOrder : completeOrders) {
            Optional<Boolean> result = Optional.ofNullable(
                    jdbcTemplate.queryForObject(sqlCompleteOrderQuery, new Object[] {completeOrder.getOrderId(),
                    completeOrder.getCourierId(),
                    completeOrder.getCompletedTime()},
                    Boolean.class)
            );

            if(result.isEmpty() || !result.get()) {
                throw new OrderAssigmentException("Order already assigment");
            }

            Order order = findOrderById(completeOrder.getOrderId());
            orders.add(order);
        }

        return orders;
    }

    @Override
    public List<Order> findAllOrdersByCourierId(int courierId) {
        return null;
    }
}
