package M3;



import M3.exceptions.ResourceException;
import M3.game.Civilization;


public class Main {
    public static void main(String[] args) {
        Civilization civil = new Civilization();
        System.out.println(civil.getArmy()[0]);
        civil.setFood(10000000);
        civil.setWood(10000000);
        civil.setIron(10000000);
        civil.setMana(10000000);
        civil.setChurch(0);
        civil.setMagicTower(0);
        
        try {
			civil.newSwordsman(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newSpearman(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newCrossbow(1254);
			} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newArrowTower(3000);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newCannon(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newCatapult(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newMagician(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newPriest(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newSwordsman(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newSpearman(1254);;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newCrossbow(1254);;
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newArrowTower(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newCannon(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newCatapult(1254);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newMagician(900);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
        
        try {
			civil.newPriest(900);
		} catch (ResourceException e) {
			System.out.println(e.getMessage());
		}
    }
}
