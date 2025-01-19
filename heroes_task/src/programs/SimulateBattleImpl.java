package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.*;


/**
 * Вставка в новый List n
 * Сортировка в новом массиве по убыванию атаки n Log n
 * Вставка в Связанный список - n
 * Итерация будет каждый раз по отсортированному списку - n
 * Удаление будет константа
 * Значит, сложность будет O(n*n)
 */

public class SimulateBattleImpl implements SimulateBattle {

    // Позволяет логировать. Использовать после каждой атаки юнита
    private PrintBattleLog printBattleLog;

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {

        LinkedList<Unit> playersOrder = setup(playerArmy);
        LinkedList<Unit> computersOrder = setup(computerArmy);
        System.out.println("playersOrder size: "+playersOrder.size());

        while (!playersOrder.isEmpty() && !computersOrder.isEmpty()) {
            simulateAttack(playersOrder);
            simulateAttack(computersOrder);
        }
    }

    private void simulateAttack(LinkedList<Unit> unitsOrder) throws InterruptedException {
        Iterator<Unit> iterator = unitsOrder.iterator();
        while (iterator.hasNext()) {
            Unit attacker = iterator.next();
            if (!attacker.isAlive()) {
                iterator.remove();
            } else {
                Unit target = attacker.getProgram().attack();
                if (target != null) {
                    printBattleLog.printBattleLog(attacker, target);
                }
            }
        }
    }
    /**
      Создаём связанный список, который будет уже хранить отсортированные по атаке юниты.
        Нам не надо будет каждый раз сортировать после удаления
     **/

    private LinkedList<Unit> setup(Army army){
        ArrayList<Unit> units =new ArrayList<>(army.getUnits());
        Collections.sort(units, Comparator.comparingInt((Unit::getBaseAttack)).reversed());
        return new LinkedList<>(units);

    }
}