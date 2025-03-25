/**
    Задача 5
    Определить, какие классы автомобилей имеют наибольшее количество автомобилей с низкой средней позицией (больше 3.0)
    и вывести информацию о каждом автомобиле из этих классов, включая его имя, класс, среднюю позицию, количество гонок,
    в которых он участвовал, страну производства класса автомобиля, а также общее количество гонок для каждого класса.
    Отсортировать результаты по количеству автомобилей с низкой средней позицией.
 **/

-- Вычисляем среднюю позицию авто в гонках --
WITH avg_cars_positions AS (
    SELECT
        c.name AS car_name,
        c.class AS car_class,
        AVG(r.position) AS average_position,
        COUNT(r.race) AS race_count
    FROM cars c
    JOIN results r ON c.name = r.car
    GROUP BY c.name, c.class
),
-- Вычисляем классы с низкой средней позицией --
low_avg_positions AS (
    SELECT
        avp.car_class as car_class,
        COUNT(*) AS low_positions_count
    FROM avg_cars_positions avp
    WHERE average_position > 3.0
    GROUP BY car_class
    ORDER BY COUNT(*) DESC
),

-- Вычисляем количество гонок для каждого класса --
classes_race_total AS (
    SELECT
        c.class AS car_class,
        COUNT(r.race) AS total_races
    FROM cars c
    INNER JOIN results r ON c.name = r.car
    GROUP BY c.class
)

SELECT
    avp.car_name,
    avp.car_class,
    ROUND(avp.average_position, 4) AS average_position,
    avp.race_count,
    cl.country AS car_country,
    crt.total_races,
    lap.low_positions_count
FROM avg_cars_positions avp
JOIN low_avg_positions lap ON avp.car_class = lap.car_class
JOIN classes cl ON avp.car_class = cl.class
JOIN classes_race_total crt ON avp.car_class = crt.car_class
WHERE avp.average_position > 3.0
ORDER BY
	lap.low_positions_count DESC,
    avp.average_position DESC;