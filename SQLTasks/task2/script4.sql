/**
    Задача 4
    Определить, какие автомобили имеют среднюю позицию лучше (меньше) средней позиции всех автомобилей в своем классе
    (то есть автомобилей в классе должно быть минимум два, чтобы выбрать один из них). Вывести информацию об этих
    автомобилях, включая их имя, класс, среднюю позицию, количество гонок, в которых они участвовали, и страну
    производства класса автомобиля. Также отсортировать результаты по классу и затем по средней позиции в порядке возрастания.

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

-- Вычисляем среднюю позицию каждого класса, у кого от двух авто--
avg_class_positions AS (
    SELECT
        avp.car_class AS car_class,
        AVG(avp.average_position) AS avg_class_position,
        COUNT(*) AS cars_in_class
    FROM avg_cars_positions avp
    GROUP BY car_class
    HAVING COUNT(*) >= 2
)
SELECT
    avp.car_name,
    avp.car_class,
    ROUND(avp.average_position, 4) AS average_position,
    avp.race_count,
    cl.country AS car_country
FROM avg_cars_positions avp
JOIN avg_class_positions aclp ON avp.car_class = aclp.car_class
JOIN Classes cl ON avp.car_class = cl.class
WHERE avp.average_position < aclp.avg_class_position
ORDER BY avp.car_class, avp.average_position ASC;