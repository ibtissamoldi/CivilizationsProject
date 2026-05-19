package M3.GUIgame.Battlepanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import M3.Database.DBConnection;
import M3.GUIgame.GameColors;
import M3.GUIgame.GameLog;
import M3.GUIgame.MainFrame;
import M3.exceptions.ResourceException;
import M3.game.Battle;
import M3.game.Civilization;
import M3.interfaces.MilitaryUnit;
import M3.interfaces.Variables;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.FlowLayout;



public class MainBattlePanel extends JPanel implements Variables {
	
	private CenterFightPanel center;

	private Battle battle;

	private Civilization civ;
	private Civilization enemy;
	
	private UnitCardPanel[] civCards;
	private UnitCardPanel[] enemyCards;
	
	private BattlePanel battlePanel;
	
	private Timer timer;
	
	private JButton btnExit;
	
	private MainFrame frame;
	
	private int battleNumber;
	


	public MainBattlePanel(Civilization civ,BattlePanel battlePanel,MainFrame frame){
		this.civ = civ;
		this.battlePanel = battlePanel;
		this.frame = frame;
		this.battleNumber = civ.getBattles() + 1;
		
		System.out.println(
			    civ.getArmy()[0].size()
			);
		enemy = new Civilization("Enemy");
		
		enemy.enemy();

		enemy.generateEnemyArmy(civ);
		
		
		battle = new Battle(civ.getArmy(),enemy.getArmy(),civ.getName(),enemy.getName());

		
		
	    setLayout(new BorderLayout(10,10));
	    setBackground(GameColors.BACKGROUND);
 
	    
	    JPanel left = new JPanel(new GridLayout(3,3,10,10));
	    JScrollPane scroll = new JScrollPane(left);
	    scroll.setBorder(null);
	    scroll.getViewport().setBackground(GameColors.PANEL);
	    left.setPreferredSize(new Dimension(330, 0));
	    JPanel right = new JPanel(new GridLayout(0,1,15,15));
	    right.setPreferredSize(new Dimension(90, 0));
	    left.setBackground(GameColors.PANEL);
	    right.setBackground(GameColors.PANEL);

	    center = new CenterFightPanel();
	    center.setMinimumSize(new Dimension(400,0));

	    
	    btnExit = new JButton("×");
	    btnExit.setFont(new Font("Arial", Font.BOLD, 14));
	    btnExit.setMargin(new Insets(0,0,0,0));
	    btnExit.setBackground(GameColors.PANEL);
	    btnExit.setForeground(GameColors.FOOD);
	    btnExit.setContentAreaFilled(false);
	    btnExit.setFocusPainted(false);
	    btnExit.setOpaque(false);
	    btnExit.setBounds(370, 5, 25, 25);

	    btnExit.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent e) {

	            int option = JOptionPane.showConfirmDialog(
	                    null,
	                    "Exit current battle?",
	                    "Exit Battle",
	                    JOptionPane.YES_NO_OPTION
	            );

	            if(option == JOptionPane.YES_OPTION){

	                exitBattle();
	            }
	        }
	    });
	    

	    center.add(btnExit);
	    
	    String[] civUnits = {"Swordsman","Spearman","Crossbow","Cannon","ArrowTower","Catapult","RocketLauncherTower","Magician", "Priest" };
	    civCards = new UnitCardPanel[civUnits.length];
	    for(int i = 0; i < civUnits.length; i++) {

	    	String unitName = civUnits[i];
	    	
	        civCards[i] =new UnitCardPanel(civUnits[i],true,true);
	        
	        civCards[i].getRecruit_btn().addActionListener(new ActionListener() {

	            public void actionPerformed(ActionEvent e) {

	                recruitUnit(unitName,civCards[getUnitIndex(unitName)].getQuantity().getText());
	            }
	        });
	        
	        
	        left.add(civCards[i]);
	    }
	    

	    String[] enemyUnits = {"Swordsman","Spearman","Crossbow","Cannon"};
	    enemyCards = new UnitCardPanel[enemyUnits.length];

	    for(int i = 0; i < enemyUnits.length; i++) {

	        enemyCards[i] =new UnitCardPanel(enemyUnits[i],false,false);
	        right.add(enemyCards[i]);
	    }
	

	    startBattleLoop();
	    
	    
	    
	    add(scroll, BorderLayout.WEST);
	    add(center, BorderLayout.CENTER);
	    add(right, BorderLayout.EAST);

	}
	
	private void startBattleLoop() {
		

		Timer enemy_generate = new Timer(20000, null);

	    enemy_generate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				battle.generateEnemyUnits(enemy);
				frame.setEnemy(enemy);
				updateLiveArmyCounters();
			}
	    	
	    });
	    
	    Timer enemy_generate_resources = new Timer(60000, null);

	    enemy_generate_resources.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generateResourcesEnemy();
				
			}
	    	
	    });

	     timer = new Timer(1000, null);

	     timer.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent e) {

	            if (!battle.battleIsOver()) {

	                battle.combat();
	                updateBattleUI();

	            } else {

	                timer.stop();
	                enemy_generate.stop();
                    enemy_generate_resources.stop(); 
	                
	                frame.getDb().saveCivilization(civ);
	                saveBattleReport();
	                
	                
	                
	                
	                System.out.println("\n\n===== STEP BY STEP =====\n");
	                System.out.println(battle.getReportStepStep());

	                System.out.println("\n\n===== FINAL REPORT =====\n");
	                System.out.println(battle.getBattleDevelopment());

	                System.out.println("\nBattle Finished");

	                int option = JOptionPane.showConfirmDialog(null,"Battle terminated!\n\nDo you want to start another battle?","Battle Finished",
	                        JOptionPane.YES_NO_OPTION
	                );

	                if(option == JOptionPane.YES_OPTION){

	                    restartBattle();

	                }else{

	                    exitBattle();
	                }
	            }
	        }
	    });

	    timer.start();
	    enemy_generate.start();
	    enemy_generate_resources.start();
	    updateLiveArmyCounters();

	    
	}
	
	private void generateResourcesEnemy() {
    	int food_enemy = FOOD_BASE_ENEMY_ARMY;
        int wood_enemy = WOOD_BASE_ENEMY_ARMY;
        int iron_enemy = IRON_BASE_ENEMY_ARMY;
        
        enemy.setFood(enemy.getFood() + food_enemy);
        enemy.setWood(enemy.getWood() + wood_enemy);
        enemy.setIron(enemy.getIron() + iron_enemy);
        
	}
	
	private String getImagePath(MilitaryUnit unit, boolean isCivUnit) {

	    String suffix;

	    if(isCivUnit) {
	        suffix = "_civ.png";
	    } else {
	        suffix = "_enemy.png";
	    }

	    return "/M3/images/" +
	            unit.getClass().getSimpleName().toLowerCase() +
	            suffix;
	}
	
	
	private void updateBattleUI() {

	    MilitaryUnit attacker =battle.getCurrentAttacker();

	    MilitaryUnit defender =battle.getCurrentDefender();

	    if(attacker != null) {
	    	
	    	boolean attackerIsCiv = battle.isCivilizationTurn();
	    	
	        center.getAttackerCard().updateCard(attacker.getClass().getSimpleName(),getImagePath(attacker, attackerIsCiv),
	        		attacker.attack(),attacker.getActualArmor());
	    }

	    if(defender != null) {
	    	
	    	boolean defenderIsCiv = !battle.isCivilizationTurn();
	    	
	        center.getDefenderCard().updateCard(defender.getClass().getSimpleName(),getImagePath(defender, defenderIsCiv)
	        		,defender.attack(),defender.getActualArmor());
	    }

	    moveCards();

	    updateCounts();
	}
	
	private void moveCards() {

	    if(battle.isCivilizationTurn()) {
	        center.showAttackerOnTop();
	    } else {
	        center.showDefenderOnTop();
	    }
	}
	
	private void updateCounts() {

	    for(int i = 0; i < civCards.length; i++) {

	        civCards[i].setCount(civ.getArmy()[i].size()
	        );
	    }

	    for(int i = 0; i < enemyCards.length; i++) {

	        enemyCards[i].setCount(enemy.getArmy()[i].size()
	        );
	    }
        updateLiveArmyCounters();
	}
	
	private void updateLiveArmyCounters() {
		int totalCivUnits = 0;
		for (ArrayList<MilitaryUnit> unitList : civ.getArmy()) {
			if (unitList != null) {
			totalCivUnits += unitList.size();
			}
			
		}
		int totalEnemyUnits = 0;
		for (ArrayList<MilitaryUnit> unitList : enemy.getArmy()) {
			if (unitList != null)  {
				totalEnemyUnits += unitList.size();
			}
			
		}
		frame.getTopBarPanel().updateLiveCounts(totalCivUnits, totalEnemyUnits);
		
	}
	
	
	private void restartBattle(){
		
		timer.stop();

	    removeAll();

	    enemy = new Civilization("Enemy");

	    enemy.enemy();

	    enemy.generateEnemyArmy(civ);

	    battle = new Battle(
	            civ.getArmy(),
	            enemy.getArmy(),
	            civ.getName(),
	            enemy.getName()
	    );

	    JPanel left = new JPanel(new GridLayout(3,3,10,10));

	    JScrollPane scroll = new JScrollPane(left);

	    scroll.setBorder(null);

	    scroll.getViewport().setBackground(GameColors.PANEL);

	    left.setPreferredSize(new Dimension(330, 0));

	    JPanel right = new JPanel(new GridLayout(0,1,15,15));

	    right.setPreferredSize(new Dimension(90, 0));

	    left.setBackground(GameColors.PANEL);

	    right.setBackground(GameColors.PANEL);

	    center = new CenterFightPanel();

	    center.setMinimumSize(new Dimension(400,0));

	    center.add(btnExit);
	    
	    String[] civUnits = {
	            "Swordsman",
	            "Spearman",
	            "Crossbow",
	            "Cannon",
	            "ArrowTower",
	            "Catapult",
	            "RocketLauncherTower",
	            "Magician",
	            "Priest"
	    };

	    civCards = new UnitCardPanel[civUnits.length];

	    for(int i = 0; i < civUnits.length; i++) {

	    	String unitName = civUnits[i];
	        civCards[i] =new UnitCardPanel(civUnits[i],true,true);

	        civCards[i].getRecruit_btn().addActionListener(new ActionListener() {

	            public void actionPerformed(ActionEvent e) {

	                recruitUnit(unitName,civCards[getUnitIndex(unitName)].getQuantity().getText()
	                );
	            }
	        });

	        
	        left.add(civCards[i]);
	    }

	    String[] enemyUnits = {
	            "Swordsman",
	            "Spearman",
	            "Crossbow",
	            "Cannon"
	    };

	    enemyCards = new UnitCardPanel[enemyUnits.length];

	    for(int i = 0; i < enemyUnits.length; i++) {

	        enemyCards[i] =
	                new UnitCardPanel(enemyUnits[i],false,false);

	        right.add(enemyCards[i]);
	    }

	    add(scroll, BorderLayout.WEST);

	    add(center, BorderLayout.CENTER);

	    add(right, BorderLayout.EAST);

	    revalidate();

	    repaint();

	    startBattleLoop();
	}
	
	
	
	private void exitBattle(){

		timer.stop();

	    battlePanel.returnToStartPanel();
	}
	

	private void recruitUnit(String unitName, String quantityText) {

	    int quantity;

	    try {

	        quantity = Integer.parseInt(quantityText);

	    } catch(NumberFormatException e) {

	        GameLog.error("Invalid quantity!");
            return;
	    }

	    if(quantity <= 0) {

	    	GameLog.error("Quantity must be positive!");
	        return;
	    }

	    try {

	        switch(unitName) {

	            case "Swordsman":
	                civ.newSwordsman(quantity);
	                break;

	            case "Spearman":
	                civ.newSpearman(quantity);
	                break;

	            case "Crossbow":
	                civ.newCrossbow(quantity);
	                break;

	            case "Cannon":
	                civ.newCannon(quantity);
	                break;

	            case "ArrowTower":
	                civ.newArrowTower(quantity);
	                break;

	            case "Catapult":
	                civ.newCatapult(quantity);
	                break;

	            case "RocketLauncherTower":
	                civ.newRocketLauncher(quantity);
	                break;

	            case "Magician":
	                civ.newMagician(quantity);
	                break;

	            case "Priest":
	                civ.newPriest(quantity);
	                break;
	        }

	        updateCounts();
	        frame.RefreshInterface();

	    } catch(ResourceException e) {

	    	int notrecruited = GetNumber(e.getMessage());
            int created = quantity - notrecruited;

            if(created > 0) {
                   GameLog.info(created + " " + unitName + " recruited!");
               }
               GameLog.error(notrecruited + " " + unitName + " could not be recruited due to insufficient resources!!");
               updateCounts();
               frame.RefreshInterface(); 
	    }
	}
	
	private int getUnitIndex(String unitName) {

	    switch(unitName) {

	        case "Swordsman":
	            return 0;

	        case "Spearman":
	            return 1;

	        case "Crossbow":
	            return 2;

	        case "Cannon":
	            return 3;

	        case "ArrowTower":
	            return 4;

	        case "Catapult":
	            return 5;

	        case "RocketLauncherTower":
	            return 6;

	        case "Magician":
	            return 7;

	        case "Priest":
	            return 8;
	    }

	    return 0;
	}
	
	 private int GetNumber(String text) {

	    	String[] words = text.split(" ");

	        for (String word : words) {
	            try {
	            	int number = Integer.parseInt(word);
	                return number;
	            } catch (NumberFormatException e) {
	            }
	        }

	        return 0;
	    }
	 
	 
	 private void saveBattleReport() {

		    String winner;

		    if (battle.getResourcesLooses()[0][3]
		            < battle.getResourcesLooses()[1][3]) {

		        winner = civ.getName();

		    } else {

		        winner = enemy.getName();
		    }

		    frame.getDb().saveBattleStats(

		            frame.getCivId(),
		            battleNumber,

		            0,
		            0,

		            winner,

		            battle.getResourcesLooses()[0][0],
		            battle.getResourcesLooses()[0][1],
		            battle.getResourcesLooses()[0][2],

		            battle.getResourcesLooses()[1][0],
		            battle.getResourcesLooses()[1][1],
		            battle.getResourcesLooses()[1][2],

		            battle.getResourcesLooses()[0][0],
		            battle.getResourcesLooses()[0][1],
		            battle.getResourcesLooses()[0][2],

		            battle.getResourcesLooses()[1][0],
		            battle.getResourcesLooses()[1][1],
		            battle.getResourcesLooses()[1][2],

		            battle.getWasteWoodIron()[0],
		            battle.getWasteWoodIron()[1]
		    );
		    
		    frame.getDb().saveBattleLog(

		            frame.getCivId(),

		            battleNumber,

		            battle.getReportStepStep());
		    
		    String[] unitNames = {"Swordsman","Spearman","Crossbow","Cannon","ArrowTower","Catapult","RocketLauncherTower","Magician","Priest"};
		    String[] categories = {"Attack","Attack","Attack","Attack","Defense","Defense","Defense","Special","Special"};
		    
		    for(int i = 0; i < unitNames.length; i++) {
		    	int initialUnits = civ.getArmy()[i].size() + battle.getUnitsLooses()[0][i];
		    	
		    	frame.getDb().saveBattleUnitStat(frame.getCivId(),battleNumber,"Player", categories[i],unitNames[i],initialUnits,battle.getUnitsLooses()[0][i]);
		    }
		    
		    for(int i = 0; i < 4; i++) {
		    	int initialUnits = enemy.getArmy()[i].size() + battle.getUnitsLooses()[1][i];
		    	
		    	frame.getDb().saveBattleUnitStat(frame.getCivId(),battleNumber,"Enemy",categories[i],unitNames[i],initialUnits,battle.getUnitsLooses()[1][i]);
		    }

		    civ.setBattles(battleNumber);

		    frame.getDb().saveCivilization(civ);
		}
	
}