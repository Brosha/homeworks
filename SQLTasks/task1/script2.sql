/*
	Задача 2
	Найти информацию о производителях и моделях различных типов транспортных средств (автомобили, мотоциклы и велосипеды),
	которые соответствуют заданным критериям.
*/

SELECT v.maker, v.model, c.horsepower, c.engine_capacity, 'Car' AS vehicle_type
FROM vehicle v
JOIN car c ON v.model=c.model
WHERE
	c.horsepower >150
	AND c.price < 350000
	AND c.engine_capacity <3
UNION ALL
SELECT v.maker, v.model, m.horsepower, m.engine_capacity, 'Motorcycle' AS vehicle_type
FROM vehicle v
JOIN motorcycle m ON v.model=m.model
WHERE
	m.horsepower >150
	AND m.price < 200000
	AND m.engine_capacity <1.5
UNION ALL
SELECT v.maker, v.model, NULL AS horsepower, NULL AS engine_capacity, 'Bicycle' AS vehicle_type
FROM vehicle v
JOIN bicycle b ON v.model=b.model
WHERE
	b.gear_count >18
	AND b.price <40000
ORDER BY horsepower DESC NULLS LAST