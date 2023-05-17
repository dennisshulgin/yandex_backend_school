DROP TABLE IF EXISTS delivery_hours;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS couriers_regions;
DROP TABLE IF EXISTS working_hours;
DROP TABLE IF EXISTS couriers;
DROP TABLE IF EXISTS courier_types;
DROP TABLE IF EXISTS regions;
DROP TABLE IF EXISTS complete_orders;

CREATE TABLE courier_types (
    courier_type_id INTEGER PRIMARY KEY NOT NULL,
    courier_type_name VARCHAR(10) NOT NULL,
    earning_factor INTEGER NOT NULL,
    rating_factor INTEGER NOT NULL
);

CREATE TABLE couriers (
    courier_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT 1) PRIMARY KEY,
    courier_type_id INTEGER NOT NULL,
    CONSTRAINT fk_courier_type
        FOREIGN KEY(courier_type_id)
        REFERENCES courier_types(courier_type_id)
        ON DELETE CASCADE
);

CREATE TABLE working_hours (
    working_hours_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT 1) PRIMARY KEY,
    start_work TIME WITHOUT TIME ZONE NOT NULL,
    end_work TIME WITHOUT TIME ZONE NOT NULL,
    courier_id INTEGER NOT NULL,
    CONSTRAINT fk_courier_working_hours
        FOREIGN KEY(courier_id)
        REFERENCES couriers(courier_id)
        ON DELETE CASCADE
);

CREATE TABLE regions (
    region_id INTEGER PRIMARY KEY NOT NULL
);

CREATE TABLE couriers_regions (
    courier_id INTEGER NOT NULL REFERENCES couriers(courier_id) ON DELETE CASCADE,
    region_id INTEGER NOT NULL REFERENCES regions(region_id) ON DELETE CASCADE,
    CONSTRAINT courier_region_pk PRIMARY KEY (courier_id, region_id)
);

CREATE TABLE orders (
    order_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT 1) PRIMARY KEY,
    weight REAL NOT NULL,
    price INTEGER NOT NULL,
    region_id INTEGER NOT NULL REFERENCES regions(region_id),

    CONSTRAINT chk_weight_orders CHECK (weight >= 0),
    CONSTRAINT chk_price_orders CHECK (price >= 0)
);

CREATE TABLE delivery_hours (
    delivery_hours_id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT 1) PRIMARY KEY,
    start_delivery TIME WITHOUT TIME ZONE NOT NULL,
    end_delivery TIME WITHOUT TIME ZONE NOT NULL,
    order_id INTEGER NOT NULL
        REFERENCES orders(order_id)
        ON DELETE CASCADE
);

CREATE TABLE complete_orders (
    order_id INTEGER PRIMARY KEY REFERENCES orders(order_id) ON DELETE CASCADE,
    courier_id INTEGER NOT NULL REFERENCES couriers(courier_id) ON DELETE CASCADE,
    completed_time TIMESTAMP WITHOUT TIME ZONE
);

CREATE INDEX idx_complete_orders_completed_time ON complete_orders (completed_time);

INSERT INTO courier_types
VALUES
    (1, 'FOOT', 2, 3),
    (2, 'AUTO', 3, 2),
    (3, 'BIKE', 4, 1);

CREATE OR REPLACE FUNCTION find_courier_by_id(in_courier_id INTEGER)
RETURNS SETOF couriers AS '
    SELECT *
    FROM couriers
    WHERE courier_id = in_courier_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION find_regions_by_courier_id(in_courier_id INTEGER)
RETURNS SETOF regions AS '
    SELECT *
    FROM regions
    WHERE region_id IN (SELECT region_id
                        FROM couriers_regions
                        WHERE courier_id = in_courier_id)
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION find_type_by_id(in_type_id INTEGER)
RETURNS SETOF courier_types AS '
    SELECT *
    FROM courier_types
    WHERE courier_type_id = in_type_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION find_working_hours_by_courier_id(in_courier_id INTEGER) RETURNS SETOF working_hours AS '
    SELECT *
    FROM working_hours
    WHERE courier_id = in_courier_id
' LANGUAGE SQL;


CREATE OR REPLACE FUNCTION find_delivery_hours_by_order_id(in_order_id INTEGER) RETURNS SETOF delivery_hours AS '
    SELECT *
    FROM delivery_hours
    WHERE order_id = in_order_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION find_region_by_id(in_region_id INTEGER) RETURNS SETOF regions AS '
    SELECT *
    FROM regions
    WHERE region_id = in_region_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION create_or_replace_courier(in_courier_id INTEGER, in_courier_type_id INTEGER)
    RETURNS INTEGER AS '
	DECLARE
        res_courier_id INTEGER;
		courier_count INTEGER;
    BEGIN
		res_courier_id := in_courier_id;

		IF in_courier_id = 0 THEN
			INSERT INTO couriers (courier_type_id)
			VALUES (in_courier_type_id)
			RETURNING courier_id
			INTO res_courier_id;
        ELSE
            SELECT COUNT(*) INTO courier_count
            FROM couriers
            WHERE courier_id = in_courier_id;

            IF courier_count > 0 THEN

                UPDATE couriers
                SET courier_type_id = in_courier_type_id
                WHERE courier_id = in_courier_id;

                DELETE FROM working_hours
                WHERE courier_id = in_courier_id;

                DELETE FROM couriers_regions
                WHERE courier_id = in_courier_id;
            ELSE
                res_courier_id = NULL;
            END IF;
        END IF;
    RETURN res_courier_id;
END;
' LANGUAGE PLPGSQL;

