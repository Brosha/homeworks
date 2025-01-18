package programs;

import com.battle.heroes.army.Army;
import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.PrintBattleLog;
import com.battle.heroes.army.programs.SimulateBattle;

import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;


/**
 * Вставка в дерево O(n log n)
 * Удаление из дерева в худшем случае n Log n
 */

public class SimulateBattleImpl implements SimulateBattle {
    private PrintBattleLog printBattleLog; // Позволяет логировать. Использовать после каждой атаки юнита

    @Override
    public void simulate(Army playerArmy, Army computerArmy) throws InterruptedException {

        TreeSet<Unit> playersOrder = new TreeSet<>(Comparator.comparingInt((Unit::getBaseAttack)).reversed());
        TreeSet<Unit> computersOrder = new TreeSet<>(Comparator.comparingInt((Unit::getBaseAttack)).reversed());
        while (!playersOrder.isEmpty() && !computersOrder.isEmpty()) {
            simulateAttack(playersOrder);
            simulateAttack(computersOrder);
        }
    }

    private void simulateAttack(TreeSet<Unit> unitsOrder) throws InterruptedException {
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
}