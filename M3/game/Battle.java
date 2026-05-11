package M3.game;

import java.util.ArrayList;

import M3.interfaces.MilitaryUnit;
import M3.interfaces.Variables;
import M3.units.AttackUnit;
import M3.units.DefenseUnit;
import M3.units.SpecialUnit;
import M3.units.attack.Swordsman;

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
    
    

	public Battle(ArrayList<MilitaryUnit>[] civilizationArmy, ArrayList<MilitaryUnit>[] enemyArmy) {
		this.civilizationArmy = civilizationArmy;
		this.enemyArmy = enemyArmy;
		this.armies[0] = civilizationArmy;
		this.armies[1] = enemyArmy;
		this.initialNumberUnitsCivilization = 20;
		this.initialNumberUnitsEnemy = 20;
	}

	public int choiceAttackerUnit(int attacker) {
    	int[] probability;
    	if (attacker == 0) {
    		probability = CHANCE_ATTACK_CIVILIZATION_UNITS;
    	} else {
    		probability = CHANCE_ATTACK_ENEMY_UNITS;
    	}
    	while (true) {
    		int randomNum = (int) (Math.random() * 100);
        	int cumulativeProbability = 0;
        	int chosenUnit = probability.length - 1;
        	for (int i = 0; i < probability.length; i++) {
        		cumulativeProbability += probability[i];
        		if (randomNum < cumulativeProbability) {
        			chosenUnit = i;
        			break;
        		}
        	}
        	if(this.armies[attacker][chosenUnit] != null && !this.armies[attacker][chosenUnit].isEmpty()) {
        		return chosenUnit;
        	}
    	}
    }
    
    public int choiceDefenseUnit(int defender) {
    	int[] probability;
    	int total_units;
    	int porcentaje = 0;
    	if (defender == 0) {
    		probability = new int[9];
    		total_units = this.actualNumberUnitsCivilization;
    		
    	} else {
    		probability = new int[4];
    		total_units = this.actualNumberUnitsEnemy;
    	}
    	
    	for (int i = 0; i < probability.length; i++) {
    		if (this.armies[defender][i] != null) {
    			probability[i] = (int) ((100 * this.armies[defender][i].size()) / total_units);
    			if (probability[i] == 0) {
    				probability[i] = 1;
    			}
    			porcentaje += probability[i];
    		}
		}
    	while (true) {
    		int randomNum = (int) (Math.random() * (1 - porcentaje) + porcentaje);
        	int cumulativeProbability = 0;
        	int chosenUnit = probability.length - 1;
        	for (int i = 0; i < probability.length; i++) {
        		cumulativeProbability += probability[i];
        		if (randomNum < cumulativeProbability) {
        			chosenUnit = i;
        			break;
        		}
        	}
        	if(this.armies[defender][chosenUnit] != null && !this.armies[defender][chosenUnit].isEmpty()) {
    			return chosenUnit;
        	}
    	}
    }
    
    public void combat() {
    	countUnits();
    	int attacker;
    	int defender;
    	String attacker_name;
    	String defender_name;
    	if ((int) (Math.random() * (0 - 2) + 2) == 0) {
    		attacker = 0;
    		defender = 1;
    		attacker_name = "Jugador";
    		defender_name = "Enemigo";
    	} else {
    		attacker = 1;
    		defender = 0;
    		attacker_name = "Enemigo";
    		defender_name = "Jugador";
    	}
    	
    	int attackUnit = choiceAttackerUnit(attacker);
    	MilitaryUnit unitAttacking = this.armies[attacker][attackUnit].get(0);
    	
    	int defenseUnit = choiceDefenseUnit(defender);
    	MilitaryUnit unitDefending = this.armies[defender][defenseUnit].get(0);
    	
    	unitDefending.setArmor(unitDefending.getActualArmor() - unitAttacking.attack());
    	System.out.println("--------------------------------------------------------------------------");
    	System.out.println(attacker_name + " ataca con " + this.armies[attacker][attackUnit].get(0).getClass().getSimpleName() + "\n" + defender_name + " defiende con " + 
    			this.armies[defender][defenseUnit].get(0).getClass().getSimpleName());
    	System.out.println("--------------------------------------------------------------------------");
    	
    	if (unitDefending.getActualArmor() <= 0) {
    		System.out.println("##########################################################################");
    		System.out.println(this.armies[defender][defenseUnit].get(0).getClass().getSimpleName() + " de " + defender_name + " muere");
    		this.armies[defender][defenseUnit].remove(0);
    		System.out.println("##########################################################################");
    	}
    	
    	countUnits();
    	
    }
    
    public void countUnits() {
    	
    	int count = 0;
    	for (int i = 0; i < this.civilizationArmy.length; i++) {
    		if (this.civilizationArmy[i] != null) {
    			for (int j = 0; j < this.civilizationArmy[i].size(); j++) {
        			count += 1;
        		}
    		}
			
    	}
    	this.actualNumberUnitsCivilization = count;
    	
    	count = 0;
    	for (int i = 0; i < this.enemyArmy.length; i++) {
    		if (this.enemyArmy[i] != null) {
    			for (int j = 0; j < this.enemyArmy[i].size(); j++) {
        			count += 1;
        		}
    		}
			
    	}
    	this.actualNumberUnitsEnemy = count;
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
