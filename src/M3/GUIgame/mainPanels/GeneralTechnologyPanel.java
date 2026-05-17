package M3.GUIgame.mainPanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import M3.GUIgame.GameColors;
import M3.GUIgame.GameLog;
import M3.GUIgame.MainFrame;
import M3.exceptions.ResourceException;
import M3.game.Civilization;
import M3.interfaces.Variables;






public class GeneralTechnologyPanel extends JPanel {

    private TechnologyPanel attack_panel;
    private TechnologyPanel defense_panel;

   

    public GeneralTechnologyPanel(Civilization civ, MainFrame frame) {

        

        setLayout(new BorderLayout());
        setBackground(GameColors.BACKGROUND);

       

        JPanel cards_panel = new JPanel(new GridLayout(1, 2, 15, 15));
        cards_panel.setBackground(GameColors.BACKGROUND);
        cards_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        attack_panel = new TechnologyPanel(civ, "ATTACK TECHNOLOGY",frame);
        defense_panel = new TechnologyPanel(civ, "DEFENSE TECHNOLOGY",frame);

        cards_panel.add(attack_panel);
        cards_panel.add(defense_panel);

        add(cards_panel, BorderLayout.CENTER);
    }
}



class TechnologyPanel extends JPanel implements Variables {

    private JLabel lbl_image;

    private JLabel lbl_level;

    private JLabel lbl_ironcost;
    private JLabel lbl_woodcost;

    private JButton btn_upgrade;

    private Civilization civ;

    private String technology_type;
    
    private MainFrame frame;

    public TechnologyPanel(Civilization civ, String technology_type,MainFrame frame) {

        this.civ = civ;
        this.technology_type = technology_type;
        this.frame=frame;

        setLayout(new BorderLayout());
        
  
        JLabel title = new JLabel(technology_type);
        title.setForeground(GameColors.TEXT);
        title.setFont(new Font("Serif", Font.BOLD, 15));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        
        
        add(title,BorderLayout.NORTH);


        setBackground(GameColors.PANEL);

    

        try {
            String image_path = getImagePath();

            File image_file = new File(image_path);

            BufferedImage original_image = ImageIO.read(image_file);

            Image scaled_image = original_image.getScaledInstance(350,350,Image.SCALE_SMOOTH
            );

            ImageIcon image = new ImageIcon(scaled_image);

            lbl_image = new JLabel(image);
         
            add(lbl_image, BorderLayout.CENTER);

        } catch (IOException e) {

            lbl_image = new JLabel("Image not found");
            lbl_image.setForeground(GameColors.TEXT);
            add(lbl_image, BorderLayout.CENTER);
        }

 

        JPanel info_panel = new JPanel(new GridLayout(3, 2, 25, 10));
        info_panel.setBackground(GameColors.PANEL);
        info_panel.setBorder(
        	    BorderFactory.createEmptyBorder(10, 25, 10, 25)
        	);

        Color labelColor = new Color(190,180,160);
        Color valueColor = new Color(245,230,190);

        Font statFont = new Font("Arial", Font.BOLD, 13);

        JLabel lbl_level_text = new JLabel("Level:");
        lbl_level_text.setForeground(labelColor);
        lbl_level_text.setFont(statFont);

        lbl_level = new JLabel();
        lbl_level.setForeground(valueColor);
        lbl_level.setFont(statFont);

        JLabel lbl_iron_text = new JLabel("Iron Cost:");
        lbl_iron_text.setForeground(labelColor);
        lbl_iron_text.setFont(statFont);

        lbl_ironcost = new JLabel();
        lbl_ironcost.setForeground(GameColors.IRON);
        lbl_ironcost.setFont(statFont);

        JLabel lbl_wood_text = new JLabel("Wood Cost:");
        lbl_wood_text.setForeground(labelColor);
        lbl_wood_text.setFont(statFont);

        lbl_woodcost = new JLabel();
        lbl_woodcost.setForeground(GameColors.WOOD);
        lbl_woodcost.setFont(statFont);

        info_panel.add(lbl_level_text);
        info_panel.add(lbl_level);

        info_panel.add(lbl_iron_text);
        info_panel.add(lbl_ironcost);

        info_panel.add(lbl_wood_text);
        info_panel.add(lbl_woodcost);



        JPanel bottom_panel = new JPanel();
        bottom_panel.setBackground(GameColors.PANEL);

        btn_upgrade = new JButton("UPGRADE");
        btn_upgrade.setBackground(GameColors.BUTTON);
        btn_upgrade.setForeground(GameColors.TEXT);
        btn_upgrade.setFocusPainted(false);
        btn_upgrade.setFont(new Font("Serif", Font.BOLD, 14));

        bottom_panel.add(btn_upgrade);
        
       


        JPanel south_panel = new JPanel(new BorderLayout());
        south_panel.setBackground(GameColors.PANEL);

        south_panel.add(info_panel, BorderLayout.CENTER);
        south_panel.add(bottom_panel, BorderLayout.SOUTH);

        add(south_panel, BorderLayout.SOUTH);

      

        btn_upgrade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upgradeTechnology();
                frame.saveGame();
                frame.RefreshInterface();
            }
        });

        loadTechnologyData();
        frame.RefreshInterface();
    }

    private void loadTechnologyData() {

        switch (technology_type) {

            case "ATTACK TECHNOLOGY":
            	 lbl_level.setText(String.valueOf(civ.getTechnologyAttack()));
            	 lbl_ironcost.setText(String.valueOf((int)(UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST*(UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST/100.0 * civ.getTechnologyAttack() + 1))));
            	 lbl_woodcost.setText(String.valueOf((int)(UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST*(UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST/100.0 *civ.getTechnologyAttack() + 1))));
                 break;
                

            case "DEFENSE TECHNOLOGY":
                lbl_level.setText(String.valueOf(civ.getTechnologyDefense()));
                lbl_ironcost.setText(String.valueOf((int)(UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST*(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST/100.0 * civ.getTechnologyDefense() + 1))));
                lbl_woodcost.setText(String.valueOf((int)(UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST*(UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST/100.0 * civ.getTechnologyDefense() + 1))));
                break;
        }
    }

    private void upgradeTechnology() {

        GameLog.log.clear();

        try {

            switch (technology_type) {

                case "ATTACK TECHNOLOGY":
                    civ.upgradeTechnologyAttack();
                    GameLog.info("Attack Technology upgraded!");
                    break;

                case "DEFENSE TECHNOLOGY":
                    civ.upgradeTechnologyDefense();
                    GameLog.info("Defense Technology upgraded!");
                    break;
            }

        } catch (ResourceException e) {
            GameLog.error(e.getMessage());
        }

        loadTechnologyData();
    }

    private String getImagePath() {

        switch (technology_type) {

            case "ATTACK TECHNOLOGY":
                return "./M3/images/attack_technology.png";

            case "DEFENSE TECHNOLOGY":
                return "./M3/images/defense_technology.png";
 
        }
        return "";
    }
}







