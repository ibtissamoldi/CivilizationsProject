package M3.GUIgame.mainPanels;

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

import M3.GUIgame.GameColors;
import M3.exceptions.ResourceException;
import M3.game.Civilization;
import M3.interfaces.Variables;
import M3.GUIgame.GameLog;
import M3.GUIgame.MainFrame;

//GENERAL JUNTAR TODAS LA TARJETAS DE CADA EDIFICIO

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
	
	private JLabel lbl_count,lbl_effect,lbl_cost;

   
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
    	
        
    

    // Imagen del edificio
    
    ImageIcon image = new ImageIcon(getImagePath());
    
    lbl_image = new JLabel(image);
   

    add(lbl_image, BorderLayout.WEST);


     // Panel derecho: contiene texto, botón Y textfield
     JPanel right_panel = new JPanel(new BorderLayout());
     right_panel.setBackground(GameColors.PANEL);


     // Panel central con textos
     JPanel center_panel = new JPanel(new GridLayout(3, 1));
     center_panel.setBackground(GameColors.PANEL);

     lbl_count = new JLabel();
     lbl_effect = new JLabel();
     lbl_cost = new JLabel();

     lbl_count.setForeground(GameColors.TEXT);
     lbl_effect.setForeground(GameColors.TEXT);
     lbl_cost.setForeground(GameColors.TEXT);

     center_panel.add(lbl_count);
     center_panel.add(lbl_effect);
     center_panel.add(lbl_cost);


     // Panel inferior con botón y textfield
     
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
     btn_build.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
     btn_build.setFont(new Font("Serif", Font.BOLD, 14));

     bottom_panel.add(field_quantity);
     bottom_panel.add(btn_build);
     


     // Añadimos texto y botón al panel derecho
     right_panel.add(center_panel, BorderLayout.CENTER);
     right_panel.add(bottom_panel, BorderLayout.SOUTH);


     // Añadimos el panel derecho al centro del BuildingPanel
     add(right_panel, BorderLayout.CENTER);
    
     // conectamos el boton de construir 
     btn_build.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        buildBuilding();
    	    }
    	});
    // cargamos informacion de las tarjetas
     loadBuildingData();
    }

    private void loadBuildingData() {
    	
	switch (building_type) {
	
	case "Farm":
		lbl_count.setText("Count: " + civ.getFarm());
		lbl_effect.setText("Effect: Increases food generation by 10%");
		lbl_cost.setText("Food " +FOOD_COST_FARM +" Wood " + WOOD_COST_FARM +" Iron " + IRON_COST_FARM);
        break;
        
	 case "Smithy":
		 lbl_count.setText("Count: " + civ.getSmithy());
         lbl_effect.setText("Effect: Increases iron generation by 10%");
         lbl_cost.setText("Food " +FOOD_COST_SMITHY +" Wood " + WOOD_COST_SMITHY +" Iron " + IRON_COST_SMITHY);
         break;

     case "Magic Tower":
    	 lbl_count.setText("Count: " + civ.getMagicTower());
         lbl_effect.setText("Effect: Unlocks mana generation, magicians, churches and priests");
         lbl_cost.setText("Food " +FOOD_COST_MAGICTOWER +" Wood " + WOOD_COST_MAGICTOWER +" Iron " + IRON_COST_MAGICTOWER);
         break;

     case "Church":
    	 lbl_count.setText("Count: " + civ.getChurch());
         lbl_effect.setText("Effect: Unlocks Priests");
         lbl_cost.setText("Food " +FOOD_COST_CHURCH +" Wood " + WOOD_COST_CHURCH +" Iron " + IRON_COST_CHURCH);
         break;
         
     case "Carpentry":
    	 lbl_count.setText("Count: " + civ.getCarpentry());
    	lbl_effect.setText("Increases wood generation by 10%");
    	lbl_cost.setText("Food " +FOOD_COST_CARPENTRY +" Wood " + WOOD_COST_CARPENTRY +" Iron " + IRON_COST_CARPENTRY);
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