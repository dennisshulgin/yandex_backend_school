package ru.yandex.yandexlavka.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.yandex.yandexlavka.entity.Region;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RegionMapper implements RowMapper<Region> {
    @Override
    public Region mapRow(ResultSet rs, int rowNum) throws SQLException {
        Region region = new Region();

        region.setId(rs.getInt("region_id"));

        return region;
    }
}
