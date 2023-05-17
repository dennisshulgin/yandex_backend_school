package ru.yandex.yandexlavka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.yandexlavka.entity.Courier;
import ru.yandex.yandexlavka.repository.CourierRepository;
import ru.yandex.yandexlavka.request_entity.CourierRequestEntity;
import ru.yandex.yandexlavka.request_entity.CourierRequestEntityWithRatingAndEarning;
import ru.yandex.yandexlavka.request_entity.CouriersRequestEntity;
import ru.yandex.yandexlavka.request_entity.CouriersRequestEntityWithOffsetAndLimit;
import ru.yandex.yandexlavka.util.Converter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("couriers")
public class CourierController {

    CourierRepository courierRepository;

    public CourierController(CourierRepository courierRepository) {
        this.courierRepository = courierRepository;
    }

    @PostMapping("")
    public ResponseEntity<?> createCouriers(@RequestBody CouriersRequestEntity couriersRequestEntity) {
        try {
            CourierRequestEntity[] courierRequests = couriersRequestEntity.getCourierRequestEntities();
            CourierRequestEntity[] courierResponse = new CourierRequestEntity[courierRequests.length];

            int index = 0;

            for (CourierRequestEntity courierRequest : courierRequests) {
                Courier courier = Converter.toCourier(courierRequest);
                courierRepository.save(courier);
                courierResponse[index++] = Converter.toCourierRequestEntity(courier);
            }

            CouriersRequestEntity couriersResponseEntity = new CouriersRequestEntity();
            couriersResponseEntity.setCourierRequestEntities(courierResponse);

            return new ResponseEntity<>(couriersResponseEntity, HttpStatus.OK);

        } catch (Exception exception) {
            exception.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getCouriers(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "1") int limit) {
        List<Courier> couriers = courierRepository.findAllCouriers(offset, limit);
        CouriersRequestEntityWithOffsetAndLimit couriersRequestEntityWithOffsetAndLimit = new CouriersRequestEntityWithOffsetAndLimit();
        CourierRequestEntity[] courierRequest = new CourierRequestEntity[couriers.size()];

        int index = 0;
        for(Courier courier : couriers) {
            courierRequest[index++] = Converter.toCourierRequestEntity(courier);
        }
        couriersRequestEntityWithOffsetAndLimit.setCourierRequestEntities(courierRequest);
        couriersRequestEntityWithOffsetAndLimit.setLimit(limit);
        couriersRequestEntityWithOffsetAndLimit.setOffset(offset);

        return new ResponseEntity<>(couriersRequestEntityWithOffsetAndLimit, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCourier(@PathVariable int id){
        try {
            Courier courier = Optional.ofNullable(courierRepository.findCourierById(id)).orElseThrow();
            CourierRequestEntity courierRequestEntity = Converter.toCourierRequestEntity(courier);
            return new ResponseEntity<>(courierRequestEntity, HttpStatus.OK);

        } catch (NoSuchElementException noSuchElementException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("meta-info/{id}")
    public ResponseEntity<?> getCourierMetaInfo(@PathVariable int id, @RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        try {
            Courier courier = Optional.ofNullable(courierRepository.findCourierById(id)).orElseThrow();

            int countCompleteOrders = courierRepository.countCompleteOrders(courier.getId());

            if(countCompleteOrders > 0) {
                LocalDateTime start = startDate.atStartOfDay();
                LocalDateTime end = endDate.atStartOfDay();

                int earning = courierRepository.calculateEarning(courier.getId(), start, end);
                int rating = courierRepository.calculateRating(courier.getId(), start, end);
                CourierRequestEntityWithRatingAndEarning courierRequestEntityWithRatingAndEarning
                        = Converter.toCourierRequestEntityWithRatingAndEarning(courier, rating, earning);
                return new ResponseEntity<>(courierRequestEntityWithRatingAndEarning, HttpStatus.OK);
            }

            CourierRequestEntity courierRequestEntity = Converter.toCourierRequestEntity(courier);

            return new ResponseEntity<>(courierRequestEntity, HttpStatus.OK);

        } catch (NoSuchElementException noSuchElementException) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteCourier(@PathVariable int id) {
        boolean isDeleted = courierRepository.deleteCourierById(id);

        if(isDeleted) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
