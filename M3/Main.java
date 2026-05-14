package M3;

import M3.exceptions.ResourceException;
import M3.game.Battle;
import M3.game.Civilization;

public class Main {
	
    public static void main(String[] args) {
    	
		Civilization civil = new Civilization("Player");
        civil.setFood(10000000);
        civil.setWood(10000000);
        civil.setIron(10000000);
        civil.setMana(10000000);
        
        try {
        	civil.newChurch();
        } catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
        	civil.newMagicTower();
        } catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newSwordsman(4);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newSpearman(4);;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newCrossbow(4);;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newArrowTower(4);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newCannon(4);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newMagician(1);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newPriest(1);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        Civilization enemigo = new Civilization("Enemy");
        
        enemigo.setFood(10000000);
        enemigo.setWood(10000000);
        enemigo.setIron(10000000);
        enemigo.setMana(10000000);
        
        try {
			enemigo.newSwordsman(4);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			enemigo.newSpearman(4);;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			enemigo.newCrossbow(4);;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			enemigo.newArrowTower(4);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			enemigo.newCannon(4);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
    	
    	Battle batalla = new Battle(civil.getArmy(), enemigo.getArmy(), "PLAYER", "ENEMY");
    	
    	int porCivil = (int) (20 * 0.2);
    	int porEnemigo = (int) (20 * 0.2);
    	
    	batalla.countUnits();
    	while (!batalla.battleIsOver()) {
    		batalla.combat();
    	}
    	

    	System.out.println("\n\n\n\n\n" + batalla.getReportStepStep());
    	System.out.println(batalla.getBattleDevelopment());
    	
    	System.out.println("fin del programa");
    }
}
