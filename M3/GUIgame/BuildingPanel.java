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

import M3.exceptions.ResourceException;
import M3.game.Civilization;
import M3.interfaces.Variables;

public class BuildingPanel extends JPanel implements Variables{
	
	private JLabel lbl_image;
	
	private JLabel lbl_count,lbl_effect,lbl_cost;

   
    private JButton btn_build;

    private Civilization civ;
    
    private String building_type;
    
    public BuildingPanel(Civilization civ, String building_type) {
    	this.civ = civ;
    	this.building_type= building_type;
    	
    	setLayout(new BorderLayout(15, 10));
    	

	    setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2),
	    		building_type,0,0,new Font("Serif", Font.BOLD, 16),GameColors.GOLD));    
        setBackground(GameColors.PANEL);
    	
        
    

    // Imagen del edificio
    
    ImageIcon image = new ImageIcon(getImagePath());
    
    lbl_image = new JLabel(image);
   
    lbl_image.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));

    add(lbl_image, BorderLayout.WEST);


     // Panel derecho: contiene texto y botón
     JPanel right_panel = new JPanel(new BorderLayout());
     right_panel.setBackground(GameColors.PANEL);


     // Panel central con textos
     JPanel center_panel = new JPanel(new GridLayout(3, 1));
     center_panel.setBackground(GameColors.PANEL);

     lbl_count = new JLabel("Count: 0");
     lbl_effect = new JLabel();
     lbl_cost = new JLabel();

     lbl_count.setForeground(GameColors.TEXT);
     lbl_effect.setForeground(GameColors.TEXT);
     lbl_cost.setForeground(GameColors.TEXT);

     center_panel.add(lbl_count);
     center_panel.add(lbl_effect);
     center_panel.add(lbl_cost);


     // Panel inferior con botón
     JPanel bottom_panel = new JPanel();
     bottom_panel.setBackground(GameColors.PANEL);

     btn_build = new JButton("Build");
     btn_build.setBackground(GameColors.BUTTON);
     btn_build.setForeground(GameColors.TEXT);

     btn_build.setFocusPainted(false); 
     btn_build.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
     btn_build.setFont(new Font("Serif", Font.BOLD, 14));

     bottom_panel.add(btn_build);


     // Añadimos texto y botón al panel derecho
     right_panel.add(center_panel, BorderLayout.CENTER);
     right_panel.add(bottom_panel, BorderLayout.SOUTH);


     // Añadimos el panel derecho al centro del BuildingPanel
     add(right_panel, BorderLayout.CENTER);
     loadBuildingData();
    
    	
    }

    private void loadBuildingData() {
    	
	switch (building_type) {
	
	case "Farm":
		lbl_effect.setText("Effect: + Iron generation");
		lbl_cost.setText("Cost: Food 5000 Wood 10000 Iron 12000 Mana 0");
        break;
        
	 case "Smithy":
         lbl_effect.setText("Effect: + Iron generation");
         lbl_cost.setText("Cost: Food 5000 Wood 10000 Iron 12000 Mana 0");
         break;

     case "Magic Tower":
         lbl_effect.setText("Effect: Generates Mana / unlocks Magicians");
         lbl_cost.setText("Cost: Food 5000 Wood 10000 Iron 12000 Mana 0");
         break;

     case "Church":
         lbl_effect.setText("Effect: Unlocks Priests");
         lbl_cost.setText("Cost: Food 5000 Wood 10000 Iron 12000 Mana 0");
         break;
         
     case "Carpentry":
    	lbl_effect.setText("Effect: + Iron generation");
 		lbl_cost.setText("Cost: Food 5000 Wood 10000 Iron 12000 Mana 0");
         break;
	}
    			
    			
    }
		
	

	private void buildBuilding() throws ResourceException {
		
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

} 
		
	

	private String getImagePath() {
        switch (building_type) {
            case "Farm":
                return "./M3/farm.png";

            case "Carpentry":
                return "./images/carpentry.png";

            case "Smithy":
                return "./images/smithy.png";

            case "Magic Tower":
                return "./images/magic_tower.png";

            case "Church":
                return "./images/church.png";

            default:
                return "./images/default.png";
        }
    }

}

// GENERAL JUNTAR TODAS LA TARJETAS DE CADA EDIFICIO
class GeneralBuildingPanel extends JPanel{
	
	private BuildingPanel farm_panel,carpentry_panel,smithy_panel,magic_tower_panel,church_panel;
	private Civilization civ;

	public GeneralBuildingPanel(Civilization civ) {
        this.civ = civ;

        setLayout(new GridLayout(3, 2, 15, 15));
        setBackground(GameColors.BACKGROUND);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        farm_panel = new BuildingPanel(civ, "Farm");
        carpentry_panel = new BuildingPanel(civ, "Carpentry");
        smithy_panel = new BuildingPanel(civ, "Smithy");
        magic_tower_panel = new BuildingPanel(civ, "Magic Tower");
        church_panel = new BuildingPanel(civ, "Church");

        add(farm_panel);
        add(carpentry_panel);
        add(smithy_panel);
        add(magic_tower_panel);
        add(church_panel);
    }
}
	
	

