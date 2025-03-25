/**
    Задача 3
**/

-- Список отелей с категориями --
WITH categorized_hotels AS (
    SELECT
        h.ID_hotel,
        h.name AS hotel_name,
        h.location,
        AVG(r.price) AS avg_room_price,
        CASE
            WHEN AVG(r.price) < 175 THEN 'Дешевый'
            WHEN AVG(r.price) BETWEEN 175 AND 300 THEN 'Средний'
            ELSE 'Дорогой'
        END AS hotel_category
    FROM hotel h
    JOIN room r ON h.ID_hotel = r.ID_hotel
    GROUP BY h.ID_hotel, h.name, h.location
),

-- Список посещённых отелей клиентом с категориями --
visited_categorized_hotels AS (
    SELECT
        c.ID_customer,
        c.name AS customer_name,
        ch.hotel_name,
        ch.hotel_category,
        ROW_NUMBER() OVER (PARTITION BY c.ID_customer ORDER BY
            CASE ch.hotel_category
                WHEN 'Дорогой' THEN 1
                WHEN 'Средний' THEN 2
                ELSE 3
            END) AS category_priority
    FROM customer c
    JOIN booking b ON c.ID_customer = b.ID_customer
    JOIN room r ON b.ID_room = r.ID_room
    JOIN categorized_hotels ch ON r.ID_hotel = ch.ID_hotel
),

-- Получение категории клиента --
customer_category AS (
    SELECT
        ID_customer,
        customer_name,
        MIN(
            CASE hotel_category
                WHEN 'Дорогой' THEN 1
                WHEN 'Средний' THEN 2
                ELSE 3
            END
        ) AS preference_rank,
        STRING_AGG(DISTINCT hotel_name, ', ' ORDER BY hotel_name) AS visited_hotels
    FROM visited_categorized_hotels
    GROUP BY ID_customer, customer_name
)

-- Финальный результат с сортировкой
SELECT
    ID_customer,
    customer_name AS name,
    CASE
        WHEN preference_rank = 1 THEN 'Дорогой'
        WHEN preference_rank = 2 THEN 'Средний'
        ELSE 'Дешевый'
    END AS preferred_hotel_type,
    visited_hotels
FROM customer_category
ORDER BY
    CASE
        WHEN preference_rank = 3 THEN 1
        WHEN preference_rank = 2 THEN 2
        ELSE 3
    END,
    customer_name;