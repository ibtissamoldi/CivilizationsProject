package M3.GUIgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

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
    private JLabel lbl_foodcost;
    private JLabel lbl_woodcost;
    private JLabel lbl_ironcost;
    private JLabel lbl_manacost;
    private JLabel lbl_attackagain;
    private JLabel lbl_wastechance;
    private JLabel lbl_exp;
    private JLabel lbl_image;
    
    private JButton btn_recruit;
    
    private JTextField field_quantity;
    
    private Civilization civ;
    
    private MilitaryUnit unit;

    private String unit_type;
    
    private MainFrame frame;
    
    
    public UnitPanel(Civilization civ, String unit_type,MainFrame frame) {
    	this.civ = civ;
        this.unit_type = unit_type;
        this.frame = frame;
        
        setLayout(new BorderLayout(10,10));
        setBorder(
        	    BorderFactory.createTitledBorder(
        	        BorderFactory.createLineBorder(GameColors.BORDER, 0),
        	        "•"+unit_type+"•",
        	        TitledBorder.CENTER,
        	        TitledBorder.TOP,
        	        new Font("Serif", Font.BOLD, 18),
        	        GameColors.GOLD
        	    )
        	);
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
            unit = new Priest(civ.getTechnologyDefense());
            break;
        }
        
    
        
        // Imagen de las unidades
        
        ImageIcon image = new ImageIcon(getImagePath());

        Image scaled = image.getImage().getScaledInstance(130, 130, Image.SCALE_SMOOTH);

        lbl_image = new JLabel(new ImageIcon(scaled));
        
        lbl_image.setHorizontalAlignment(JLabel.CENTER);
        lbl_image.setVerticalAlignment(JLabel.CENTER);
       
        lbl_image.setPreferredSize(new Dimension(150, 150));

        add(lbl_image, BorderLayout.EAST);
        
    	
        // panel central y left panel
        JPanel left_panel = new JPanel(new BorderLayout());
        left_panel.setBackground(GameColors.PANEL);

        
        
        JPanel center_panel = new JPanel(new GridLayout(11,1,2,2));
        center_panel.setBackground(GameColors.PANEL);

        JLabel lbl_owned_text = new JLabel("Total Units:");
        lbl_owned_text.setForeground(new Color(190, 180, 160));
        lbl_owned = new JLabel(String.valueOf(GetUnitCount()));
        lbl_owned.setForeground(new Color(245, 230, 190));
        
        JLabel lbl_dmg_text = new JLabel("DMG/Unit:");
        lbl_dmg_text.setForeground(new Color(190, 180, 160));
        lbl_dmg = new JLabel(String.valueOf(unit.attack()));
        lbl_dmg.setForeground(new Color(245, 230, 190));

        JLabel lbl_armor_text = new JLabel("Armor/Unit:");
        lbl_armor_text.setForeground(new Color(190, 180, 160));
        lbl_armor = new JLabel(String.valueOf(unit.getActualArmor()));
        lbl_armor.setForeground(new Color(245, 230, 190));

        JLabel lbl_attack_text = new JLabel("Attack Again:");
        lbl_attack_text.setForeground(new Color(190, 180, 160));
        lbl_attackagain = new JLabel(unit.getChanceAttackAgain() + "%");
        lbl_attackagain.setForeground(new Color(245, 230, 190));

        JLabel lbl_waste_text = new JLabel("Waste Chance:");
        lbl_waste_text.setForeground(new Color(190, 180, 160));
        lbl_wastechance = new JLabel(unit.getChanceGeneratinWaste() + "%");
        lbl_wastechance.setForeground(new Color(245, 230, 190));
        
        
        JLabel lbl_food_text = new JLabel("Food:");
        lbl_food_text.setForeground(new Color(190, 180, 160));
        lbl_foodcost = new JLabel(String.valueOf(unit.getFoodCost()));
        lbl_foodcost.setForeground(GameColors.FOOD);

        JLabel lbl_wood_text = new JLabel("Wood:");
        lbl_wood_text.setForeground(new Color(190, 180, 160));
        lbl_woodcost = new JLabel(String.valueOf(unit.getWoodCost()));
        lbl_woodcost.setForeground(GameColors.WOOD);

        JLabel lbl_iron_text = new JLabel("Iron:");
        lbl_iron_text.setForeground(new Color(190, 180, 160));
        lbl_ironcost = new JLabel(String.valueOf(unit.getIronCost()));
        lbl_ironcost.setForeground(GameColors.IRON);

        JLabel lbl_mana_text = new JLabel("Mana:");
        lbl_mana_text.setForeground(new Color(190, 180, 160));
        lbl_manacost = new JLabel(String.valueOf(unit.getManaCost()));
        lbl_manacost.setForeground(GameColors.MANA);

        JLabel lbl_exp_text = new JLabel("Total Experience:");
        lbl_exp_text.setForeground(new Color(190, 180, 160));
        lbl_exp = new JLabel(String.valueOf(unit.getExperience()));
        lbl_exp.setForeground(new Color(245, 230, 190));
        
        setFont(new Font("Arial", Font.BOLD, 12));

        center_panel.add(lbl_owned_text);
        center_panel.add(lbl_owned);

        center_panel.add(lbl_dmg_text);
        center_panel.add(lbl_dmg);

        center_panel.add(lbl_armor_text);
        center_panel.add(lbl_armor);

        center_panel.add(lbl_attack_text);
        center_panel.add(lbl_attackagain);

        center_panel.add(lbl_waste_text);
        center_panel.add(lbl_wastechance);

        center_panel.add(lbl_food_text);
        center_panel.add(lbl_foodcost);

        center_panel.add(lbl_wood_text);
        center_panel.add(lbl_woodcost);

        center_panel.add(lbl_iron_text);
        center_panel.add(lbl_ironcost);

        center_panel.add(lbl_mana_text);
        center_panel.add(lbl_manacost);

        center_panel.add(lbl_exp_text);
        center_panel.add(lbl_exp);
        
        UpdateInfo();
        
        
        
        JPanel bottom_panel = new JPanel();
        bottom_panel.setBackground(GameColors.PANEL);
        field_quantity = new JTextField("1", 5); 
        field_quantity.setBackground(GameColors.INPUT_BG);
        field_quantity.setForeground(GameColors.TEXT);
        field_quantity.setBorder(
        	    BorderFactory.createEmptyBorder(5,8,5,8)
        	);
        
        
        btn_recruit = new JButton("Recruit");
        btn_recruit.setBackground(GameColors.BUTTON);
        btn_recruit.setForeground(GameColors.TEXT);
        btn_recruit.setFocusPainted(false);
        btn_recruit.setBorder(
        	    BorderFactory.createCompoundBorder(
        	        BorderFactory.createLineBorder(GameColors.BORDER, 2),
        	        BorderFactory.createEmptyBorder(2, 15, 2, 15)
        	    )
        	);
        btn_recruit.setFont(new Font("Serif", Font.BOLD, 12));
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
                frame.saveGame();
                frame.RefreshInterface();
            }
        });        
    }
    
	private String getImagePath() {
        switch (unit_type) {
            case "Swordsman":
                return "./M3/images/swordsman_civ.png";

            case "Spearman":
                return "./M3/images/spearman_civ.png";

            case "Crossbow":
                return "./M3/images/crossbow_civ.png";

            case "Cannon":
                return "./M3/images/cannon_civ.png";

            case "ArrowTower":
                return "./M3/images/arrowTower_civ.png";
                
            case "Catapult":
            	return "./M3/images/catapult_civ.png";

            case "RocketLauncherTower":
                return "./M3/images/rocketLauncherTower_civ.png";

            case "Magician":
                return "./M3/images/magician_civ.png";

            case "Priest":
                return "./M3/images/priest_civ.png";


             
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
            frame.RefreshInterface();
            
            
    	} catch (ResourceException e) {
    		
    		int notrecruited = GetNumber(e.getMessage());
            int created = quantity - notrecruited;

            if(created > 0) {
                   GameLog.info(created + " " + unit_type + " recruited!");
               }
               GameLog.error(notrecruited + " " + unit_type + " could not be recruited due to insufficient resources!!");
               UpdateInfo();
               frame.RefreshInterface();
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

        lbl_owned.setText(String.valueOf(GetUnitCount()));
		lbl_dmg.setText(String.valueOf(unit.attack()));
		lbl_armor.setText(String.valueOf(unit.getActualArmor()));
		lbl_attackagain.setText(unit.getChanceAttackAgain() + "%");
		lbl_wastechance.setText(unit.getChanceGeneratinWaste() + "%");
        lbl_foodcost.setText(String.valueOf(unit.getFoodCost()));
        lbl_woodcost.setText(String.valueOf(unit.getWoodCost()));
        lbl_ironcost.setText(String.valueOf(unit.getIronCost()));
        lbl_manacost.setText(String.valueOf(unit.getManaCost()));		
		lbl_exp.setText(String.valueOf(unit.getExperience()));
	}

    
}


 class AttackTabPanel extends JPanel {
	
	public AttackTabPanel(Civilization civ,MainFrame frame) {
		setLayout(new BorderLayout());
        setBackground(GameColors.BACKGROUND);
        
        JPanel grid = new JPanel(new GridLayout(2,2,20,20));
        grid.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        grid.setBackground(GameColors.BACKGROUND);
        
        grid.add(new UnitPanel(civ, "Swordsman",frame));
        grid.add(new UnitPanel(civ, "Spearman",frame));
        grid.add(new UnitPanel(civ, "Crossbow",frame));
        grid.add(new UnitPanel(civ, "Cannon",frame));
        
        add(grid, BorderLayout.CENTER);

        
	}
	
	
	
}



