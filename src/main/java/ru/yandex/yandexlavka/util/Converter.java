package ru.yandex.yandexlavka.util;

import ru.yandex.yandexlavka.entity.*;
import ru.yandex.yandexlavka.request_entity.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Converter {
    public static Courier toCourier(CourierRequestEntity request) {
        Courier courier = new Courier();
        courier.setType(new CourierType(request.getType()));
        List<WorkingHours> workingHoursList = new ArrayList<>();

        for(String work : request.getWorkingHours()) {
            String[] workArray = work.split("-");
            LocalTime start = LocalTime.parse(workArray[0]);
            LocalTime end = LocalTime.parse(workArray[1]);
            WorkingHours workingHours = new WorkingHours();
            workingHours.setStart(start);
            workingHours.setEnd(end);
            workingHoursList.add(workingHours);
        }
        courier.setWorkingHoursList(workingHoursList);

        List<Region> regions = new ArrayList<>();

        for(int regionNum : request.getRegions()) {
            Region region = new Region();
            region.setId(regionNum);
            regions.add(region);
        }

        courier.setRegions(regions);
        return courier;
    }

    public static CourierRequestEntity toCourierRequestEntity(Courier courier) {
        CourierRequestEntity courierRequestEntity = new CourierRequestEntity();
        courierRequestEntity.setType(courier.getType().getName());
        int[] regionsReq = new int[courier.getRegions().size()];
        String[] workingHoursReq = new String[courier.getWorkingHoursList().size()];

        for(int i = 0; i < courier.getRegions().size(); i++) {
            regionsReq[i] = courier.getRegions()
                    .get(i)
                    .getId();
        }

        for(int i = 0; i < courier.getWorkingHoursList().size(); i++) {
            String start = courier.getWorkingHoursList()
                    .get(i)
                    .getStart()
                    .format(DateTimeFormatter.ofPattern("HH:mm"));
            String end = courier.getWorkingHoursList()
                    .get(i)
                    .getEnd()
                    .format(DateTimeFormatter.ofPattern("HH:mm"));
            workingHoursReq[i] = start + "-" + end;
        }

        courierRequestEntity.setWorkingHours(workingHoursReq);
        courierRequestEntity.setRegions(regionsReq);
        courierRequestEntity.setId(courier.getId());

        return courierRequestEntity;
    }

    public static CourierRequestEntityWithRatingAndEarning toCourierRequestEntityWithRatingAndEarning(Courier courier, int rating, int earning) {
        CourierRequestEntityWithRatingAndEarning courierRequestEntityWithRatingAndEarning = new CourierRequestEntityWithRatingAndEarning();
        courierRequestEntityWithRatingAndEarning.setType(courier.getType().getName());
        int[] regionsReq = new int[courier.getRegions().size()];
        String[] workingHoursReq = new String[courier.getWorkingHoursList().size()];

        for(int i = 0; i < courier.getRegions().size(); i++) {
            regionsReq[i] = courier.getRegions()
                    .get(i)
                    .getId();
        }

        for(int i = 0; i < courier.getWorkingHoursList().size(); i++) {
            String start = courier.getWorkingHoursList()
                    .get(i)
                    .getStart()
                    .format(DateTimeFormatter.ofPattern("HH:mm"));
            String end = courier.getWorkingHoursList()
                    .get(i)
                    .getEnd()
                    .format(DateTimeFormatter.ofPattern("HH:mm"));
            workingHoursReq[i] = start + "-" + end;
        }

        courierRequestEntityWithRatingAndEarning.setWorkingHours(workingHoursReq);
        courierRequestEntityWithRatingAndEarning.setRegions(regionsReq);
        courierRequestEntityWithRatingAndEarning.setId(courier.getId());
        courierRequestEntityWithRatingAndEarning.setEarning(earning);
        courierRequestEntityWithRatingAndEarning.setRating(rating);
        return courierRequestEntityWithRatingAndEarning;
    }

    public static Order toOrder(OrderRequestEntity orderRequestEntity) {
        Order order = new Order();
        order.setWeight(orderRequestEntity.getWeight());
        order.setCompletedTime(null);
        order.setCost(orderRequestEntity.getCost());

        Region region = new Region();
        region.setId(orderRequestEntity.getRegions());
        order.setRegion(region);

        List<DeliveryHours> deliveryHoursList = new ArrayList<>();

        for(String delivery : orderRequestEntity.getDeliveryHours()) {
            String[] deliveryArray = delivery.split("-");
            LocalTime start = LocalTime.parse(deliveryArray[0]);
            LocalTime end = LocalTime.parse(deliveryArray[1]);
            DeliveryHours deliveryHours = new DeliveryHours();
            deliveryHours.setStart(start);
            deliveryHours.setEnd(end);
            deliveryHoursList.add(deliveryHours);
        }

        order.setDeliveryHours(deliveryHoursList);

        return order;
    }

    public static OrderResponseEntity toOrderResponseEntity(Order order) {
        OrderResponseEntity orderResponseEntity = new OrderResponseEntity();
        orderResponseEntity.setOrderId(order.getId());
        orderResponseEntity.setCost(order.getCost());
        orderResponseEntity.setWeight(order.getWeight());
        orderResponseEntity.setRegions(order.getRegion().getId());
        orderResponseEntity.setCompletedTime(order.getCompletedTime());

        String[] deliveryHours = new String[order.getDeliveryHours().size()];

        for(int i = 0; i < order.getDeliveryHours().size(); i++) {
            String start = order.getDeliveryHours()
                    .get(i)
                    .getStart()
                    .format(DateTimeFormatter.ofPattern("HH:mm"));
            String end = order.getDeliveryHours()
                    .get(i)
                    .getEnd()
                    .format(DateTimeFormatter.ofPattern("HH:mm"));
            deliveryHours[i] = start + "-" + end;
        }

        orderResponseEntity.setDeliveryHours(deliveryHours);

        return orderResponseEntity;
    }

    public static CompleteOrder toCompleteOrder(CompleteInfoRequestEntity completeInfoRequestEntity) {
        CompleteOrder completeOrder = new CompleteOrder();

        completeOrder.setCourierId(completeInfoRequestEntity.getCourierId());
        completeOrder.setOrderId(completeInfoRequestEntity.getOrderId());
        completeOrder.setCompletedTime(completeInfoRequestEntity.getCompleteTime());
        return completeOrder;
    }
}














