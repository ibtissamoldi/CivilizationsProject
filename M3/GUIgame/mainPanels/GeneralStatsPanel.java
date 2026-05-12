package M3.GUIgame.mainPanels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import M3.GUIgame.GameColors;
import M3.game.Civilization;
import M3.interfaces.Variables;

public class GeneralStatsPanel extends JPanel implements Variables {

    private Civilization civ;

    // TECHNOLOGY
    private JLabel lbl_attackTechnology;
    private JLabel lbl_defenseTechnology;

    // BUILDINGS
    private JLabel lbl_farm;
    private JLabel lbl_smithy;
    private JLabel lbl_carpentry;
    private JLabel lbl_magicTower;
    private JLabel lbl_church;

    // DEFENSES
    private JLabel lbl_arrowTower;
    private JLabel lbl_catapult;
    private JLabel lbl_rocketLauncher;

    // ATTACK UNITS
    private JLabel lbl_swordsman;
    private JLabel lbl_spearman;
    private JLabel lbl_crossbow;
    private JLabel lbl_cannon;

    // SPECIAL UNITS
    private JLabel lbl_magician;
    private JLabel lbl_priest;

    // RESOURCES
    private JLabel lbl_food;
    private JLabel lbl_wood;
    private JLabel lbl_iron;
    private JLabel lbl_mana;

    // GENERATION RESOURCES
    private JLabel lbl_foodGeneration;
    private JLabel lbl_woodGeneration;
    private JLabel lbl_ironGeneration;
    private JLabel lbl_manaGeneration;

