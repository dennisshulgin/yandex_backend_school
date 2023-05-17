package ru.yandex.yandexlavka.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.entity.CompleteOrder;
import ru.yandex.yandexlavka.entity.Courier;
import ru.yandex.yandexlavka.entity.Order;
import ru.yandex.yandexlavka.exception.OrderAssigmentException;
import ru.yandex.yandexlavka.exception.SaveException;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public interface OrderRepository {
    Order findOrderById(int id) throws NoSuchElementException;

    Order save(Order order) throws SaveException;

    boolean deleteOrderById(int id);

    List<Order> findAllOrdersByCourierId(int courierId);

    List<Order> findAllOrders(int offset, int limit);

    List<Order> completeOrders(List<CompleteOrder> completeOrders) throws OrderAssigmentException;
}
