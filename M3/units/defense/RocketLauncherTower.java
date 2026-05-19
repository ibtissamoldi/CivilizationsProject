package M3.units.defense;

import M3.units.DefenseUnit;

public class RocketLauncherTower extends DefenseUnit{

    public RocketLauncherTower(int technologyDefense, int technologyAttack) {
        super(
            ARMOR_ROCKETLAUNCHERTOWER + (technologyDefense * PLUS_ARMOR_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY * ARMOR_ROCKETLAUNCHERTOWER / 100),
            BASE_DAMAGE_ROCKETLAUNCHERTOWER + (technologyAttack * PLUS_ATTACK_ROCKETLAUNCHERTOWER_BY_TECHNOLOGY * BASE_DAMAGE_ROCKETLAUNCHERTOWER / 100)
        );

    }
    
    public RocketLauncherTower(int armor, int damage, String nada) {
    	super(armor, damage);
	}

    public int getFoodCost() {
        return FOOD_COST_ROCKETLAUNCHERTOWER;
    }

    public int getWoodCost() {
        return WOOD_COST_ROCKETLAUNCHERTOWER;
    }

    public int getIronCost() {
        return IRON_COST_ROCKETLAUNCHERTOWER;
    }

    public int getManaCost() {
        return MANA_COST_ROCKETLAUNCHERTOWER;
    }

    public int getChanceGeneratinWaste() {
        return CHANCE_GENERATNG_WASTE_ROCKETLAUNCHERTOWER;
    }

     public int getChanceAttackAgain() {
        return CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER;
    }


    

    
}