    public GeneralStatsPanel(Civilization civ) {

        this.civ = civ;

        setLayout(new BorderLayout());
        setBackground(GameColors.BACKGROUND);

        JLabel title = new JLabel("CIVILIZATION STATS");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(GameColors.GOLD);
        title.setFont(new Font("Serif", Font.BOLD, 28));
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        add(title, BorderLayout.NORTH);

        JPanel content_panel = new JPanel();
        content_panel.setLayout(new GridLayout(0, 1, 0, 25));
        content_panel.setBackground(GameColors.BACKGROUND);

        // =========================
        // TECHNOLOGY
        // =========================

        content_panel.add(createSectionTitle("TECHNOLOGY"));

        lbl_attackTechnology = new JLabel();
        lbl_defenseTechnology = new JLabel();

        content_panel.add(createStatCard(
                "Attack Technology",
                lbl_attackTechnology,
                "./M3/images/attack_technology.png",
                80,
                80
        ));

        content_panel.add(createStatCard(
                "Defense Technology",
                lbl_defenseTechnology,
                "./M3/images/defense_technology.png",
                80,
                80
        ));

        // =========================
        // BUILDINGS
        // =========================

        content_panel.add(createSectionTitle("BUILDINGS"));

        lbl_farm = new JLabel();
        lbl_smithy = new JLabel();
        lbl_carpentry = new JLabel();
        lbl_magicTower = new JLabel();
        lbl_church = new JLabel();

        content_panel.add(createStatCard("Farm", lbl_farm, "./M3/images/farm.png", 80, 80));
        content_panel.add(createStatCard("Smithy", lbl_smithy, "./M3/images/smithy.png", 80, 80));
        content_panel.add(createStatCard("Carpentry", lbl_carpentry, "./M3/images/carpentry.png", 80, 80));
        content_panel.add(createStatCard("Magic Tower", lbl_magicTower, "./M3/images/magic_tower.png", 80, 80));
        content_panel.add(createStatCard("Church", lbl_church, "./M3/images/church.png", 80, 80));

        // =========================
        // DEFENSES
        // =========================

        content_panel.add(createSectionTitle("DEFENSES"));

        lbl_arrowTower = new JLabel();
        lbl_catapult = new JLabel();
        lbl_rocketLauncher = new JLabel();

        content_panel.add(createStatCard("Arrow Tower", lbl_arrowTower, "./M3/images/arrowTower.png", 80, 80));
        content_panel.add(createStatCard("Catapult", lbl_catapult, "./M3/images/catapult.png", 80, 80));
        content_panel.add(createStatCard("Rocket Launcher", lbl_rocketLauncher, "./M3/images/rocketLauncherTower.png", 80, 80));

        // =========================
        // ATTACK UNITS
        // =========================

        content_panel.add(createSectionTitle("ATTACK UNITS"));

        lbl_swordsman = new JLabel();
        lbl_spearman = new JLabel();
        lbl_crossbow = new JLabel();
        lbl_cannon = new JLabel();

        content_panel.add(createStatCard("Swordsman", lbl_swordsman, "./M3/images/swordsman.png", 80, 80));
        content_panel.add(createStatCard("Spearman", lbl_spearman, "./M3/images/spearman.png", 80, 80));
        content_panel.add(createStatCard("Crossbow", lbl_crossbow, "./M3/images/crossbow.png", 80, 80));
        content_panel.add(createStatCard("Cannon", lbl_cannon, "./M3/images/cannon.png", 80, 80));

        // =========================
        // SPECIAL UNITS
        // =========================

        content_panel.add(createSectionTitle("SPECIAL UNITS"));

        lbl_magician = new JLabel();
        lbl_priest = new JLabel();

        content_panel.add(createStatCard("Mague", lbl_magician, "./M3/images/magician.png", 80, 80));
        content_panel.add(createStatCard("Priest", lbl_priest, "./M3/images/priest.png", 120, 80));

        // =========================
        // RESOURCES
        // =========================

        content_panel.add(createSectionTitle("RESOURCES"));

        lbl_food = new JLabel();
        lbl_wood = new JLabel();
        lbl_iron = new JLabel();
        lbl_mana = new JLabel();

        content_panel.add(createStatCard("Food", lbl_food, "./M3/images/food.png", 30, 30));
        content_panel.add(createStatCard("Wood", lbl_wood, "./M3/images/wood.png", 30, 30));
        content_panel.add(createStatCard("Iron", lbl_iron, "./M3/images/iron.png", 30, 30));
        content_panel.add(createStatCard("Mana", lbl_mana, "./M3/images/mana.png", 30, 30));

        // =========================
        // GENERATION RESOURCES
        // =========================

        content_panel.add(createSectionTitle("GENERATION RESOURCES"));

        lbl_foodGeneration = new JLabel();
        lbl_woodGeneration = new JLabel();
        lbl_ironGeneration = new JLabel();
        lbl_manaGeneration = new JLabel();

        content_panel.add(createStatCard("Food Generation", lbl_foodGeneration, "./M3/images/food.png", 30, 30));
        content_panel.add(createStatCard("Wood Generation", lbl_woodGeneration, "./M3/images/wood.png", 30, 30));
        content_panel.add(createStatCard("Iron Generation", lbl_ironGeneration, "./M3/images/iron.png", 30, 30));
        content_panel.add(createStatCard("Mana Generation", lbl_manaGeneration, "./M3/images/mana.png", 30, 30));

        JScrollPane scrollPane = new JScrollPane(content_panel);
        add(scrollPane, BorderLayout.CENTER);

        // Cargamos los datos iniciales
        loadStatsData();

        // Actualiza automáticamente cada medio segundo
        Timer timer = new Timer(500, e -> loadStatsData());
        timer.start();
    }

    private JLabel createSectionTitle(String text) {

        JLabel section = new JLabel(text);
        section.setHorizontalAlignment(SwingConstants.CENTER);
        section.setForeground(GameColors.GOLD);
        section.setFont(new Font("Serif", Font.BOLD, 20));

        return section;
    }

