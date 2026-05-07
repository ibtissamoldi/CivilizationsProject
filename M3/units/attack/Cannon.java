package units.attack;

import units.AttackUnit;


public class Cannon extends AttackUnit{

    public Cannon(int technologyDefense, int technologyAttack) {
        super(
            ARMOR_CANNON + (technologyDefense * PLUS_ARMOR_CANNON_BY_TECHNOLOGY * ARMOR_CANNON / 100),
            BASE_DAMAGE_CANNON + (technologyAttack * PLUS_ATTACK_CANNON_BY_TECHNOLOGY * BASE_DAMAGE_CANNON / 100)
        );

    }

    /*2nd constructor.. */
    public Cannon() {
        super(ARMOR_CANNON, BASE_DAMAGE_CANNON);
    }

    public int getFoodCost() {
        return FOOD_COST_CANNON;
    }


    public int getWoodCost() {
        return WOOD_COST_CANNON;
    }


    public int getIronCost() {
        return IRON_COST_CANNON;
    }

    public int getManaCost() {
        return MANA_COST_CANNON;
    }


    public int getChanceGeneratinWaste() {
        return CHANCE_GENERATNG_WASTE_CANNON;
    }


    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_CANNON;
    }

}
