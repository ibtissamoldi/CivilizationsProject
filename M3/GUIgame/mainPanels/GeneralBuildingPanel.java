package M3.GUIgame.mainPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import M3.GUIgame.GameColors;
import M3.exceptions.ResourceException;
import M3.game.Civilization;
import M3.interfaces.Variables;
import M3.GUIgame.GameLog;
import M3.GUIgame.MainFrame;


public class GeneralBuildingPanel extends JPanel{
	
	private BuildingPanel farm_panel,carpentry_panel,smithy_panel,magic_tower_panel,church_panel;
	private Civilization civ;

	public GeneralBuildingPanel(Civilization civ, MainFrame frame) {
        this.civ = civ;

        setLayout(new GridLayout(3, 2, 15, 15));
        setBackground(GameColors.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        farm_panel = new BuildingPanel(civ, "Farm",frame);
        carpentry_panel = new BuildingPanel(civ, "Carpentry",frame);
        smithy_panel = new BuildingPanel(civ, "Smithy",frame);
        magic_tower_panel = new BuildingPanel(civ, "Magic Tower",frame);
        church_panel = new BuildingPanel(civ, "Church",frame);

        add(farm_panel);
        add(carpentry_panel);
        add(smithy_panel);
        add(magic_tower_panel);
        add(church_panel);
    }
}




class BuildingPanel extends JPanel implements Variables{
	
	private JLabel lbl_image;
	
	private JTextField field_quantity;
	
	private JLabel lbl_count,lbl_effect;
	
	private JLabel lbl_foodcost;
	private JLabel lbl_woodcost;
	private JLabel lbl_ironcost;

   
    private JButton btn_build;

    private Civilization civ;
    
    private String building_type;
    
    private MainFrame frame;
    