    private JPanel createStatCard(String title, JLabel lbl_value, String imagePath, int xDimension, int yDimension) {

        JPanel card = new JPanel();
        card.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));

        card.setBackground(GameColors.PANEL);
        card.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 1));

        JLabel lbl_image;

        try {

            File image_file = new File(imagePath);
            BufferedImage original_image = ImageIO.read(image_file);

            Image scaled_image = original_image.getScaledInstance(
                    xDimension,
                    yDimension,
                    Image.SCALE_SMOOTH
            );

            ImageIcon image = new ImageIcon(scaled_image);
            lbl_image = new JLabel(image);

        } catch (IOException e) {

            lbl_image = new JLabel("Image not found");
            lbl_image.setForeground(GameColors.TEXT);
        }

        JLabel lbl_title = new JLabel(title);
        lbl_title.setForeground(GameColors.TEXT);
        lbl_title.setFont(new Font("Serif", Font.BOLD, 20));

        lbl_value.setForeground(GameColors.GOLD);
        lbl_value.setFont(new Font("Serif", Font.BOLD, 28));

        card.add(lbl_image);
        card.add(lbl_title);
        card.add(lbl_value);

        return card;
    }

    public void loadStatsData() {

        // TECHNOLOGY
        lbl_attackTechnology.setText(String.valueOf(civ.getTechnologyAttack()));
        lbl_defenseTechnology.setText(String.valueOf(civ.getTechnologyDefense()));

        // BUILDINGS
        lbl_farm.setText(String.valueOf(civ.getFarm()));
        lbl_smithy.setText(String.valueOf(civ.getSmithy()));
        lbl_carpentry.setText(String.valueOf(civ.getCarpentry()));
        lbl_magicTower.setText(String.valueOf(civ.getMagicTower()));
        lbl_church.setText(String.valueOf(civ.getChurch()));

        // DEFENSES
        lbl_arrowTower.setText(String.valueOf(getArmySize(4)));
        lbl_catapult.setText(String.valueOf(getArmySize(5)));
        lbl_rocketLauncher.setText(String.valueOf(getArmySize(6)));

        // ATTACK UNITS
        lbl_swordsman.setText(String.valueOf(getArmySize(0)));
        lbl_spearman.setText(String.valueOf(getArmySize(1)));
        lbl_crossbow.setText(String.valueOf(getArmySize(2)));
        lbl_cannon.setText(String.valueOf(getArmySize(3)));

        // SPECIAL UNITS
        lbl_magician.setText(String.valueOf(getArmySize(7)));
        lbl_priest.setText(String.valueOf(getArmySize(8)));

        // RESOURCES
        lbl_food.setText(String.valueOf(civ.getFood()));
        lbl_wood.setText(String.valueOf(civ.getWood()));
        lbl_iron.setText(String.valueOf(civ.getIron()));
        lbl_mana.setText(String.valueOf(civ.getMana()));

        // GENERATION RESOURCES
        lbl_foodGeneration.setText(String.valueOf(getFoodGeneration()));
        lbl_woodGeneration.setText(String.valueOf(getWoodGeneration()));
        lbl_ironGeneration.setText(String.valueOf(getIronGeneration()));
        lbl_manaGeneration.setText(String.valueOf(getManaGeneration()));
    }

    private int getArmySize(int index) {

        if (civ.getArmy() == null || civ.getArmy()[index] == null) {
            return 0;
        }

        return civ.getArmy()[index].size();
    }

    private int getFoodGeneration() {

        return CIVILIZATION_FOOD_GENERATED
                + civ.getFarm() * CIVILIZATION_FOOD_GENERATED_PER_FARM;
    }

    private int getWoodGeneration() {

        return CIVILIZATION_WOOD_GENERATED
                + civ.getCarpentry() * CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY;
    }

    private int getIronGeneration() {

        return CIVILIZATION_IRON_GENERATED
                + civ.getSmithy() * CIVILIZATION_IRON_GENERATED_PER_SMITHY;
    }

    private int getManaGeneration() {

        return civ.getMagicTower() * CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER;
    }
}