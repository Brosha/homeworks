/**
    Задача 2
    Необходимо провести анализ клиентов, которые сделали более двух бронирований в разных отелях и потратили более
    500 долларов на свои бронирования. Для этого:
    -- Определить клиентов, которые сделали более двух бронирований и забронировали номера в более чем одном отеле.
       Вывести для каждого такого клиента следующие данные: ID_customer, имя, общее количество бронирований,
       общее количество уникальных отелей, в которых они бронировали номера, и общую сумму, потраченную на бронирования.
    -- Также определить клиентов, которые потратили более 500 долларов на бронирования, и вывести для них ID_customer,
       имя, общую сумму, потраченную на бронирования, и общее количество бронирований.
    -- В результате объединить данные из первых двух пунктов, чтобы получить список клиентов, которые соответствуют
       условиям обоих запросов. Отобразить поля: ID_customer, имя, общее количество бронирований, общую сумму,
       потраченную на бронирования, и общее количество уникальных отелей. Результаты отсортировать по общей сумме,
       потраченной клиентами, в порядке возрастания.

**/


-- Клиенты с более чем 2 бронированиями в разных отелях --
WITH customers_with_bookings AS (
    SELECT
        c.ID_customer,
        c.name,
        COUNT(DISTINCT b.ID_booking) AS total_bookings,
        COUNT(DISTINCT h.ID_hotel) AS unique_hotels
    FROM customer c
    JOIN booking b ON c.ID_customer = b.ID_customer
    JOIN room r ON b.ID_room = r.ID_room
    JOIN hotel h ON r.ID_hotel = h.ID_hotel
    GROUP BY c.ID_customer, c.name
    HAVING COUNT(DISTINCT b.ID_booking) > 2
       AND COUNT(DISTINCT h.ID_hotel) > 1
),

-- Клиенты, потратившие более $500 --
spenders AS (
    SELECT
        c.ID_customer,
        c.name,
         SUM(r.price) AS total_spent,
        COUNT(DISTINCT b.ID_booking) AS total_bookings
    FROM customer c
    JOIN booking b ON c.ID_customer = b.ID_customer
    JOIN room r ON b.ID_room = r.ID_room
    GROUP BY c.ID_customer, c.name
    HAVING SUM(r.price) > 500
)

-- Объединение результатов для клиентов, удовлетворяющих обоим условиям --
SELECT
    c.ID_customer,
    c.name,
    c.total_bookings,
    b.total_spent,
    c.unique_hotels
FROM customers_with_bookings c
JOIN spenders b ON c.ID_customer = b.ID_customer
ORDER BY b.total_spent ASC;