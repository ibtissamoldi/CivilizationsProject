package M3.units.attack;

import M3.units.AttackUnit;

public class Spearman extends AttackUnit {

    public Spearman(int technologyDefense, int technologyAttack) {
        super(ARMOR_SPEARMAN + (technologyDefense * PLUS_ARMOR_SPEARMAN_BY_TECHNOLOGY * ARMOR_SPEARMAN / 100),
            BASE_DAMAGE_SPEARMAN + (technologyAttack * PLUS_ATTACK_SPEARMAN_BY_TECHNOLOGY * BASE_DAMAGE_SPEARMAN / 100)
        );
    }

    public Spearman() {
        super(ARMOR_SPEARMAN, BASE_DAMAGE_SPEARMAN);
    } 
    
    public Spearman(int armor, int damage, String nada) {
    	super(armor, damage);
	}

    public int getFoodCost() {
        return FOOD_COST_SPEARMAN;
    }

    public int getWoodCost() {
        return WOOD_COST_SPEARMAN;
    }

    public int getIronCost() {
        return IRON_COST_SPEARMAN;
    }

    public int getManaCost() {
        return MANA_COST_SPEARMAN;
    }

    public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_SPEARMAN;
    }

    public int getChanceGeneratinWaste() {
        return CHANCE_GENERATNG_WASTE_SPEARMAN;
    }
    
}
