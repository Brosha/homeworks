/*
    Задача 1
	Определить, какие автомобили из каждого класса имеют наименьшую среднюю позицию в гонках, и вывести информацию
	о каждом таком автомобиле для данного класса, включая его класс, среднюю позицию и количество гонок, в которых он участвовал.
	Также отсортировать результаты по средней позиции.
*/

/**
    Используем обобщённые табличные выражения avg_cars_positions и min_avg_class_positions
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

/**
    Для каждого класса авто находим минимальную среднюю позицию
    в гонках из результатов предыдущего запроса и группируем
**/

min_avg_class_positions AS (
    SELECT
        car_class,
        MIN(average_position) AS min_avg_position
    FROM avg_cars_positions
    GROUP BY car_class
)
/**
    Получаем итоговый результат из двух запросов, отбирая только те авто, где
    авто из одного класса и минимальная средняя позиция авто минимальна в классе
**/
SELECT
    avp.car_name,
    avp.car_class,
    ROUND(avp.average_position, 4) AS average_position,
    avp.race_count
FROM avg_cars_positions avp
JOIN min_avg_class_positions macp ON
	avp.car_class = macp.car_class
    AND avp.average_position = macp.min_avg_position
ORDER BY average_position;

