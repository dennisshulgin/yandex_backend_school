package ru.yandex.yandexlavka.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.entity.CompleteOrder;
import ru.yandex.yandexlavka.entity.Order;
import ru.yandex.yandexlavka.exception.OrderAssigmentException;
import ru.yandex.yandexlavka.repository.OrderRepository;
import ru.yandex.yandexlavka.request_entity.*;
import ru.yandex.yandexlavka.util.Converter;

import java.util.*;

@RestController
@RequestMapping("orders")
public class OrderController {

    OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> createOrders(@RequestBody OrdersRequestEntity ordersRequestEntity) {
        try {
            OrderRequestEntity[] orderRequests = ordersRequestEntity.getOrderJsonEntities();
            OrderResponseEntity[] orderResponse = new OrderResponseEntity[orderRequests.length];

            int index = 0;

            for (OrderRequestEntity orderRequest : orderRequests) {
                Order order = Converter.toOrder(orderRequest);
                orderRepository.save(order);
                orderResponse[index++] = Converter.toOrderResponseEntity(order);
            }

            return new ResponseEntity<>(orderResponse, HttpStatus.OK);
        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getOrders(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "1") int limit) {
        List<Order> orders = orderRepository.findAllOrders(offset, limit);

        OrderResponseEntity[] orderResponse = new OrderResponseEntity[orders.size()];

        int index = 0;
        for(Order order : orders) {
            orderResponse[index++] = Converter.toOrderResponseEntity(order);
        }

        return new ResponseEntity<>(orderResponse, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getOrder(@PathVariable int id){
        try {
            Order order = Optional.ofNullable(orderRepository.findOrderById(id)).orElseThrow();
            OrderResponseEntity orderResponseEntity = Converter.toOrderResponseEntity(order);
            return new ResponseEntity<>(orderResponseEntity, HttpStatus.OK);

        } catch (NoSuchElementException noSuchElementException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("complete")
    public ResponseEntity<?> completeOrder(@RequestBody CompleteInfosRequestEntity completeInfosRequestEntity){
        try {
            List<CompleteOrder> completeOrders = new ArrayList<>();

            for(CompleteInfoRequestEntity completeInfoRequestEntity : completeInfosRequestEntity.getCompleteInfoRequestEntities()) {
                CompleteOrder completeOrder = Converter.toCompleteOrder(completeInfoRequestEntity);
                completeOrders.add(completeOrder);
            }
            List<Order> orders = orderRepository.completeOrders(completeOrders);
            OrderResponseEntity[] orderResponse = new OrderResponseEntity[orders.size()];

            int index = 0;
            for(Order order : orders) {
                orderResponse[index++] = Converter.toOrderResponseEntity(order);
            }

            return new ResponseEntity<>(orderResponse, HttpStatus.OK);

        } catch (OrderAssigmentException orderAssigmentException) {
            orderAssigmentException.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
