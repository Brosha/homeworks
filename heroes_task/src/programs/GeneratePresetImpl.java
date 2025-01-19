package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.GeneratePreset;

import java.util.*;


/**
 * Сортировка по порядку убывания эффективности хоть и n log n, но на самом деле это константа, так как всего 4 типа
 * Операции над HashSet O(1)
 * Самое долгое здесь генерация юнитов. Поэтому сложность около N, где N - число юнитов. Оно не превышает число 4*11.
 * Так что скорость даже лучше, чем в тз:
 * Алгоритмическая сложность должна быть O(n*m) или лучше, где:
 * n — общее число типов юнитов,
 * m — максимальное число юнитов в армии.
 */

public class GeneratePresetImpl implements GeneratePreset {

    @Override
    public Army generate(List<Unit> unitList, int maxPoints) {

        //Сортируем по порядку убывания эффективности
        unitList.sort(Comparator.comparingDouble(((Unit unit) -> (double) (unit.getHealth() + unit.getBaseAttack() / unit.getCost()))).reversed());
        for (Unit unit : unitList) {
            System.out.println(unit.getName() + " : " + unit.getUnitType());
            System.out.println(unit.getxCoordinate() + " : " + unit.getyCoordinate());
        }

        //Заводим массив, где храним количество набранных работяг каждого типа
        int[] unintsCount = new int[unitList.size()];
        int armyCost = 0;
        int currentType = 0;


        ArrayList<Unit> recruted = new ArrayList<>();

        //Обёртка над двумя координатами
        Set<String> coordinatesWrapper = new HashSet<>();


        Random random = new Random();
        //Пусть будет немного рандомный выбор юнита
        double randomness = 0.3;

        while (armyCost < maxPoints && currentType < unitList.size()) {

            while (unintsCount[currentType] < 11 && armyCost < maxPoints) {
                int temp = currentType;
                //Рандомно определяем следует ли брать в армию следующего юнита по убыванию эффективности
                if (random.nextDouble() < randomness && temp + 1 < unitList.size()) {
                    temp++;
                }
                if (armyCost + unitList.get(temp).getCost() < maxPoints) {
                    unintsCount[temp]++;
                    armyCost += unitList.get(temp).getCost();
                    recruted.add(unitGenerator(coordinatesWrapper, unitList.get(temp), unintsCount[temp], random));

                } else break;

            }
            currentType++;
        }

        for (Unit unit : recruted) {
            System.out.println(unit.getName() + " : " + unit.getUnitType());
            System.out.println(unit.getxCoordinate() + " : " + unit.getyCoordinate());
        }
        System.out.println("Army Cost: " + armyCost);
        Army army = new Army();
        army.setUnits(recruted);
        army.setPoints(armyCost);
        return army;
    }

    private void generateCordinates(Set<String> coordinatesWrapper, Unit unit, Random random) {
        int x, y;
        String wrapper;
        do {
            x = random.nextInt(3);
            y = random.nextInt(21);
            wrapper = x + " " + y;
        } while (coordinatesWrapper.contains(wrapper));
        unit.setxCoordinate(x);
        unit.setyCoordinate(y);
        coordinatesWrapper.add(wrapper);
    }

    private Unit unitGenerator(Set<String> coordintatesWrapper, Unit unit, int id, Random random) {
        Unit unit1 = new Unit(unit.getName(), unit.getUnitType(), unit.getHealth(),
                unit.getBaseAttack(), unit.getCost(), unit.getAttackType(),
                unit.getAttackBonuses(), unit.getDefenceBonuses(), -1, -1);
        unit1.setName(unit.getUnitType() + " " + id);

        generateCordinates(coordintatesWrapper, unit1, random);

        return unit1;
    }


}