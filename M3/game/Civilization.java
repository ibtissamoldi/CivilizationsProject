package M3.game;

import java.util.ArrayList;

import M3.interfaces.MilitaryUnit;

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

	
}
