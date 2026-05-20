package M3.units.attack;

import M3.units.AttackUnit;

public class Swordsman extends AttackUnit {
    public Swordsman(int technologyDefense, int technologyAttack) {
		super(ARMOR_SWORDSMAN + (technologyDefense * PLUS_ARMOR_SWORDSMAN_BY_TECHNOLOGY * ARMOR_SWORDSMAN / 100),
            BASE_DAMAGE_SWORDSMAN + (technologyAttack * PLUS_ATTACK_SWORDSMAN_BY_TECHNOLOGY * BASE_DAMAGE_SWORDSMAN / 100)
        );
	}

    public Swordsman() {
		super(ARMOR_SWORDSMAN, BASE_DAMAGE_SWORDSMAN);
    } 
    
    public Swordsman(int armor, int damage, String nada) {
    	super(armor, damage);
	}

	public int getFoodCost() {
		return FOOD_COST_SWORDSMAN;
	}

	public int getWoodCost() {
		return WOOD_COST_SWORDSMAN;
	}

	public int getIronCost() {
		return IRON_COST_SWORDSMAN;
	}

	public int getManaCost() {
		return MANA_COST_SWORDSMAN;
	}

	public int getChanceGeneratinWaste() {
		return CHANCE_GENERATNG_WASTE_SWORDSMAN;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_SWORDSMAN;
	}
	
}
