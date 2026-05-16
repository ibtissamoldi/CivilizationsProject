package M3.GUIgame.Battlepanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import M3.GUIgame.GameColors;

public class UnitCardPanel extends JPanel{
	private JLabel lbl_name;
    private JLabel lbl_image;
    private JLabel lbl_count;
    private JTextField quantity;
    private JButton recruit_btn;
    
    public UnitCardPanel(String unitName, boolean isCivilization, boolean recruit) {
    	
    	 setLayout(new BorderLayout());
         setBackground(GameColors.PANEL_LIGHT);
         setPreferredSize(new Dimension(280,220));
         setBorder(BorderFactory.createCompoundBorder(
        		    BorderFactory.createLineBorder(GameColors.BORDER,2),
        		    BorderFactory.createEmptyBorder(15,15,15,15)
        		));

         lbl_name = new JLabel(displayName(unitName));
         lbl_name.setFont(new Font("Serif", Font.BOLD, 12));
         lbl_name.setHorizontalAlignment(JLabel.CENTER);
         lbl_name.setForeground(GameColors.TEXT);
         
         String imagePath = buildImagePath(unitName, isCivilization);

         
         Image img = new ImageIcon(imagePath).getImage();
         Image scaled = img.getScaledInstance(60, 60, Image.SCALE_SMOOTH);
         ImageIcon icon =new ImageIcon(scaled);
         
         lbl_image = new JLabel(icon);
         lbl_image.setHorizontalAlignment(JLabel.CENTER);
         lbl_image.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));

         lbl_count = new JLabel("Count: 0");
         lbl_count.setHorizontalAlignment(JLabel.CENTER);
         lbl_count.setForeground(GameColors.TEXT);

         add(lbl_name, BorderLayout.NORTH);
         add(lbl_image, BorderLayout.CENTER);

         JPanel south = new JPanel(new GridLayout(3,1));
         south.setOpaque(false);

         south.add(lbl_count);
         
         quantity = new JTextField("1", 5); 
         quantity.setBackground(GameColors.INPUT_BG);
         quantity.setForeground(GameColors.TEXT);
         quantity.setBorder(
         	    BorderFactory.createEmptyBorder(5,8,5,8)
         	);
         if(recruit){
             recruit_btn = new JButton("+");
             recruit_btn.setBackground(GameColors.BUTTON);
             recruit_btn.setForeground(GameColors.TEXT);
             recruit_btn.setFocusPainted(false);
             recruit_btn.setMargin(new Insets(2,10,2,10));
             recruit_btn.setBorder(
            		    BorderFactory.createLineBorder(GameColors.BORDER)
            		);
             south.add(quantity);
             south.add(recruit_btn);
             
         }
         
         if(!isCivilization){
        	 setBackground(GameColors.ENEMY_PANEL);

        	 setBorder(
        	     BorderFactory.createLineBorder(
        	         GameColors.ENEMY_BORDER,2
        	     )
        	 );
        	}

         add(south, BorderLayout.SOUTH);
         
         
         addMouseListener(new MouseAdapter() {

        	    public void mouseEntered(MouseEvent e) {
        	        setBackground(GameColors.BUTTON_HOVER);
        	    }

        	    public void mouseExited(MouseEvent e) {
        	        setBackground(GameColors.PANEL_LIGHT);
        	        if(!isCivilization) {
        	        	setBackground(GameColors.ENEMY_PANEL);
        	        }
        	    }
        	});
     }
    
    private String displayName(String unit){

        switch(unit){

            case "RocketLauncherTower":
                return "Rocket";

            case "ArrowTower":
                return "Arrow";

            default:
                return unit;
        }
    }
    
    private String buildImagePath(String unitName, boolean civ){

        String side;

        if(civ)
            side = "_civ.png";
        else
            side = "_enemy.png";

        return "./M3/images/" +unitName.toLowerCase() +side;
    }

     public void setCount(int count){
         lbl_count.setText("Count: " + count);
     }
    

}
