/*
	Задача 2
	Определить автомобиль, который имеет наименьшую среднюю позицию в гонках среди всех автомобилей,
	и вывести информацию об этом автомобиле, включая его класс, среднюю позицию, количество гонок, в которых он участвовал,
	и страну производства класса автомобиля. Если несколько автомобилей имеют одинаковую наименьшую среднюю позицию,
	выбрать один из них по алфавиту (по имени автомобиля).
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
    Находим минимальную среднюю позицию авто
**/
min_avg_auto_positions AS (
    SELECT
        MIN(average_position) AS min_avg_position
    FROM avg_cars_positions
)
/**
    Получаем итоговый результат из двух запросов, отбирая только те авто, где
    минимальная средняя позиция авто минимальна среди всех, добавляем страну таблицы class
**/

SELECT
    avp.car_name,
    avp.car_class,
    ROUND(avp.average_position, 4) AS average_position,
    avp.race_count,
	cl.country
FROM avg_cars_positions avp
JOIN min_avg_auto_positions mp ON avp.average_position = mp.min_avg_position
JOIN classes cl ON avp.car_class = cl.class
ORDER BY avp.car_name
LIMIT 1