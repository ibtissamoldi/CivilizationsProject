package units.defense;

import units.DefenseUnit;

public class Catapult extends DefenseUnit{

    public Catapult(int technologyDefense, int technologyAttack) {
                super(ARMOR_CATAPULT + (technologyDefense * PLUS_ARMOR_CATAPULT_BY_TECHNOLOGY * ARMOR_CATAPULT / 100),
                BASE_DAMAGE_CATAPULT + (technologyAttack * PLUS_ATTACK_CATAPULT_BY_TECHNOLOGY * BASE_DAMAGE_CATAPULT / 100)
        );

    }


    public int getFoodCost() {
        return FOOD_COST_CATAPULT;
    }

    public int getWoodCost() {
        return WOOD_COST_CATAPULT;
    }

    public int getIronCost() {
        return IRON_COST_CATAPULT;
    }

    public int getManaCost() {
        return MANA_COST_CATAPULT;
    }


    public int getChanceGeneratinWaste() {
        return CHANCE_GENERATNG_WASTE_CATAPULT;
    }

    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_CATAPULT;
    }
    
}
