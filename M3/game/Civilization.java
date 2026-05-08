package game;


import exceptions.*;
import interfaces.MilitaryUnit;
import units.attack.Cannon;
import units.attack.Crossbow;
import units.attack.Spearman;
import units.attack.Swordsman;
import units.defense.ArrowTower;
import units.defense.Catapult;
import units.defense.RocketLauncherTower;
import units.special.Magician;
import units.special.Priest;
import exceptions.ResourceException;

import java.util.ArrayList;

import interfaces.MilitaryUnit;
import interfaces.Variables;

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
	private int technologyAttack;
	
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
		this.wood = CIVILIZATION_WOOD_GENERATED;
		this.iron = CIVILIZATION_IRON_GENERATED;
		this.food = CIVILIZATION_FOOD_GENERATED;
		this.mana = 0;
		this.magicTower = 0;
		this.church = 0;
		this.farm = 0;
		this.smithy = 0;
		this.carpentry = 0;
		this.battles = 0;
	}
	
	public int getTechnologyDefense() {
		return technologyDefense;
	}

	public void setTechnologyDefense(int technologyDefense) {
		this.technologyDefense = technologyDefense;
	}

	public int getTechnologyAttack() {
		return technologyAttack;
	}

	public void setTechnologyAttack(int technologyAttack) {
		this.technologyAttack = technologyAttack;
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

	public int getFarm() {
		return farm;
	}

	public void setFarm(int farm) {
		this.farm = farm;
	}

	public int getSmithy() {
		return smithy;
	}

	public void setSmithy(int smithy) {
		this.smithy = smithy;
	}

	public int getCarpentry() {
		return carpentry;
	}

	public void setCarpentry(int carpentry) {
		this.carpentry = carpentry;
	}

	public ArrayList<MilitaryUnit>[] getArmy() {
		return this.army;
	}

	public void newChurch() throws ResourceException {
		if (this.wood >= WOOD_COST_CHURCH && this.iron >= IRON_COST_CHURCH && this.food >= FOOD_COST_CHURCH) {
			this.church += 1;
			takeMaterialBuildingCost(WOOD_COST_CHURCH, IRON_COST_CHURCH, FOOD_COST_CHURCH);
		} else {
			throw new ResourceException("Not enough material to create a church");
		}
	}

	public void newMagicTower() throws ResourceException {
		if (this.wood >= WOOD_COST_MAGICTOWER && this.iron >= IRON_COST_MAGICTOWER && this.food >= FOOD_COST_MAGICTOWER) {
			this.magicTower += 1;
			takeMaterialBuildingCost(WOOD_COST_MAGICTOWER, IRON_COST_MAGICTOWER, FOOD_COST_MAGICTOWER);
		} else {
			throw new ResourceException("Not enough material to create a magic tower");
		}
	}

	public void newFarm() throws ResourceException {
		if (this.wood >= WOOD_COST_FARM && this.iron >= IRON_COST_FARM && this.food >= FOOD_COST_FARM) {
			this.farm += 1;
			takeMaterialBuildingCost(WOOD_COST_FARM, IRON_COST_FARM, FOOD_COST_FARM);
		} else {
			throw new ResourceException("Not enough material to create a farm");
		}
	}

	public void newCarpentry() throws ResourceException {
		if (this.wood >= WOOD_COST_CARPENTRY && this.iron >= IRON_COST_CARPENTRY && this.food >= FOOD_COST_CARPENTRY) {
			this.carpentry += 1;
			takeMaterialBuildingCost(WOOD_COST_CARPENTRY, IRON_COST_CARPENTRY, FOOD_COST_CARPENTRY);
		} else {
			throw new ResourceException("Not enough material to create a carpentry");
		}
	}

	public void newSmithy() throws ResourceException {
		if (this.wood >= WOOD_COST_SMITHY && this.iron >= IRON_COST_SMITHY && this.food >= FOOD_COST_SMITHY) {
			this.smithy += 1;
			takeMaterialBuildingCost(WOOD_COST_SMITHY, IRON_COST_SMITHY, FOOD_COST_SMITHY);
		} else {
			throw new ResourceException("Not enough material to create a smithy");
		}
	}
	
	public void upgradeTechnologyDefense() throws ResourceException {
		int wood_cost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST + 
				((int) (this.technologyDefense - 1) * (UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST * UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST / 100));
		int iron_cost = UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST + 
				((int) (this.technologyDefense - 1) * (UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST * UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST / 100));
		if (this.iron >= iron_cost && this.wood >= wood_cost) {
			this.technologyDefense += 1;
			takeMaterialBuildingCost(wood_cost, iron_cost, 0);
		} else {
			throw new ResourceException("Not enough material to upgrade technology defense");
		}
	}

	public void upgradeTechnologyAttack() throws ResourceException {
		int wood_cost = UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST + 
				((int) (this.technologyAttack - 1) * (UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST * UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST / 100));
		int iron_cost = UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST + 
				((int) (this.technologyAttack - 1) * (UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST * UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST / 100));
		if (this.iron >= iron_cost && this.wood >= wood_cost) {
			this.technologyAttack += 1;
			takeMaterialBuildingCost(wood_cost, iron_cost, 0);
		} else {
			throw new ResourceException("Not enough material to upgrade technology attack");
		}
	}
	
	private void takeMaterialBuildingCost(int wood, int iron, int food) {
		this.wood -= wood;
		this.iron -= iron;
		this.food -= food;
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
			if (checkCostsUnits(new ArrowTower(this.technologyDefense, this.technologyAttack), unidades)) {
				if (this.army[4] == null) {
					army[4] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[4].add(new ArrowTower(this.technologyDefense, this.technologyAttack));
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
						this.army[4].add(new ArrowTower(this.technologyDefense, this.technologyAttack));
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
			if (checkCostsUnits(new Catapult(this.technologyDefense, this.technologyAttack), unidades)) {
				if (this.army[5] == null) {
					army[5] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[5].add(new Catapult(this.technologyDefense, this.technologyAttack));
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
						this.army[5].add(new Catapult(this.technologyDefense, this.technologyAttack));
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
			if (checkCostsUnits(new RocketLauncherTower(this.technologyDefense, this.technologyAttack), unidades)) {
				if (this.army[6] == null) {
					army[6] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[6].add(new RocketLauncherTower(this.technologyDefense, this.technologyAttack));
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
						this.army[6].add(new RocketLauncherTower(this.technologyDefense, this.technologyAttack));
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
			if (checkCostsUnits(new Magician(this.technologyAttack), unidades)) {
				if (this.army[7] == null) {
					army[7] = new ArrayList<MilitaryUnit>();
					for (int i = 0; i < n; i++) {
						this.army[7].add(new Magician(this.technologyAttack));
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
						this.army[7].add(new Magician(this.technologyAttack));
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
