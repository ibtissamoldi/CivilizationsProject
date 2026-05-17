package M3.game;

import java.util.ArrayList;

import M3.exceptions.ResourceException;
import M3.interfaces.MilitaryUnit;
import M3.interfaces.Variables;
import M3.units.AttackUnit;
import M3.units.DefenseUnit;
import M3.units.SpecialUnit;
import M3.units.attack.*;
import M3.units.defense.*;
import M3.units.special.*;

public class Battle implements Variables{
	
	private String civil_name;
	private String enemy_name;
    private ArrayList<MilitaryUnit>[] civilizationArmy = new ArrayList[9];
    private ArrayList<MilitaryUnit>[] enemyArmy = new ArrayList[4];
    private ArrayList<MilitaryUnit>[][] armies = new ArrayList[2][9];
    
    private MilitaryUnit currentAttacker;
    private MilitaryUnit currentDefender;
    private boolean civilizationTurn;
    
    private int firstAttacker = -1;
    private int[][] unitsLooses = new int[2][9];
    private String battleDevelopment;
    private String reportStepStep = "";
    private int initialCostFleet;
    private int initialNumberUnitsCivilization, initialNumberUnitsEnemy;
    private int[] wasteWoodIron = new int[2];
    private int enemyDrops;
    private int civilizationDrops;
    private int[][] resourcesLooses = new int[2][4];
    private int initialArmies;
    private int actualNumberUnitsCivilization;
    private int actualNumberUnitsEnemy;
    private int lastUnitEnemy = -1;

