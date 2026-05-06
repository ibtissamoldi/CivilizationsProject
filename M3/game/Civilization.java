package game;


import java.util.ArrayList;


import interfaces.MilitaryUnit;
import interfaces.Variables;

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

	
}