class DefenseTabPanel extends JPanel {

	public DefenseTabPanel(Civilization civ,MainFrame frame) {
		setLayout(new BorderLayout());
        setBackground(GameColors.BACKGROUND);
        
        JPanel grid = new JPanel(new GridLayout(2,1,20,20));
        grid.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        grid.setBackground(GameColors.BACKGROUND);
        
        grid.add(new UnitPanel(civ, "ArrowTower",frame));
        grid.add(new UnitPanel(civ, "Catapult",frame));
        grid.add(new UnitPanel(civ, "RocketLauncherTower",frame));
        
        add(grid, BorderLayout.CENTER);
        
        
        
	}
	
	
	
	
	
}


class SpecialTabPanel extends JPanel {
	
	public SpecialTabPanel(Civilization civ,MainFrame frame) {
	    setLayout(new BorderLayout());
	    setBackground(GameColors.BACKGROUND);
	
	    JPanel grid = new JPanel(new GridLayout(2,1,20,20));
	    grid.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
	    grid.setBackground(GameColors.BACKGROUND);
	
	    grid.add(new UnitPanel(civ, "Magician",frame));
	    grid.add(new UnitPanel(civ, "Priest",frame));
	
	    add(grid, BorderLayout.CENTER);
}
	
    
    
	
	
	
}