	public Battle(ArrayList<MilitaryUnit>[] civilizationArmy, ArrayList<MilitaryUnit>[] enemyArmy, String civil_name, String enemy_name) {
		this.civil_name = civil_name;
		this.enemy_name = enemy_name;
		this.civilizationArmy = civilizationArmy;
		this.enemyArmy = enemyArmy;
		this.armies[0] = civilizationArmy;
		this.armies[1] = enemyArmy;
		int count = 0;
		for (int i = 0; i < this.civilizationArmy.length; i++) {
    		if (this.civilizationArmy[i] != null) {
    			for (int j = 0; j < this.civilizationArmy[i].size(); j++) {
        			count += 1;
        		}
    		}
    	}
		this.initialNumberUnitsCivilization = count;
		count = 0;
    	for (int i = 0; i < this.enemyArmy.length; i++) {
    		if (this.enemyArmy[i] != null) {
    			for (int j = 0; j < this.enemyArmy[i].size(); j++) {
        			count += 1;
        		}
    		}
    	}
		this.initialNumberUnitsEnemy = count;
		countUnits();
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
        	int chosenUnit = -1;
        	for (int i = 0; i < probability.length; i++) {
        		cumulativeProbability += probability[i];
        		if (randomNum < cumulativeProbability) {
        			chosenUnit = i;
        			break;
        		}
        	}
        	if(chosenUnit != -1 && this.armies[attacker][chosenUnit] != null && !this.armies[attacker][chosenUnit].isEmpty()) {
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
    	int attacker;
        int defender;
        String attacker_name;
        String defender_name;
        if (this.firstAttacker == -1) {
            this.firstAttacker = (int) (Math.random() * 2);
        } else {
            if (this.firstAttacker == 0) {
                this.firstAttacker = 1;
            } else {
                this.firstAttacker = 0;
            }
        }
        attacker = this.firstAttacker;
        
        if (attacker == 0) {
            defender = 1;
            attacker_name = this.civil_name;
            defender_name = this.enemy_name;
        } else {
            defender = 0;
            attacker_name = this.enemy_name;
            defender_name = this.civil_name;
        }
		
    	String step_report = "";
    	
    	if (attacker == 0) {
    		attacker_name = this.civil_name;
    		defender_name = this.enemy_name;
    	} else {
    		attacker_name = this.enemy_name;
    		defender_name = this.civil_name;
    	}
    	
    	int attackUnit = choiceAttackerUnit(attacker);
    	int indexAttackingUnit = (int) (Math.random() * (0 - this.armies[attacker][attackUnit].size()) + this.armies[attacker][attackUnit].size());
    	MilitaryUnit unitAttacking = this.armies[attacker][attackUnit].get(indexAttackingUnit);
    	
    	step_report += "\n********************CHANGE ATTACKER********************\n";
    	
    	do {
    		int defenseUnit = choiceDefenseUnit(defender);
    		int indexDefendingUnit = (int) (Math.random() * (0 - this.armies[defender][defenseUnit].size()) + this.armies[defender][defenseUnit].size());
        	MilitaryUnit unitDefending = this.armies[defender][defenseUnit].get(indexDefendingUnit);
        	
        	currentAttacker = unitAttacking;
        	currentDefender = unitDefending;
        	if (attacker == 0) {
        	    civilizationTurn = true;
        	} else {
        	    civilizationTurn = false;
        	}
        	
        	if (attacker == 0 && this.armies[0][8] != null && !this.armies[0][8].isEmpty()) {
        		if (unitAttacking instanceof AttackUnit) {
        			((AttackUnit) unitAttacking).isSanctified();
        		} else if (unitAttacking instanceof DefenseUnit) {
        			((DefenseUnit) unitAttacking).isSanctified();
        		}
        	}
    		unitDefending.setArmor(unitDefending.getActualArmor() - unitAttacking.attack());
    		step_report += "Attacks " + attacker_name + ": " + unitAttacking.getClass().getSimpleName() + " attacks " + unitDefending.getClass().getSimpleName() + 
    						"\n" + unitAttacking.getClass().getSimpleName() + " damage = "  + unitAttacking.attack() + 
    						"\n" + unitDefending.getClass().getSimpleName() + " stay with armor: " + unitDefending.getActualArmor() + "\n";
    		if (unitDefending.getActualArmor() <= 0) {
        		step_report += attacker_name + " eliminates " + unitDefending.getClass().getSimpleName() + "\n";
        		this.armies[defender][defenseUnit].remove(indexDefendingUnit);
        		currentDefender = null;
                countUnits();
        		updateUnitsLooses(defender, defenseUnit);
        		updateResourcesLooses(defender, unitDefending);
        		if ((int) (Math.random() * 100) <= unitAttacking.getChanceGeneratinWaste()) {
        			this.wasteWoodIron[0] += unitAttacking.getWoodCost() * PERCENTATGE_WASTE / 100;
        			this.wasteWoodIron[1] += unitAttacking.getIronCost() * PERCENTATGE_WASTE / 100;
        		}
        	}
    		updateReportStepStep(step_report);
    		step_report = "";
    		
    	} while ((int) (Math.random() * 100) <= unitAttacking.getChanceAttackAgain() && !battleIsOver());
    	
    	setBattleDevelopment();
    	
    }
    
    public void updateResourcesLooses(int bando, MilitaryUnit unit) {
    	resourcesLooses[bando][0] += unit.getFoodCost();
    	resourcesLooses[bando][1] += unit.getWoodCost();
    	resourcesLooses[bando][2] += unit.getIronCost();
    	resourcesLooses[bando][3] += unit.getIronCost() + (int) (unit.getWoodCost() / 5) + (int) (unit.getFoodCost() / 10);
    }
    
    public boolean battleIsOver() {
    	if (this.actualNumberUnitsCivilization > (int) (initialNumberUnitsCivilization) * 0.2 
    			&& this.actualNumberUnitsEnemy > (int) (initialNumberUnitsEnemy * 0.20)) {
    		return false;
    	} else {
    		return true;
    	}
    }
    
    public void updateUnitsLooses(int bando, int unit) {
    	this.unitsLooses[bando][unit] += 1;
    }
    
    public void updateReportStepStep(String newReport) {
    	this.reportStepStep += newReport;
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
    
 
    
    
	public String getReportStepStep() {
		return reportStepStep;
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
	public void setBattleDevelopment() {
		String row = String.format("\n%-25s%-9s%-9s%-25s%9s%9s\n", this.civil_name + " army", "Units", "Drops", "Initial army " + this.enemy_name, "Units" , "Drops");
    	for (int i = 0; i < this.unitsLooses[0].length; i++) {
    		if (this.civilizationArmy[i] != null) {
    			row += String.format("\n%-25s%5d%9d    ", UNITS_NAMES[i], this.civilizationArmy[i].size(), this.unitsLooses[0][i]);
    		} else {
    			row += String.format("\n%-25s%5d%9d    ", UNITS_NAMES[i], 0, this.unitsLooses[0][i]);
    		}
    		if (i <= 3) {
    			row += String.format("%-25s%9d%9d\n", UNITS_NAMES[i], this.enemyArmy[i].size(), this.unitsLooses[1][i]);
    		} else {
    			row += "\n";
    		}
    	}
    	row += "*".repeat(86) + "\n" + String.format("%-38s%-38s", "Cost Army Civilization", "Cost Army Enemy") + "\n\n";
    	row += String.format("%-38s%-38s", String.format("Food:%15d", this.resourcesLooses[0][0]), String.format("Food:%15d", this.resourcesLooses[1][0])) + "\n";
    	row += String.format("%-38s%-38s", String.format("Wood:%15d", this.resourcesLooses[0][1]), String.format("Wood:%15d", this.resourcesLooses[1][1])) + "\n";
    	row += String.format("%-38s%-38s", String.format("Iron:%15d", this.resourcesLooses[0][2]), String.format("Iron:%15d", this.resourcesLooses[1][2])) + "\n";
    	
    	//NO SE DE DONDE SALEN LOS DATOS ------ PENDIENTE DE REVISÓN
    	row += "\n" + "*".repeat(86) + "\n" + String.format("%-38s%-38s", "Losses Army Civilization", "Losses Army Enemy") + "\n\n";
    	row += String.format("%-38s%-38s", String.format("Food:%15d", this.resourcesLooses[0][0]), String.format("Food:%15d", this.resourcesLooses[1][0])) + "\n";
    	row += String.format("%-38s%-38s", String.format("Wood:%15d", this.resourcesLooses[0][1]), String.format("Wood:%15d", this.resourcesLooses[1][1])) + "\n";
    	row += String.format("%-38s%-38s", String.format("Iron:%15d", this.resourcesLooses[0][2]), String.format("Iron:%15d", this.resourcesLooses[1][2])) + "\n";
    	
    	row += "\n" + "*".repeat(86) + "\n" + String.format("%-38s", "Waste Generated") + "\n\n";
    	row += String.format("%-38s", String.format("Wood:%15d", this.wasteWoodIron[0])) + "\n";
    	row += String.format("%-38s", String.format("Iron:%15d", this.wasteWoodIron[1])) + "\n";
    	
    	if (this.resourcesLooses[0][3] < this.resourcesLooses[1][3]) {
    		row += "\n" + "Battle Winned by " + civil_name + ", We Collect Rubble";
    		
    	} else {
    		row += "\n" + "Battle Winned by " + enemy_name + ", We don't Collect Rubble";
    	}
    	this.battleDevelopment = row;
	}
	
	public void generateEnemyUnits(Civilization enemy) {
		int[] final_chance = CHANCE_GENERATE_ENEMY_UNITS;
		if (this.actualNumberUnitsCivilization < this.actualNumberUnitsEnemy) {
			final_chance[0] -= 30;
			final_chance[3] += 30;
		} else if (enemy.getFood() < 20000 && enemy.getMana() < 20000 && enemy.getWood() < 20000 && enemy.getIron() < 20000) {
			final_chance[0] += 40;
			final_chance[1] -= 20;
			final_chance[2] -= 15;
			final_chance[3] -= 5;
		} else if (this.lastUnitEnemy != -1) {
			for (int i = 0; i < final_chance.length; i++) {
				if(i == this.lastUnitEnemy) {
					final_chance[this.lastUnitEnemy] -= 9; 
				} else {
					final_chance[i] += 3;
				}
			}
		}
		
		int randomNum = (int) (Math.random() * 100);
		
    	if (randomNum <= final_chance[0]) {
    		try {
				enemy.newSwordsman(99);
			} catch (ResourceException e) {
				e.printStackTrace();
			}
    	} else if (randomNum <= final_chance[0] + final_chance[1]) {
    		try {
				enemy.newSpearman(99);
			} catch (ResourceException e) {
				e.printStackTrace();
				if (e.getMessage().contains("99")) {
					try {
						enemy.newSwordsman(99);
					} catch (ResourceException ee) {
						ee.printStackTrace();
					}
				}
			}
    	} else if (randomNum <= final_chance[0] + final_chance[1] + final_chance[2]) {
    		try {
				enemy.newCrossbow(99);
			} catch (ResourceException e) {
				e.printStackTrace();
				if (e.getMessage().contains("99")) {
					try {
						enemy.newSwordsman(99);
					} catch (ResourceException ee) {
						ee.printStackTrace();
					}
				}
			}
    	} else {
    		try {
				enemy.newCannon(99);
			} catch (ResourceException e) {
				e.printStackTrace();
				if (e.getMessage().contains("99")) {
					try {
						enemy.newSwordsman(99);
					} catch (ResourceException ee) {
						ee.printStackTrace();
					}
				}
			}
    	}
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
	public int[] getWasteWoodIron() {
		return wasteWoodIron;
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
	public int[][] getResourcesLooses() {
		return resourcesLooses;
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
	public MilitaryUnit getCurrentAttacker() {
	    return currentAttacker;
	}

	public MilitaryUnit getCurrentDefender() {
	    return currentDefender;
	}

	public boolean isCivilizationTurn() {
	    return civilizationTurn;
	}
}
