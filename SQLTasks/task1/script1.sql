/*
	Задача 1
	Найдите производителей (maker) и модели всех мотоциклов, которые имеют мощность более 150 лошадиных сил,
	стоят менее 20 тысяч долларов и являются спортивными (тип Sport). Также отсортируйте результаты по мощности в порядке убывания.
*/

SELECT v.maker, v.model
FROM vehicle v
JOIN motorcycle m ON v.model=m.model
WHERE
	m.horsepower >150
	AND m.price < 200000
	AND m.type = 'Sport'
ORDER BY m.horsepower DESC