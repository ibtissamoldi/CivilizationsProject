package GUIgame;

import java.awt.BorderLayout;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import exceptions.ResourceException;
import game.Civilization;

public class UnitPanel extends JPanel{
	
	private JLabel lbl_count;
    private JLabel lbl_stats;
    private JLabel lbl_cost;
    
    private JButton btn_recruit;
    
    private JTextField field_quantity;
    
    private Civilization civ;
    
    private String unit_type;
    
    
    public UnitPanel(Civilization civ, String unit_type) {
    	this.civ = civ;
        this.unit_type = unit_type;
        
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2),unit_type,0,0,new Font("Serif", Font.BOLD, 16),
        		GameColors.GOLD
        	));        
        setBackground(GameColors.PANEL);
        
      
        
    	
        
        JPanel center_panel = new JPanel(new GridLayout(3,1));
        center_panel.setBackground(GameColors.PANEL);

        lbl_count = new JLabel("Count: ");
        lbl_stats = new JLabel("DMG:  | Armor: ");
        lbl_cost  = new JLabel("Cost: Food ? Wood ? Iron ? Mana ?");
        
        lbl_count.setForeground(GameColors.TEXT);
        lbl_stats.setForeground(GameColors.TEXT);
        lbl_cost.setForeground(GameColors.TEXT);
        
        center_panel.add(lbl_count);
        center_panel.add(lbl_stats);
        center_panel.add(lbl_cost);
        
        add(center_panel,BorderLayout.CENTER);
        
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
        
        add(bottom_panel,BorderLayout.SOUTH);
        
        btn_recruit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                RecruitUnit();
            }
        });        
    }
    
    private void RecruitUnit() {
    	int quantity =  Integer.parseInt(field_quantity.getText());
    	
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
            }
            JOptionPane.showMessageDialog(this, quantity + " " + unit_type + " recruited!");
            
    	} catch (ResourceException e) {
    		
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lacking Resources error :(", JOptionPane.ERROR_MESSAGE);
        }
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
        
        JPanel grid = new JPanel(new GridLayout(3,1,20,20));
        grid.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        grid.setBackground(GameColors.BACKGROUND);
        
        grid.add(new UnitPanel(civ, "ArrowTower"));
        grid.add(new UnitPanel(civ, "Catapult"));
        grid.add(new UnitPanel(civ, "RocketLauncher"));
        
        add(grid, BorderLayout.CENTER);
        
        
        
	}
	
	
	
	
	
}


class SpecialTabPanel extends JPanel {
	
	
	
	
    
    
	
	
	
}
