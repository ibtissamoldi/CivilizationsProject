package M3.GUIgame.Battlepanels;

import java.awt.BorderLayout;


import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import M3.GUIgame.GameColors;

import M3.game.Battle;
import M3.game.Civilization;
import M3.interfaces.MilitaryUnit;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class MainBattlePanel extends JPanel {
	
	private CenterFightPanel center;

	private Battle battle;

	private Civilization civ;
	private Civilization enemy;
	
	private UnitCardPanel[] civCards;
	private UnitCardPanel[] enemyCards;
	


	public MainBattlePanel(Civilization civ){
		this.civ = civ;
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

	    
	    
	    
	    String[] civUnits = {"Swordsman","Spearman","Crossbow","Cannon","ArrowTower","Catapult","RocketLauncherTower","Magician", "Priest" };
	    civCards = new UnitCardPanel[civUnits.length];
	    for(int i = 0; i < civUnits.length; i++) {

	        civCards[i] =new UnitCardPanel(civUnits[i],true,true);
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

	    Timer timer = new Timer(1000, null);

	    timer.addActionListener(new ActionListener() {

	        public void actionPerformed(ActionEvent e) {

	            if (!battle.battleIsOver()) {

	                battle.combat();
	                updateBattleUI();

	            } else {

	                timer.stop();
	                
	                System.out.println("\n\n===== STEP BY STEP =====\n");
	                System.out.println(battle.getReportStepStep());

	                System.out.println("\n\n===== FINAL REPORT =====\n");
	                System.out.println(battle.getBattleDevelopment());

	                System.out.println("\nBattle Finished");

	                JOptionPane.showMessageDialog(null, "Battle Finished!");
	            }
	        }
	    });

	    timer.start();
	}
	
	
	private void updateBattleUI() {

	    MilitaryUnit attacker =battle.getCurrentAttacker();

	    MilitaryUnit defender =battle.getCurrentDefender();

	    if(attacker != null) {
	        center.getAttackerCard().updateCard(attacker.getClass().getSimpleName(),"./M3/images/"+ 
	        attacker.getClass().getSimpleName().toLowerCase()+ "_civ.png",attacker.attack(),attacker.getActualArmor());
	    }

	    if(defender != null) {
	        center.getDefenderCard().updateCard(defender.getClass().getSimpleName(),"./M3/images/"+
	        defender.getClass().getSimpleName().toLowerCase()+ "_enemy.png",defender.attack(),defender.getActualArmor());
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
	}

	
}