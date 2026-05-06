package M3.game;

import M3.exceptions.*;
import M3.interfaces.MilitaryUnit;
import M3.units.attack.Cannon;
import M3.units.attack.Crossbow;
import M3.units.attack.Spearman;
import M3.units.attack.Swordsman;
import M3.units.defense.ArrowTower;
import M3.units.defense.Catapult;
import M3.units.defense.RocketLauncherTower;
import M3.units.special.Magician;
import M3.units.special.Priest;
import java.util.ArrayList;

public class Civilization {
    private int technologyDefense;
	private int technologyAtack;
	
	private int wood;
	private int iron;
	private int food;
	private int mana;

	private int magicTower;
	private int church;
	private int farm;
	private int smithy;
	private int carpentry;

	private int battles;

	private ArrayList<MilitaryUnit>[] army = new ArrayList[9];

	public Civilization() {

	}

	public ArrayList<MilitaryUnit>[] getArmy() {
		return this.army;
	}

	public void newChurch() {
		this.church += 1;
	}

	public void newMagicTower() {
		this.magicTower += 1;
	}

	public void newFarm() {
		this.farm += 1;
	}

	public void newCarpentry() {
		this.carpentry += 1;
	}

	public void newSmithy() {
		this.smithy += 1;
	}

	public void upgradeTechnologyDefense() {
		this.technologyDefense += 1;
	}

	public void upgradeTechnologyAttack() {
		this.technologyAtack += 1;
	}

	public void newSwordsman(int n) throws BuildingException{
		if (checkCosts(new Swordsman(), n)){
			if (this.army[0] == null) {
			army[0] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[0].add(new Swordsman());
			}
			} else {
				for (int i = 0; i < n; i++) {
					this.army[0].add(new Swordsman());
				}
			}
		} else {
			throw new BuildingException("Hola");
		}
		
	}

	public void newSpearman(int n) {
		if (this.army[1] == null) {
			army[1] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[1].add(new Spearman());
			}

		} else {
			this.army[1].add(new Spearman());
		}
	}

	public void newCrossbow(int n) {
		if (this.army[2] == null) {
			army[2] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[2].add(new Crossbow());
			}
		} else {
			this.army[2].add(new Crossbow());
		}
	}

	public void newCannon(int n) {
		if (this.army[3] == null) {
			army[3] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[3].add(new Cannon());
			}
		} else {
			this.army[3].add(new Cannon());
		}
	}

	public void newArrowTower(int n) {
		if (this.army[4] == null) {
			army[4] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[4].add(new ArrowTower(this.technologyDefense, this.technologyAtack));
			}
		} else {
			this.army[4].add(new ArrowTower(this.technologyDefense, this.technologyAtack));
		}
	}

	public void newCatapult(int n) {
		if (this.army[5] == null) {
			army[5] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[5].add(new Catapult(this.technologyDefense, this.technologyAtack));
			}
		} else {
			this.army[5].add(new Catapult(this.technologyDefense, this.technologyAtack));
		}
	}

	public void newRocketLauncher(int n) {
		if (this.army[6] == null) {
			army[6] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[6].add(new RocketLauncherTower(this.technologyDefense, this.technologyAtack));
			}
		} else {
			this.army[6].add(new RocketLauncherTower(this.technologyDefense, this.technologyAtack));
		}
	}

	public void newMagician(int n) {
		if (this.army[7] == null) {
			army[7] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[7].add(new Magician(this.technologyAtack));
			}
		} else {
			this.army[7].add(new Magician(this.technologyAtack));
		}
	}

	public void newPriest(int n) {
		if (this.army[8] == null) {
			army[8] = new ArrayList<MilitaryUnit>();
			for (int i = 0; i < n; i++) {
				this.army[8].add(new Priest());
			}
		} else {
			this.army[8].add(new Priest());
		}
	}

	public boolean checkCosts(MilitaryUnit m, int n) {
		if (m.getFoodCost() * n <= this.food) {
			return true;
		} else {
			return false;
		}
	}

	public void printStats() {
		
	}
}
