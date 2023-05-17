package ru.yandex.yandexlavka.repository;

import org.springframework.stereotype.Repository;
import ru.yandex.yandexlavka.entity.Courier;
import ru.yandex.yandexlavka.exception.SaveException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Repository
public interface CourierRepository {
    Courier findCourierById(int id) throws NoSuchElementException;
    Courier save(Courier courier) throws SaveException;
    boolean deleteCourierById(int id);
    List<Courier> findAllCouriers(int offset, int limit);
    int calculateEarning(int courierId, LocalDateTime start, LocalDateTime end);
    int calculateRating(int courierId, LocalDateTime start, LocalDateTime end);
    int countCompleteOrders(int courierId);
}