CREATE OR REPLACE FUNCTION find_courier_type_by_name(in_type VARCHAR) RETURNS SETOF courier_types AS '
    SELECT *
    FROM courier_types
    WHERE courier_type_name = in_type
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION create_working_hours(in_start TIME, in_end TIME, in_courier_id INTEGER) RETURNS INTEGER AS '
	INSERT INTO working_hours (start_work, end_work, courier_id)
    VALUES (in_start, in_end, in_courier_id)
	RETURNING working_hours_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION create_delivery_hours(in_start TIME, in_end TIME, in_order_id INTEGER) RETURNS INTEGER AS '
    INSERT INTO delivery_hours (start_delivery, end_delivery, order_id)
    VALUES (in_start, in_end, in_order_id)
    RETURNING delivery_hours_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION create_region(in_region_id INTEGER) RETURNS VOID AS '
    INSERT INTO regions (region_id)
    VALUES (in_region_id)
    ON CONFLICT DO NOTHING
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION create_link_courier_region(in_courier_id INTEGER, in_region_id INTEGER) RETURNS VOID AS '
    INSERT INTO couriers_regions
    VALUES
    (in_courier_id,in_region_id)
' LANGUAGE SQL;


CREATE OR REPLACE FUNCTION delete_courier_by_id(in_courier_id INTEGER) RETURNS INTEGER AS '
    DELETE FROM couriers
    WHERE courier_id = in_courier_id
    RETURNING courier_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION find_all_couriers(in_offset INTEGER, in_limit INTEGER) RETURNS SETOF couriers AS '
    SELECT *
    FROM couriers
    OFFSET in_offset
    LIMIT in_limit
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION find_all_orders(in_offset INTEGER, in_limit INTEGER) RETURNS SETOF orders AS '
    SELECT *
    FROM orders
    OFFSET in_offset
    LIMIT in_limit
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION find_order_by_id(in_order_id INTEGER)
RETURNS TABLE (order_id INTEGER, weight REAL, price INTEGER, region_id INTEGER, completed_time TIMESTAMP WITHOUT TIME ZONE) AS '
    SELECT order_id, weight, price, region_id, completed_time
    FROM orders
    LEFT JOIN complete_orders
    USING(order_id)
    WHERE order_id = in_order_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION create_order(in_weight REAL, in_region_id INTEGER, in_cost INTEGER) RETURNS INTEGER AS '
    INSERT INTO orders (weight, price, region_id)
    VALUES (in_weight, in_cost, in_region_id)
    RETURNING order_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION create_or_replace_order(in_order_id INTEGER, in_weight REAL, in_cost INTEGER, in_region_id INTEGER)
    RETURNS INTEGER AS '
DECLARE
    res_order_id INTEGER;
    order_count INTEGER;
BEGIN
    res_order_id := in_order_id;

    IF in_order_id = 0 THEN
        INSERT INTO orders (weight, price, region_id)
        VALUES (in_weight, in_cost, in_region_id)
        RETURNING order_id
            INTO res_order_id;
    ELSE
        SELECT COUNT(*) INTO order_count
        FROM orders
        WHERE order_id = in_order_id;

        IF order_count > 0 THEN
            UPDATE orders
            SET weight = in_weight,
                price = in_cost,
                region_id = in_region_id
            WHERE order_id = in_order_id;

            DELETE FROM delivery_hours
            WHERE order_id = in_order_id;
        ELSE
            res_order_id = NULL;
        END IF;
    END IF;

    RETURN res_order_id;
END
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION delete_order_by_id(in_order_id INTEGER) RETURNS INTEGER AS '
    DELETE FROM orders
    WHERE order_id = in_order_id
    RETURNING order_id
' LANGUAGE SQL;


CREATE OR REPLACE FUNCTION complete_order(in_order_id INTEGER, in_courier_id INTEGER, in_completed_time TIMESTAMP) RETURNS BOOLEAN AS '
BEGIN
    INSERT INTO complete_orders
    VALUES (in_order_id, in_courier_id, in_completed_time);
    RETURN TRUE;
EXCEPTION
    WHEN OTHERS
        THEN RETURN FALSE;
END
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION count_complete_orders(in_courier_id INTEGER) RETURNS BIGINT AS '
    SELECT COUNT(*)
    FROM complete_orders
    WHERE courier_id = in_courier_id
' LANGUAGE SQL;

CREATE OR REPLACE FUNCTION calculate_rating(in_courier_id INTEGER, in_start_date TIMESTAMP, in_end_date TIMESTAMP)
    RETURNS INTEGER AS '
DECLARE
    hours INTEGER;
    factor INTEGER;
    count_orders INTEGER;
BEGIN
    hours := EXTRACT(EPOCH FROM in_end_date - in_start_date) / 3600;

    SELECT rating_factor
    INTO factor
    FROM couriers
             JOIN courier_types
                  USING(courier_type_id)
    WHERE courier_id = in_courier_id;

    SELECT COUNT(*)
    INTO count_orders
    FROM complete_orders
    WHERE courier_id = in_courier_id AND completed_time >= in_start_date
      AND completed_time < in_end_date;
    RETURN (count_orders / hours) * factor;
END
' LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION calculate_earning(in_courier_id INTEGER, in_start_date TIMESTAMP, in_end_date TIMESTAMP)
    RETURNS INTEGER AS '
DECLARE
    factor INTEGER;
    earning INTEGER;
BEGIN
    SELECT earning_factor
    INTO factor
    FROM couriers
             JOIN courier_types
                  USING(courier_type_id)
    WHERE courier_id = in_courier_id;

    SELECT SUM(factor * price)
    INTO earning
    FROM complete_orders
             JOIN orders
                  USING(order_id)
    WHERE complete_orders.courier_id = in_courier_id;

    RETURN earning;
END
' LANGUAGE plpgsql;