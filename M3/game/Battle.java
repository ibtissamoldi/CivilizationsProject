package M3.game;

import java.util.ArrayList;

import M3.interfaces.MilitaryUnit;

public class Battle {
	
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
    
}
