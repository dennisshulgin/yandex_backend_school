package ru.yandex.yandexlavka.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.yandexlavka.entity.*;
import ru.yandex.yandexlavka.exception.SaveException;
import ru.yandex.yandexlavka.mapper.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class CourierRepositoryImpl implements CourierRepository{

    private JdbcTemplate jdbcTemplate;

    public CourierRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Courier findCourierById(int id) throws NoSuchElementException {
        // Ищем курьера в базе данных
        String sqlCourierByIdQuery = "SELECT * FROM find_courier_by_id(?);";

        Optional<Courier> courierOptional = jdbcTemplate.query(sqlCourierByIdQuery, new Object[]{id}, new CourierMapper())
                .stream().findAny();

        if(courierOptional.isEmpty()) {
            throw new NoSuchElementException("Courier not found.");
        }
        Courier courier = courierOptional.get();

        // Ищем тип курьера в базе
        String sqlCourierTypeQuery = "SELECT * FROM find_type_by_id(?)";

        Optional<CourierType> typeOptional = jdbcTemplate.query(sqlCourierTypeQuery, new Object[]{courier.getType().getId()}, new CourierTypeMapper())
                .stream().findAny();

        if(typeOptional.isEmpty()) {
            throw new IllegalStateException("Incorrect courier type.");
        }

        courier.setType(typeOptional.get());

        // Собираем регионы курьера
        String sqlRegionsByCourierIdQuery = "SELECT * FROM find_regions_by_courier_id(?);";
        List<Region> regions = jdbcTemplate.query(sqlRegionsByCourierIdQuery, new Object[]{id}, new RegionMapper());
        courier.setRegions(regions);

        // Собираем рабочие часы курьера
        String sqlWorkingHoursByCourierIdQuery = "SELECT * FROM find_working_hours_by_courier_id(?)";
        List<WorkingHours> workingHoursList = jdbcTemplate.query(sqlWorkingHoursByCourierIdQuery, new Object[]{id}, new WorkingHoursMapper());
        courier.setWorkingHoursList(workingHoursList);

        return courier;
    }

    @Override
    @Transactional
    public Courier save(Courier courier) throws SaveException {
        // Проверяем тип курьера
        Optional<CourierType> optionalType = Optional.ofNullable(courier.getType());

        if(optionalType.isEmpty()) {
            throw new SaveException("Courier type is null");
        }

        CourierType type = optionalType.get();

        String sqlCourierTypeByCourierTypeNameQuery = "SELECT * FROM find_courier_type_by_name(?)";

        Optional<CourierType> optionalTypeFromDb = jdbcTemplate
                .query(sqlCourierTypeByCourierTypeNameQuery, new Object[]{type.getName()}, new CourierTypeMapper())
                .stream()
                .findAny();
        if(optionalTypeFromDb.isEmpty()) {
            throw new SaveException("Incorrect courier type.");
        }

        courier.setType(optionalTypeFromDb.get());

        // Создаем курьера
        String sqlCreateOrReplaceCourierQuery = "SELECT * FROM create_or_replace_courier(?,?)";

        Integer id = jdbcTemplate.queryForObject(sqlCreateOrReplaceCourierQuery, new Object[]{courier.getId(), courier.getType().getId()}, Integer.class);
        Optional<Integer> optionalId = Optional.ofNullable(id);

        if(optionalId.isEmpty()) {
            throw new SaveException("Courier id not found");
        }
        courier.setId(id);

        // Создаем рабочие часы
        List<WorkingHours> workingHoursList = Optional.ofNullable(courier.getWorkingHoursList()).orElse(new ArrayList<>());

        for(WorkingHours workingHours : workingHoursList) {
            String sqlInsertWorkingHoursQuery = "SELECT * FROM create_working_hours(?,?,?)";

            Optional<LocalTime> startWorking = Optional.ofNullable(workingHours.getStart());
            Optional<LocalTime> endWorking = Optional.ofNullable(workingHours.getStart());

            if(startWorking.isPresent() && endWorking.isPresent()) {
                Integer workingHoursId = jdbcTemplate.queryForObject(sqlInsertWorkingHoursQuery, new Object[] {workingHours.getStart(), workingHours.getEnd(), courier.getId()}, Integer.class);
                Optional<Integer> optionalWorkingHours = Optional.ofNullable(workingHoursId);

                if(optionalWorkingHours.isEmpty()) {
                    throw new IllegalStateException("Error create working hours");
                }
                workingHours.setId(optionalWorkingHours.get());
            } else {
                throw new SaveException("Incorrect courier working time.");
            }
        }

        // Регионы
        List<Region> regions = Optional.ofNullable(courier.getRegions()).orElse(new ArrayList<>());

        for (Region region : regions) {
            String sqlInsertRegionQuery = "SELECT * FROM create_region(?)";
            String sqlInsertCourierRegionQuery = "SELECT * FROM create_link_courier_region(?,?)";

            jdbcTemplate.queryForObject(sqlInsertRegionQuery, new Object[] {region.getId()}, Void.class);
            jdbcTemplate.queryForObject(sqlInsertCourierRegionQuery, new Object[] {courier.getId(), region.getId()}, Void.class);
        }

        return courier;
    }

    @Override
    @Transactional
    public boolean deleteCourierById(int id) {
        String sqlDeleteCourierByCourierIdReturningCourierIdQuery = "SELECT * FROM delete_courier_by_id(?)";

        Integer deletedId = jdbcTemplate.queryForObject(sqlDeleteCourierByCourierIdReturningCourierIdQuery, new Object[] {id}, Integer.class);
        Optional<Integer> optionalDeletedId = Optional.ofNullable(deletedId);

        return optionalDeletedId.isPresent();
    }

    @Override
    @Transactional
    public List<Courier> findAllCouriers(int offset, int limit) {
        String sqlCourierIds = "SELECT * FROM find_all_couriers(?,?)";

        List<Integer> ids = jdbcTemplate.query(sqlCourierIds, new Object[]{offset, limit}, (rs, rowNum) -> rs.getInt("courier_id"));
        List<Courier> couriers = new ArrayList<>(ids.size());

        for(int id : ids) {
            Courier courier = findCourierById(id);
            couriers.add(courier);
        }

        return couriers;
    }

    @Override
    @Transactional
    public int countCompleteOrders(int courierId) {
        String sqlCountCompleteOrdersQuery = "SELECT * FROM count_complete_orders(?)";

        Optional<Integer> optionalCountCompleteOrders = Optional.ofNullable(
                jdbcTemplate.queryForObject(sqlCountCompleteOrdersQuery, new Object[] {courierId}, Integer.class)
        );

        return optionalCountCompleteOrders.orElse(0);
    }

    @Override
    @Transactional
    public int calculateEarning(int courierId, LocalDateTime start, LocalDateTime end) {
        String sqlEarningQuery = "SELECT * FROM calculate_earning(?, ?, ?)";

        Optional<Integer> optionalEarning = Optional.ofNullable(
                jdbcTemplate.queryForObject(sqlEarningQuery, new Object[] {courierId, start, end}, Integer.class)
        );

        return optionalEarning.orElse(0);
    }

    @Override
    @Transactional
    public int calculateRating(int courierId, LocalDateTime start, LocalDateTime end) {
        String sqlRatingQuery = "SELECT * FROM calculate_rating(?, ?, ?)";

        Optional<Integer> optionalRating = Optional.ofNullable(
                jdbcTemplate.queryForObject(sqlRatingQuery, new Object[] {courierId, start, end}, Integer.class)
        );

        return optionalRating.orElse(0);
    }

}