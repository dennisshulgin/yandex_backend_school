package ru.yandex.yandexlavka.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.yandexlavka.entity.WorkingHours;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalTime;

public class WorkingHoursMapper implements RowMapper<WorkingHours> {
    @Override
    public WorkingHours mapRow(ResultSet rs, int rowNum) throws SQLException {
        WorkingHours workingHours = new WorkingHours();
        workingHours.setId(rs.getInt("working_hours_id"));
        Time startTimeSql = rs.getTime("start_work");
        LocalTime startTime = startTimeSql.toLocalTime();

        Time endTimeSql = rs.getTime("end_work");
        LocalTime endTime = endTimeSql.toLocalTime();

        workingHours.setStart(startTime);
        workingHours.setEnd(endTime);


        return workingHours;
    }
}
