package M3.units.defense;

import M3.units.DefenseUnit;

public class ArrowTower extends DefenseUnit{

    public ArrowTower(int technologyDefense, int technologyAttack) {
            super(ARMOR_ARROWTOWER + (technologyDefense * PLUS_ARMOR_ARROWTOWER_BY_TECHNOLOGY * ARMOR_ARROWTOWER / 100),
            BASE_DAMAGE_ARROWTOWER + (technologyAttack * PLUS_ATTACK_ARROWTOWER_BY_TECHNOLOGY * BASE_DAMAGE_ARROWTOWER / 100)
        );
    }


    public int getFoodCost() {
        return FOOD_COST_ARROWTOWER;
    }

    public int getWoodCost() {
        return WOOD_COST_ARROWTOWER;
    }

    public int getIronCost() {
        return IRON_COST_ARROWTOWER;
    }

    public int getManaCost() {
        return MANA_COST_ARROWTOWER;
    }


    public int getChanceGeneratinWaste() {
        return CHANCE_GENERATNG_WASTE_ARROWTOWER;
    }

    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_ARROWTOWER;
    }

    
}
