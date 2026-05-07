package M3.game;

import M3.exceptions.*;
import M3.interfaces.MilitaryUnit;
import M3.interfaces.Variables;
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

public class Civilization implements Variables{
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

	public int getWood() {
		return wood;
	}

	public void setWood(int wood) {
		this.wood = wood;
	}

	public int getIron() {
		return iron;
	}

	public void setIron(int iron) {
		this.iron = iron;
	}

	public int getFood() {
		return food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public ArrayList<MilitaryUnit>[] getArmy() {
		return this.army;
	}

	public int getMagicTower() {
		return magicTower;
		
	}

	public void setMagicTower(int magicTower) {
		this.magicTower = magicTower;
	}

	public int getChurch() {
		return church;
	}

	public void setChurch(int church) {
		this.church = church;
	}

	public void newChurch() throws ResourceException {
		if (this.wood >= WOOD_COST_CHURCH && this.iron >= IRON_COST_CHURCH && this.food >= FOOD_COST_CHURCH) {
			this.church += 1;
		} else {
			throw new ResourceException("Not enough material to create a church");
		}
	}

	public void newMagicTower() throws ResourceException {
		if (this.wood >= WOOD_COST_MAGICTOWER && this.iron >= IRON_COST_MAGICTOWER && this.food >= FOOD_COST_MAGICTOWER) {
			this.magicTower += 1;
		} else {
			throw new ResourceException("Not enough material to create a magic tower");
		}
	}

	public void newFarm() throws ResourceException {
		if (this.wood >= WOOD_COST_FARM && this.iron >= IRON_COST_FARM && this.food >= FOOD_COST_FARM) {
			this.farm += 1;
		} else {
			throw new ResourceException("Not enough material to create a farm");
		}
	}

	public void newCarpentry() throws ResourceException {
		if (this.wood >= WOOD_COST_CARPENTRY && this.iron >= IRON_COST_CARPENTRY && this.food >= FOOD_COST_CARPENTRY) {
			this.carpentry += 1;
		} else {
			throw new ResourceException("Not enough material to create a carpentry");
		}
	}

	public void newSmithy() throws ResourceException {
		if (this.wood >= WOOD_COST_SMITHY && this.iron >= IRON_COST_SMITHY && this.food >= FOOD_COST_SMITHY) {
			this.smithy += 1;
		} else {
			throw new ResourceException("Not enough material to create a smithy");
		}
	}
	
	public void upgradeTechnologyDefense() throws ResourceException {
		
		this.technologyDefense += 1;
	}

	public void upgradeTechnologyAttack() throws ResourceException {
		this.technologyAtack += 1;
	}

	public void newSwordsman(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new Swordsman(), unidades)) {
				if (this.army[0] == null) {
					army[0] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[0].add(new Swordsman());
					}
					if (unidades > 1) {
						System.out.println(unidades + " swordsmen successfully created");
					} else {
						System.out.println(unidades + " swordsman successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " swordsmen");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[0].add(new Swordsman());
					}
					if (unidades > 1) {
						System.out.println(unidades + " swordsmen successfully created");
					} else {
						System.out.println(unidades + " swordsman successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " swordsmen");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " swordsmen");
	}

	public void newSpearman(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new Spearman(), unidades)) {
				if (this.army[1] == null) {
					army[1] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[1].add(new Spearman());
					}
					if (unidades > 1) {
						System.out.println(unidades + " spearmen successfully created");
					} else {
						System.out.println(unidades + " spearman successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " spearmen");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[1].add(new Spearman());
					}
					if (unidades > 1) {
						System.out.println(unidades + " spearmen successfully created");
					} else {
						System.out.println(unidades + " spearman successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " spearmen");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " spearmen");
	}

	public void newCrossbow(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new Crossbow(), unidades)) {
				if (this.army[2] == null) {
					army[2] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[2].add(new Crossbow());
					}
					if (unidades > 1) {
						System.out.println(unidades + " crossbows successfully created");
					} else {
						System.out.println(unidades + " crossbow successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " crossbow");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[2].add(new Crossbow());
					}
					if (unidades > 1) {
						System.out.println(unidades + " crossbows successfully created");
					} else {
						System.out.println(unidades + " crossbow successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " crossbow");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " crossbow");
	}

	public void newCannon(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new Cannon(), unidades)) {
				if (this.army[3] == null) {
					army[3] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[3].add(new Cannon());
					}
					if (unidades > 1) {
						System.out.println(unidades + " cannons successfully created");
					} else {
						System.out.println(unidades + " cannon successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " cannons");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[3].add(new Cannon());
					}
					if (unidades > 1) {
						System.out.println(unidades + " cannons successfully created");
					} else {
						System.out.println(unidades + " cannon successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " cannons");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " cannons");
	}

	public void newArrowTower(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new ArrowTower(this.technologyDefense, this.technologyAtack), unidades)) {
				if (this.army[4] == null) {
					army[4] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[4].add(new ArrowTower(this.technologyDefense, this.technologyAtack));
					}
					if (unidades > 1) {
						System.out.println(unidades + " arrow towers successfully created");
					} else {
						System.out.println(unidades + " arrow tower successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " arrow towers");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[4].add(new ArrowTower(this.technologyDefense, this.technologyAtack));
					}
					if (unidades > 1) {
						System.out.println(unidades + " arrow towers successfully created");
					} else {
						System.out.println(unidades + " arrow tower successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " arrow towers");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " arrow towers");
	}

	public void newCatapult(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new Catapult(this.technologyDefense, this.technologyAtack), unidades)) {
				if (this.army[5] == null) {
					army[5] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[5].add(new Catapult(this.technologyDefense, this.technologyAtack));
					}
					if (unidades > 1) {
						System.out.println(unidades + " catapults successfully created");
					} else {
						System.out.println(unidades + " catapult successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " catapults");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[5].add(new Catapult(this.technologyDefense, this.technologyAtack));
					}
					if (unidades > 1) {
						System.out.println(unidades + " catapults successfully created");
					} else {
						System.out.println(unidades + " catapult successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " catapults");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " catapults");
	}

	public void newRocketLauncher(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new RocketLauncherTower(this.technologyDefense, this.technologyAtack), unidades)) {
				if (this.army[6] == null) {
					army[6] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[6].add(new RocketLauncherTower(this.technologyDefense, this.technologyAtack));
					}
					if (unidades > 1) {
						System.out.println(unidades + " rocket launcher towers successfully created");
					} else {
						System.out.println(unidades + " rocket launcher tower successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " rocket launcher towers");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[6].add(new RocketLauncherTower(this.technologyDefense, this.technologyAtack));
					}
					if (unidades > 1) {
						System.out.println(unidades + " rocket launcher towers successfully created");
					} else {
						System.out.println(unidades + " rocket launcher tower successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " rocket launcher towers");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " rocket launcher towers");
	}

	public void newMagician(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new Magician(this.technologyAtack), unidades)) {
				if (this.army[7] == null) {
					army[7] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[7].add(new Magician(this.technologyAtack));
					}
					if (unidades > 1) {
						System.out.println(unidades + " magicians successfully created");
					} else {
						System.out.println(unidades + " magician successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " magicians");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[7].add(new Magician(this.technologyAtack));
					}
					if (unidades > 1) {
						System.out.println(unidades + " magicians successfully created");
					} else {
						System.out.println(unidades + " magician successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " magicians");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " magicians");
	}

	public void newPriest(int n) throws ResourceException {
		int contador = 0;
		for (int unidades = n; unidades > 0; unidades--) {
			if (checkCostsUnits(new Priest(), unidades)) {
				if (this.army[8] == null) {
					army[8] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[8].add(new Priest());
					}
					if (unidades > 1) {
						System.out.println(unidades + " priests successfully created");
					} else {
						System.out.println(unidades + " priest successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " priests");
					}
					return;
				} else {
					for (int i = 0; i < n; i++) {
						this.army[8].add(new Priest());
					}
					if (unidades > 1) {
						System.out.println(unidades + " priests successfully created");
					} else {
						System.out.println(unidades + " priest successfully created");
					}
					if (contador > 0) {
						throw new ResourceException("Not enough material to create " + contador + " priests");
					}
					return;
				}
			} else {
				contador++;
			}
		}
		throw new ResourceException("Not enough material to create " + contador + " priests");
	}

	public boolean checkCostsUnits(MilitaryUnit m, int n) throws ResourceException {
		if (m.getClass().equals(Magician.class)) {
			if (m.getFoodCost() * n <= this.food && 
					m.getIronCost() * n <= this.iron &&
					m.getManaCost() * n <= this.mana &&
					m.getWoodCost() * n <= this.wood &&
					n <= this.magicTower) {
				takeMaterialsForMilitaryUnit(m, n);
				return true;
			} else {
				return false;
			}
		} else if (m.getClass().equals(Priest.class)) {
			if (m.getFoodCost() * n <= this.food && 
					m.getIronCost() * n <= this.iron &&
					m.getManaCost() * n <= this.mana &&
					m.getWoodCost() * n <= this.wood &&
					n <= this.church) {
				takeMaterialsForMilitaryUnit(m, n);
				return true;
			} else {
				return false;
			}
		} else {
			if (m.getFoodCost() * n <= this.food && 
					m.getIronCost() * n <= this.iron &&
					m.getManaCost() * n <= this.mana &&
					m.getWoodCost() * n <= this.wood) {
				takeMaterialsForMilitaryUnit(m, n);
				return true;
			} else {
				return false;
			}
		}
		
	}
	
	private void takeMaterialsForMilitaryUnit(MilitaryUnit m, int n) {
		// wood iron food mana
		this.mana =  this.mana - m.getManaCost() * n;
		this.wood = this.wood - m.getWoodCost() * n;
		this.food = this.food - m.getFoodCost() * n;
		this.iron = this.iron - m.getIronCost() * n;
		
	}
	
	public void printStats() {
		
	}
}
