/**
    Задача 3
    Определить классы автомобилей, которые имеют наименьшую среднюю позицию в гонках, и вывести информацию о каждом автомобиле из этих классов,
    включая его имя, среднюю позицию, количество гонок, в которых он участвовал, страну производства класса автомобиля,а также общее количество гонок,
    в которых участвовали автомобили этих классов. Если несколько классов имеют одинаковую среднюю позицию, выбрать все из них.
**/


-- Вычисляем среднюю позицию каждого класса --

WITH avg_class_positions AS (
    SELECT
        c.class AS car_class,
        AVG(r.position) AS avg_class_position,
        COUNT(r.race) AS total_race_count
    FROM cars c
    JOIN results r ON c.name = r.car
    GROUP BY c.class
),
-- Вычисляем минимальную среднюю позицию --
min_avg_class_positions AS (
    SELECT MIN(avg_class_position) AS min_avg_position
    FROM avg_class_positions
),

-- Получаем список классов с минимальной средней позицией --
classes_with_min_avg_positions AS (
    SELECT avp.car_class, avp.avg_class_position, avp.total_race_count
    FROM avg_class_positions avp
    JOIN min_avg_class_positions mp ON avp.avg_class_position = mp.min_avg_position
)
/**
    Получаем итоговый результат из подзапроса, который вычисляет среднюю позицию авто,
    объединяя со списком классов с минимальной позицией, таблицей классов для получения страны
**/
SELECT
    avp.car_name,
    avp.car_class,
    ROUND(avp.average_position, 4) AS average_position,
    avp.race_count,
    cl.country,
    cmp.total_race_count AS total_races
FROM (
    SELECT
        c.name AS car_name,
        c.class AS car_class,
        AVG(r.position) AS average_position,
        COUNT(r.race) AS race_count
    FROM cars c
    JOIN results r ON c.name = r.car
    GROUP BY c.name, c.class
) avp
JOIN classes_with_min_avg_positions cmp ON avp.car_class = cmp.car_class
JOIN classes cl ON avp.car_class = cl.class
ORDER BY average_position, car_name;