package M3.GUIgame;

import java.awt.BorderLayout;
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

import M3.exceptions.ResourceException;
import M3.game.Civilization;
import M3.interfaces.Variables;

public class TechnologyPanel extends JPanel implements Variables {

    private JLabel lbl_image;

    private JLabel lbl_level;
    private JLabel lbl_cost;

    private JButton btn_upgrade;

    private Civilization civ;

    private String technology_type;

    public TechnologyPanel(Civilization civ, String technology_type) {

        this.civ = civ;
        this.technology_type = technology_type;

        setLayout(new BorderLayout(10, 10));

        setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(GameColors.BORDER, 2),
                technology_type,
                0,
                0,
                new Font("Serif", Font.BOLD, 16),
                GameColors.GOLD
        ));

        setBackground(GameColors.PANEL);

        // =========================
        // IMAGEN
        // =========================

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
            lbl_image.setHorizontalAlignment(JLabel.CENTER);
            add(lbl_image, BorderLayout.CENTER);
        }

        // =========================
        // PANEL DE INFORMACIÓN
        // =========================

        JPanel info_panel = new JPanel(new GridLayout(2, 1, 5, 10));
        info_panel.setBackground(GameColors.PANEL);

        lbl_level = new JLabel();
        lbl_cost = new JLabel();

        lbl_level.setForeground(GameColors.TEXT);
        lbl_cost.setForeground(GameColors.TEXT);
        
        lbl_level.setHorizontalAlignment(JLabel.CENTER);
        lbl_cost.setHorizontalAlignment(JLabel.CENTER);
        
        lbl_level.setFont(new Font("Serif", Font.BOLD, 15));
        lbl_cost.setFont(new Font("Serif", Font.BOLD, 14));

        info_panel.add(lbl_level);
        info_panel.add(lbl_cost);

        // =========================
        // BOTÓN
        // =========================

        JPanel bottom_panel = new JPanel();
        bottom_panel.setBackground(GameColors.PANEL);

        btn_upgrade = new JButton("UPGRADE");
        btn_upgrade.setBackground(GameColors.BUTTON);
        btn_upgrade.setForeground(GameColors.TEXT);
        btn_upgrade.setFocusPainted(false);
        btn_upgrade.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
        btn_upgrade.setFont(new Font("Serif", Font.BOLD, 14));

        bottom_panel.add(btn_upgrade);
        
       
        

        // =========================
        // PANEL INFERIOR
        // =========================

        JPanel south_panel = new JPanel(new BorderLayout());
        south_panel.setBackground(GameColors.PANEL);

        south_panel.add(info_panel, BorderLayout.CENTER);
        south_panel.add(bottom_panel, BorderLayout.SOUTH);

        add(south_panel, BorderLayout.SOUTH);

        // =========================
        // ACTION LISTENER
        // =========================

        btn_upgrade.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                upgradeTechnology();
            }
        });

        loadTechnologyData();
    }

    private void loadTechnologyData() {

        switch (technology_type) {

            case "ATTACK TECHNOLOGY":
                lbl_level.setText("Level: " + civ.getTechnologyAttack());
                lbl_cost.setText("Cost: "  + " iron");
                break;

            case "DEFENSE TECHNOLOGY":
                lbl_level.setText("Level: " + civ.getTechnologyDefense());
                lbl_cost.setText("Cost: " + " iron"  );
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

            default:
                return "";
        }
    }
}


class GeneralTechnologyPanel extends JPanel {

    private TechnologyPanel attack_panel;
    private TechnologyPanel defense_panel;

    private Civilization civ;

    public GeneralTechnologyPanel(Civilization civ) {

        this.civ = civ;

        setLayout(new BorderLayout());
        setBackground(GameColors.BACKGROUND);

       

        JPanel cards_panel = new JPanel(new GridLayout(1, 2, 15, 15));
        cards_panel.setBackground(GameColors.BACKGROUND);
        cards_panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        attack_panel = new TechnologyPanel(civ, "ATTACK TECHNOLOGY");
        defense_panel = new TechnologyPanel(civ, "DEFENSE TECHNOLOGY");

        cards_panel.add(attack_panel);
        cards_panel.add(defense_panel);

        add(cards_panel, BorderLayout.CENTER);
    }
}