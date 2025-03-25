/**
    Задача 1
    Определить, какие клиенты сделали более двух бронирований в разных отелях, и вывести информацию о каждом таком клиенте,
    включая его имя, электронную почту, телефон, общее количество бронирований, а также список отелей, в которых они бронировали
    номера (объединенные в одно поле через запятую с помощью CONCAT). Также подсчитать среднюю длительность их пребывания (в днях)
    по всем бронированиям. Отсортировать результаты по количеству бронирований в порядке убывания.
**/
-- Считаем количество броней каждого клиента в разных отелях --
WITH customer_bookings AS (
    SELECT
        c.ID_customer,
        c.name AS customer_name,
        c.email,
        c.phone,
        COUNT(DISTINCT h.ID_hotel) AS unique_hotels_count,
        COUNT(b.ID_booking) AS total_bookings
    FROM customer c
    JOIN booking b ON c.ID_customer = b.ID_customer
    JOIN room r ON b.ID_room = r.ID_room
    JOIN hotel h ON r.ID_hotel = h.ID_hotel
    GROUP BY c.ID_customer, c.name, c.email, c.phone
    HAVING COUNT(DISTINCT h.ID_hotel) > 1
           AND COUNT(b.ID_booking) > 2
),
-- Собираем детали бронирований для этих клиентов --
booking_details AS (

    SELECT
        cb.ID_customer,
        cb.customer_name,
        cb.email,
        cb.phone,
        cb.total_bookings,
        h.name AS hotel_name,
        (b.check_out_date - b.check_in_date) AS stay_duration
    FROM customer_bookings cb
    JOIN Booking b ON cb.ID_customer = b.ID_customer
    JOIN Room r ON b.ID_room = r.ID_room
    JOIN Hotel h ON r.ID_hotel = h.ID_hotel
)

-- Финальный запрос с агрегированием данных --
SELECT
    customer_name,
    email,
    phone,
    total_bookings,
    ROUND(AVG(stay_duration), 4) AS average_stay_duration,
    STRING_AGG(DISTINCT hotel_name, ', ') AS hotel_names
FROM booking_details
GROUP BY ID_customer, customer_name, email, phone, total_bookings
ORDER BY total_bookings DESC;