package M3.game;

import java.util.ArrayList;

import M3.interfaces.MilitaryUnit;
import M3.interfaces.Variables;
import M3.units.AttackUnit;
import M3.units.DefenseUnit;
import M3.units.SpecialUnit;

public class Battle implements Variables{
	
    private ArrayList<MilitaryUnit>[] civilizationArmy = new ArrayList[9];
    private ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[4];
    private ArrayList<MilitaryUnit>[][] armies = new ArrayList[2][9];
    private String battleDevelopment;
    private int initialCostFleet;
    private int initialNumberUnitsCivilization, initialNumberUnitsEnemy;
    private int wasteWoodIron;
    private int enemyDrops;
    private int civilizationDrops;
    private int resourcesLooses;
    private int initialArmies;
    private int actualNumberUnitsCivilization;
    private int actualNumberUnitsEnemy;
    
    public int choiceAttackerUnit(int attacker) {
    	int[] probability;
    	if (attacker == 0) {
    		probability = CHANCE_ATTACK_CIVILIZATION_UNITS;
    	} else {
    		probability = CHANCE_ATTACK_ENEMY_UNITS;
    	}
    	int randomNum = (int) (Math.random() * (1 - 100) + 100);
    	int cumulativeProbability = 0;
    	for (int i = 0; i < probability.length; i++) {
    		cumulativeProbability += probability[i];
    		if (randomNum < cumulativeProbability) {
    			return i;
    		}
    	}
    	return probability.length - 1;
    }
    
    public int choiceDefenseUnit(int defender) {
    	int[] probability;
    	int total_units;
    	if (defender == 0) {
    		probability = new int[9];
    		total_units = this.actualNumberUnitsCivilization;
    		
    	} else {
    		probability = new int[4];
    		total_units = this.actualNumberUnitsEnemy;
    	}
    	for (int i = 0; i < probability.length; i++) {
			probability[i] = (int) (100 * this.armies[defender][i].size() / total_units);
		}
    	
    	int randomNum = (int) (Math.random() * (1 - 100) + 100);
    	int cumulativeProbability = 0;
    	for (int i = 0; i < probability.length; i++) {
    		cumulativeProbability += probability[i];
    		if (randomNum < cumulativeProbability) {
    			return i;
    		}
    	}
    	return probability.length - 1;
    }
    
    public void combat() {
    	int attacker;
    	int defender;
    	if ((int) (Math.random() * (1 - 100) + 100) == 0) {
    		attacker = 0;
    		defender = 1;
    	} else {
    		attacker = 1;
    		defender = 0;
    	}
    	
    	int attackUnit = choiceAttackerUnit(attacker);
    	
    	MilitaryUnit unitAttacking = this.armies[attacker][attackUnit].get(0);
    	
    	if (unitAttacking instanceof AttackUnit) {
    		AttackUnit unitAttackingCheck = (AttackUnit) unitAttacking;
    	} else if (unitAttacking instanceof DefenseUnit) {
    		DefenseUnit unitAttackingCheck = (DefenseUnit) unitAttacking;
    	} else if (unitAttacking instanceof SpecialUnit) {
    		SpecialUnit unitAttackingCheck = (SpecialUnit) unitAttacking;
    	}
    	
    	int defenseUnit = choiceDefenseUnit(defender);
    	
    	MilitaryUnit unitDefendig = this.armies[defender][defenseUnit].get(0);
    	
    	if (unitDefendig instanceof AttackUnit) {
    		AttackUnit unitDefendigCheck = (AttackUnit) unitDefendig;
    	} else if (unitDefendig instanceof DefenseUnit) {
    		DefenseUnit unitDefendigCheck = (DefenseUnit) unitDefendig;
    	} else if (unitDefendig instanceof SpecialUnit) {
    		SpecialUnit unitDefendigCheck = (SpecialUnit) unitDefendig;
    	}
    }
    
	public ArrayList<MilitaryUnit>[] getCivilizationArmy() {
		return civilizationArmy;
	}
	public void setCivilizationArmy(ArrayList<MilitaryUnit>[] civilizationArmy) {
		this.civilizationArmy = civilizationArmy;
	}
	public ArrayList<MilitaryUnit>[] getEnemyArmy() {
		return enemyArmy;
	}
	public void setEnemyArmy(ArrayList<MilitaryUnit>[] enemyArmy) {
		this.enemyArmy = enemyArmy;
	}
	public ArrayList<MilitaryUnit>[][] getArmies() {
		return armies;
	}
	public void setArmies(ArrayList<MilitaryUnit>[][] armies) {
		this.armies = armies;
	}
	public String getBattleDevelopment() {
		return battleDevelopment;
	}
	public void setBattleDevelopment(String battleDevelopment) {
		this.battleDevelopment = battleDevelopment;
	}
	public int getInitialCostFleet() {
		return initialCostFleet;
	}
	public void setInitialCostFleet(int initialCostFleet) {
		this.initialCostFleet = initialCostFleet;
	}
	public int getInitialNumberUnitsCivilization() {
		return initialNumberUnitsCivilization;
	}
	public void setInitialNumberUnitsCivilization(int initialNumberUnitsCivilization) {
		this.initialNumberUnitsCivilization = initialNumberUnitsCivilization;
	}
	public int getInitialNumberUnitsEnemy() {
		return initialNumberUnitsEnemy;
	}
	public void setInitialNumberUnitsEnemy(int initialNumberUnitsEnemy) {
		this.initialNumberUnitsEnemy = initialNumberUnitsEnemy;
	}
	public int getWasteWoodIron() {
		return wasteWoodIron;
	}
	public void setWasteWoodIron(int wasteWoodIron) {
		this.wasteWoodIron = wasteWoodIron;
	}
	public int getEnemyDrops() {
		return enemyDrops;
	}
	public void setEnemyDrops(int enemyDrops) {
		this.enemyDrops = enemyDrops;
	}
	public int getCivilizationDrops() {
		return civilizationDrops;
	}
	public void setCivilizationDrops(int civilizationDrops) {
		this.civilizationDrops = civilizationDrops;
	}
	public int getResourcesLooses() {
		return resourcesLooses;
	}
	public void setResourcesLooses(int resourcesLooses) {
		this.resourcesLooses = resourcesLooses;
	}
	public int getInitialArmies() {
		return initialArmies;
	}
	public void setInitialArmies(int initialArmies) {
		this.initialArmies = initialArmies;
	}
	public int getActualNumberUnitsCivilization() {
		return actualNumberUnitsCivilization;
	}
	public void setActualNumberUnitsCivilization(int actualNumberUnitsCivilization) {
		this.actualNumberUnitsCivilization = actualNumberUnitsCivilization;
	}
	public int getActualNumberUnitsEnemy() {
		return actualNumberUnitsEnemy;
	}
	public void setActualNumberUnitsEnemy(int actualNumberUnitsEnemy) {
		this.actualNumberUnitsEnemy = actualNumberUnitsEnemy;
	}
    
}
