package M3.units.attack;

import M3.units.AttackUnit;

public class Crossbow extends AttackUnit{

    public Crossbow(int technologyDefense, int technologyAttack) {
        super(
            ARMOR_CROSSBOW + (technologyDefense * PLUS_ARMOR_CROSSBOW_BY_TECHNOLOGY * ARMOR_CROSSBOW / 100),
            BASE_DAMAGE_CROSSBOW + (technologyAttack * PLUS_ATTACK_CROSSBOW_BY_TECHNOLOGY * BASE_DAMAGE_CROSSBOW / 100)
        );

    }

    /*2nd constructor..*/
    public Crossbow() {
        super(ARMOR_CROSSBOW, BASE_DAMAGE_CROSSBOW);
    } 
    public Crossbow(int armor, int damage, String nada) {
    	super(armor, damage);
	}
  
    
    public int getFoodCost() {
        return FOOD_COST_CROSSBOW;
    }

    public int getIronCost() {
        return IRON_COST_CROSSBOW;
    }

    public int getWoodCost() {
        return WOOD_COST_CROSSBOW;
    }

    public int getManaCost() {
        return MANA_COST_CROSSBOW;
    }

    public int getChanceGeneratinWaste() {
        return CHANCE_GENERATNG_WASTE_CROSSBOW;
    }

    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_CROSSBOW;
    }
    
}
