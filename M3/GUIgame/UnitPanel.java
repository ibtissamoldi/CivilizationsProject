package M3.GUIgame;

import java.awt.BorderLayout;


import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import M3.exceptions.ResourceException;
import M3.game.Civilization;
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

public class UnitPanel extends JPanel{
	
	private JLabel lbl_owned;
    private JLabel lbl_dmg;
    private JLabel lbl_armor;
    private JLabel lbl_cost;
    private JLabel lbl_attackagain;
    private JLabel lbl_wastechance;
    private JLabel lbl_exp;
    private JLabel lbl_image;
    
    private JButton btn_recruit;
    
    private JTextField field_quantity;
    
    private Civilization civ;
    
    private MilitaryUnit unit;

    private String unit_type;
    
    
    public UnitPanel(Civilization civ, String unit_type) {
    	this.civ = civ;
        this.unit_type = unit_type;
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2),unit_type,0,0,new Font("Serif", Font.BOLD, 16),
        		GameColors.GOLD
        	));        
        setBackground(GameColors.PANEL);
        
        
        switch(unit_type) {

        case "Swordsman":
            unit = new Swordsman();
            break;
        case "Spearman":
            unit = new Spearman();
            break;
        case "Crossbow":
            unit = new Crossbow();
            break;
        case "Cannon":
            unit = new Cannon();
            break;
        case "ArrowTower":
            unit = new ArrowTower(civ.getTechnologyDefense(),civ.getTechnologyAttack());
            break;
        case "Catapult":
            unit = new Catapult(civ.getTechnologyDefense(),civ.getTechnologyAttack());
            break;
        case "RocketLauncherTower":
            unit = new RocketLauncherTower(civ.getTechnologyDefense(),civ.getTechnologyAttack());
            break;
        case "Magician":
            unit = new Magician(civ.getTechnologyAttack());
            break;
        case "Priest":
            unit = new Priest();
            break;
        }
      
        
     // Imagen de las unidades
        
        ImageIcon image = new ImageIcon(getImagePath());
        
        lbl_image = new JLabel(image);
       

        add(lbl_image, BorderLayout.EAST);
        
    	
        // panel central y left panel
        JPanel left_panel = new JPanel(new BorderLayout());
        left_panel.setBackground(GameColors.PANEL);

        
        
        
        
        JPanel center_panel = new JPanel(new GridLayout(7,1,5,5));
        center_panel.setBackground(GameColors.PANEL);

        lbl_owned = new JLabel("Owned: "+ GetUnitCount());
        lbl_dmg = new JLabel("Damage:  " + unit.attack());
        lbl_armor = new JLabel("Armor: " + unit.getActualArmor());
        lbl_attackagain = new JLabel("Attack Again: "+unit.getChanceAttackAgain() + "%");
        lbl_wastechance =  new JLabel("Waste Chance: "+unit.getChanceGeneratinWaste() + "%");
        lbl_cost  = new JLabel("Cost:     Food: " + unit.getFoodCost() + " Wood: " + unit.getWoodCost()+ " Iron: " + unit.getIronCost() +" Mana: "+unit.getManaCost());
        lbl_exp = new JLabel("Experience: "+unit.getExperience());


        JLabel[] labels = {lbl_owned,lbl_dmg,lbl_armor,lbl_attackagain,lbl_wastechance,lbl_cost,lbl_exp};

        for (JLabel label : labels) {
                label.setForeground(GameColors.TEXT);
                center_panel.add(label);
                }
        
      
        UpdateInfo();
        

        
        
        JPanel bottom_panel = new JPanel();
        bottom_panel.setBackground(GameColors.PANEL);
        field_quantity = new JTextField("1", 5); 
        field_quantity.setBackground(GameColors.INPUT_BG);
        field_quantity.setForeground(GameColors.TEXT);
        field_quantity.setBorder(
        	    BorderFactory.createLineBorder(GameColors.BORDER)
        	);
        
        
        btn_recruit = new JButton("Recruit");
        btn_recruit.setBackground(GameColors.BUTTON);
        btn_recruit.setForeground(GameColors.TEXT);
        btn_recruit.setFocusPainted(false);
        btn_recruit.setBorder(
        	    BorderFactory.createLineBorder(GameColors.BORDER, 2)
        	);
        btn_recruit.setFont(new Font("Serif", Font.BOLD, 14));
        btn_recruit.setOpaque(true);
        
        bottom_panel.add(field_quantity);
        bottom_panel.add(btn_recruit);
        
     // Añadimos texto y botón al panel izquierdo
        left_panel.add(center_panel,BorderLayout.CENTER);
        left_panel.add(bottom_panel,BorderLayout.SOUTH);
        
        // Añadimos el panelDOIZQUIERO al centro DE UNIT PANEL
        add(left_panel, BorderLayout.CENTER);
       
        
        
        btn_recruit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecruitUnit();
            }
        });        
    }
    
	private String getImagePath() {
        switch (unit_type) {
            case "Swordsman":
                return "./M3/images/swordsman.png";

            case "Spearman":
                return "./M3/images/spearman.png";

            case "Crossbow":
                return "./M3/images/crossbow.png";

            case "Cannon":
                return "./M3/images/cannon.png";

            case "ArrowTower":
                return "./M3/images/arrowTower.png";
                
            case "Catapult":
            	return "./M3/images/catapult.png";

            case "RocketLauncherTower":
                return "./M3/images/rocketLauncherTower.png";

            case "Magician":
                return "./M3/images/magician.png";

            case "Priest":
                return "./M3/images/priest.png";


             
        }
        return "";
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
    
    private void RecruitUnit() {
    	GameLog.log.clear();
    	int quantity;
    	try {
    		quantity =  Integer.parseInt(field_quantity.getText());
    	}catch(NumberFormatException e) {
    		GameLog.error("Invalid quantity!");
            return;
    	}
    	if(quantity <= 0) {
            GameLog.error("Quantity must be positive!");
            return;
    	}
    	try {
    		switch(unit_type) {
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
    		GameLog.info(quantity + " " + unit_type + " recruited!");
            UpdateInfo();
            
    	} catch (ResourceException e) {
    		
    		int notrecruited = GetNumber(e.getMessage());
            int created = quantity - notrecruited;

            if(created > 0) {
                   GameLog.info(created + " " + unit_type + " recruited!");
               }
               GameLog.error(notrecruited + " " + unit_type + " could not be recruited due to insufficient resources!!");
               UpdateInfo();
        }
    }
    
    
    private int GetArmySize(int index) {
        if(civ.getArmy()[index] == null) {
        return 0;
    }

    return civ.getArmy()[index].size();
    }
    
    private int GetUnitCount() {

        switch(unit_type) {

            case "Swordsman":
                return GetArmySize(0);

            case "Spearman":
                return GetArmySize(1);

            case "Crossbow":
                return GetArmySize(2);

            case "Cannon":
                return GetArmySize(3);

            case "ArrowTower":
                return GetArmySize(4);

            case "Catapult":
                return GetArmySize(5);

            case "RocketLauncherTower":
                return GetArmySize(6);

            case "Magician":
                return GetArmySize(7);

            case "Priest":
                return GetArmySize(8);
        }
        return 0;
    }
    
    private void UpdateInfo() {

        lbl_owned.setText("Owned: "+GetUnitCount());
		lbl_dmg.setText("Damage:  " + unit.attack());
		lbl_armor.setText( "Armor: " + unit.getActualArmor());
		lbl_attackagain.setText("Attack Again: "+unit.getChanceAttackAgain() + "%");
		lbl_wastechance.setText("Waste Chance: "+unit.getChanceGeneratinWaste() + "%");
		lbl_cost.setText("Cost:     Food: " + unit.getFoodCost() + " Wood: " + unit.getWoodCost()+ " Iron: " + unit.getIronCost() +" Mana: "+unit.getManaCost());
		lbl_exp.setText("Experience: "+unit.getExperience());
	}

    
}


class AttackTabPanel extends JPanel {
	
	public AttackTabPanel(Civilization civ) {
		setLayout(new BorderLayout());
        setBackground(GameColors.BACKGROUND);
        
        JPanel grid = new JPanel(new GridLayout(2,2,20,20));
        grid.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        grid.setBackground(GameColors.BACKGROUND);
        
        grid.add(new UnitPanel(civ, "Swordsman"));
        grid.add(new UnitPanel(civ, "Spearman"));
        grid.add(new UnitPanel(civ, "Crossbow"));
        grid.add(new UnitPanel(civ, "Cannon"));
        
        add(grid, BorderLayout.CENTER);

        
	}
	
	
	
}



class DefenseTabPanel extends JPanel {

	public DefenseTabPanel(Civilization civ) {
		setLayout(new BorderLayout());
        setBackground(GameColors.BACKGROUND);
        
        JPanel grid = new JPanel(new GridLayout(2,1,20,20));
        grid.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        grid.setBackground(GameColors.BACKGROUND);
        
        grid.add(new UnitPanel(civ, "ArrowTower"));
        grid.add(new UnitPanel(civ, "Catapult"));
        grid.add(new UnitPanel(civ, "RocketLauncherTower"));
        
        add(grid, BorderLayout.CENTER);
        
        
        
	}
	
	
	
	
	
}


class SpecialTabPanel extends JPanel {
	
	public SpecialTabPanel(Civilization civ) {
	    setLayout(new BorderLayout());
	    setBackground(GameColors.BACKGROUND);
	
	    JPanel grid = new JPanel(new GridLayout(2,1,20,20));
	    grid.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	    grid.setBackground(GameColors.BACKGROUND);
	
	    grid.add(new UnitPanel(civ, "Magician"));
	    grid.add(new UnitPanel(civ, "Priest"));
	
	    add(grid, BorderLayout.CENTER);
}
	
    
    
	
	
	
}
