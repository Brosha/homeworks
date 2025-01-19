package programs;

import com.battle.heroes.army.Unit;
import com.battle.heroes.army.programs.SuitableForAttackUnitsFinder;

import java.util.ArrayList;
import java.util.List;

/*
   Перебираем N строк, в которой не более чем m столбцов - сложность O(n*m)
 */

public class SuitableForAttackUnitsFinderImpl implements SuitableForAttackUnitsFinder {

    @Override
    public List<Unit> getSuitableUnits(List<List<Unit>> unitsByRow, boolean isLeftArmyTarget) {
        if (isLeftArmyTarget){
            return leftTargets(unitsByRow);
        }
        else return rightTargets(unitsByRow);
        // Ваше решение

    }

    /**
     * Ищем крайних
     *
     */
    private List<Unit> leftTargets(List<List<Unit>> unitsByRow) {
        List<Unit> targets = new ArrayList<>();
        for (List<Unit> row : unitsByRow) {

            Unit unit = leftTraversal(row);
            if (unit!=null)
                targets.add(unit);
        }
        return targets;
    }

    private List<Unit> rightTargets(List<List<Unit>> unitsByRow) {
        List<Unit> targets = new ArrayList<>();
        for (List<Unit> row : unitsByRow) {

            Unit unit = rightTraversal(row);
            if (unit!=null)
                targets.add(unit);
        }
        return targets;
    }



    private Unit rightTraversal(List<Unit> units) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i) != null && units.get(i).isAlive()) {
                return units.get(i);
            }
        }
        return null;
    }

    private Unit leftTraversal(List<Unit> units) {
        for (int i = units.size() - 1; i <= 0; i--) {
            if (units.get(i) != null && units.get(i).isAlive()) {
                return units.get(i);
            }
        }
        return null;
    }
}