    public BuildingPanel(Civilization civ, String building_type,MainFrame frame) {
    	this.civ = civ;
    	this.building_type= building_type;
    	this.frame = frame;
    	
    	setLayout(new BorderLayout(15, 10));
    	

	    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2),
	    		building_type,0,0,new Font("Serif", Font.BOLD, 16),GameColors.GOLD));    
        setBackground(GameColors.PANEL);
    	
        
    

    
    ImageIcon image = new ImageIcon(getImagePath());
    lbl_image = new JLabel(image);
    add(lbl_image, BorderLayout.WEST);


     JPanel right_panel = new JPanel(new BorderLayout());
     right_panel.setBackground(GameColors.PANEL);


     JPanel center_panel = new JPanel(new GridLayout(5, 2, 5, 5));
     center_panel.setBackground(GameColors.PANEL);

     JLabel lbl_count_text = new JLabel("Buildings:");
     lbl_count_text.setForeground(new Color(190,180,160));
     
     lbl_count = new JLabel();
     lbl_count.setForeground(new Color(245,230,190));
     
     JLabel lbl_effect_text = new JLabel("Effect:");
     lbl_effect_text.setForeground(new Color(190,180,160));

     lbl_effect = new JLabel();
     lbl_effect.setForeground(new Color(220,220,200));


     JLabel lbl_food_text = new JLabel("Food:");
     lbl_food_text.setForeground(new Color(190,180,160));

     lbl_foodcost = new JLabel();
     lbl_foodcost.setForeground(GameColors.FOOD);
     
     
     JLabel lbl_wood_text = new JLabel("Wood:");
     lbl_wood_text.setForeground(new Color(190,180,160));

     lbl_woodcost = new JLabel();
     lbl_woodcost.setForeground(GameColors.WOOD);
     
     
     JLabel lbl_iron_text = new JLabel("Iron:");
     lbl_iron_text.setForeground(new Color(190,180,160));

     lbl_ironcost = new JLabel();
     lbl_ironcost.setForeground(GameColors.IRON);
     
     
     center_panel.add(lbl_count_text);
     center_panel.add(lbl_count);

     center_panel.add(lbl_effect_text);
     center_panel.add(lbl_effect);

     center_panel.add(lbl_food_text);
     center_panel.add(lbl_foodcost);

     center_panel.add(lbl_wood_text);
     center_panel.add(lbl_woodcost);

     center_panel.add(lbl_iron_text);
     center_panel.add(lbl_ironcost);


     
     JPanel bottom_panel = new JPanel();
     bottom_panel.setBackground(GameColors.PANEL);
     
     field_quantity = new JTextField("1", 5);
     field_quantity.setBackground(GameColors.INPUT_BG);
     field_quantity.setForeground(GameColors.TEXT);
     field_quantity.setBorder(BorderFactory.createLineBorder(GameColors.BORDER));
     
     

     btn_build = new JButton("Build");
     btn_build.setBackground(GameColors.BUTTON);
     btn_build.setForeground(GameColors.TEXT);
     btn_build.setFocusPainted(false); 
     btn_build.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2),BorderFactory.createEmptyBorder(2, 15, 2, 15)));
     btn_build.setFont(new Font("Serif", Font.BOLD, 12));

     bottom_panel.add(field_quantity);
     bottom_panel.add(btn_build);
     


     right_panel.add(center_panel, BorderLayout.CENTER);
     right_panel.add(bottom_panel, BorderLayout.SOUTH);


     add(right_panel, BorderLayout.CENTER);
    
     btn_build.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        buildBuilding();
    	    }
    	});

     loadBuildingData();
    }

    private void loadBuildingData() {
    	
		switch (building_type) {
		
		case "Farm":
			lbl_count.setText(String.valueOf(civ.getFarm()));
			lbl_effect.setText("+10% Food");
			lbl_foodcost.setText(String.valueOf(FOOD_COST_FARM));
			lbl_woodcost.setText(String.valueOf(WOOD_COST_FARM));
			lbl_ironcost.setText(String.valueOf(IRON_COST_FARM));
	        break;
	        
		 case "Smithy":
			 lbl_count.setText(String.valueOf(civ.getSmithy()));
	         lbl_effect.setText("+10% Iron");
	         lbl_foodcost.setText(String.valueOf(FOOD_COST_SMITHY));
	 		 lbl_woodcost.setText(String.valueOf(WOOD_COST_SMITHY));
	 		 lbl_ironcost.setText(String.valueOf(IRON_COST_SMITHY));
	         break;
	
	     case "Magic Tower":
	    	 lbl_count.setText(String.valueOf(civ.getMagicTower()));
	         lbl_effect.setText("Unlocks mana");
	         lbl_foodcost.setText(String.valueOf(FOOD_COST_MAGICTOWER));
	 		 lbl_woodcost.setText(String.valueOf(WOOD_COST_MAGICTOWER));
	 		 lbl_ironcost.setText(String.valueOf(IRON_COST_MAGICTOWER));
	         break;
	
	     case "Church":
	    	 lbl_count.setText(String.valueOf(civ.getChurch()));
	         lbl_effect.setText("Unlocks Priests");
	         lbl_foodcost.setText(String.valueOf(FOOD_COST_CHURCH));
	 		 lbl_woodcost.setText(String.valueOf(WOOD_COST_CHURCH));
	 		 lbl_ironcost.setText(String.valueOf(IRON_COST_CHURCH));
	         break;
	         
	     case "Carpentry":
	    	lbl_count.setText(String.valueOf(civ.getCarpentry()));
	    	lbl_effect.setText("+10% Wood");
	    	lbl_foodcost.setText(String.valueOf(FOOD_COST_CARPENTRY));
			lbl_woodcost.setText(String.valueOf(WOOD_COST_CARPENTRY));
			lbl_ironcost.setText(String.valueOf(IRON_COST_CARPENTRY));
	         break;
		}
	    			
    }
		
	

    private void buildBuilding() {

        GameLog.log.clear();

        int quantity;

        try {
            quantity = Integer.parseInt(field_quantity.getText());
        } catch (NumberFormatException e) {
            GameLog.error("Invalid quantity!");
            return;
        }

        if (quantity <= 0) {
            GameLog.error("Quantity must be positive!");
            return;
        }

        int built = 0;

        try {
            for (int i = 0; i < quantity; i++) {

                switch (building_type) {

                    case "Farm":
                        civ.newFarm();
                        break;

                    case "Carpentry":
                        civ.newCarpentry();
                        break;

                    case "Smithy":
                        civ.newSmithy();
                        break;

                    case "Magic Tower":
                        civ.newMagicTower();
                        break;

                    case "Church":
                        civ.newChurch();
                        break;
                }

                built++;
            }

            GameLog.info(built + " " + building_type + " built!");
            

        } catch (ResourceException e) {
        	int notBuilt=quantity-built;

            if (built > 0) {
                GameLog.info(built + " " + building_type + " built!");
                GameLog.error(notBuilt+" Could not build more " + building_type + " due to insufficient resources!");
            } else {
                GameLog.error(notBuilt +" Could not build " + building_type + " due to insufficient resources!");
            }
        }

        loadBuildingData();  
        frame.RefreshInterface();
    }
		
	

	private String getImagePath() {
        switch (building_type) {
            case "Farm":
                return "./M3/images/farm.png";

            case "Carpentry":
                return "./M3/images/carpentry.png";

            case "Smithy":
                return "./M3/images/smithy.png";

            case "Magic Tower":
                return "./M3/images/magic_tower.png";

            case "Church":
                return "./M3/images/church.png";

            default:
                return "";
        }
    }

}