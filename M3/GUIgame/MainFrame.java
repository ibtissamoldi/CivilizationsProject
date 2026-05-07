package GUIgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements ActionListener {

    private JPanel mainPanel, northPanel, westPanel, centerPanel;
    private JLabel food, wood, iron, mana;
    private JButton civilization, army, buildings, stats, battles;

    private JPanel civilizationPanel, armyPanel, buildingsPanel, statsPanel, battlesPanel;

    private BufferedImage icon_image;

    public MainFrame() {
        setTitle("Civilizations Game");
        setBounds(250, 100, 1000, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        try {
            icon_image = ImageIO.read(new File("./swords.png"));
            setIconImage(icon_image);
        } catch (IOException e) {
            System.out.println("We have some problems trying to add the icon image");
        }

        initComponents();
        setVisible(true);
    }

    public void initComponents() {
        // Crear paneles principales
        createMainPanel();
        createWestPanel();
        createnorthPanel();
        createCenterPanel();

        // Crear paneles de contenido
        createCivilizationPanel();
        createArmyPanel();
        createBuildingsPanel();
        createStatsPanel();
        createBattlesPanel();

        // Añadir paneles al principal
        mainPanel.add(northPanel, BorderLayout.NORTH);
        mainPanel.add(westPanel, BorderLayout.WEST);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Mostrar un panel inicial
        showCivilizationPanel();
    }

    public void createMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.BLACK);
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
    }

    public void createnorthPanel() {
        northPanel = new JPanel();
        northPanel.setBackground(Color.GRAY);
        northPanel.setPreferredSize(new Dimension(0, 30));

        food = new JLabel("Food: 0");
        wood = new JLabel("Wood: 0");
        iron = new JLabel("Iron: 0");
        mana = new JLabel("Mana: 0");

        northPanel.add(food);
        northPanel.add(wood);
        northPanel.add(iron);
        northPanel.add(mana);
    }

    public void createWestPanel() {
        westPanel = new JPanel();
        westPanel.setBackground(Color.GRAY);
        westPanel.setPreferredSize(new Dimension(150, 0));
        westPanel.setLayout(new GridLayout(5, 1, 5, 5));

        civilization = new JButton("Civilization");
        civilization.setBackground(Color.BLACK);
        civilization.setForeground(Color.WHITE);
        civilization.addActionListener(this);

        army = new JButton("Army");
        army.setBackground(Color.BLACK);
        army.setForeground(Color.WHITE);
        army.addActionListener(this);

        buildings = new JButton("Buildings");
        buildings.setBackground(Color.BLACK);
        buildings.setForeground(Color.WHITE);
        buildings.addActionListener(this);

        stats = new JButton("Stats");
        stats.setBackground(Color.BLACK);
        stats.setForeground(Color.WHITE);
        stats.addActionListener(this);

        battles = new JButton("Battles");
        battles.setBackground(Color.BLACK);
        battles.setForeground(Color.WHITE);
        battles.addActionListener(this);

        westPanel.add(civilization);
        westPanel.add(army);
        westPanel.add(buildings);
        westPanel.add(stats);
        westPanel.add(battles);
    }

    public void createCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.YELLOW);

        // IMPORTANTE: para que el panel que metas dentro ocupe todo el centro
        centerPanel.setLayout(new BorderLayout());
    }

    public void createCivilizationPanel() {
        civilizationPanel = new JPanel();
        civilizationPanel.setBackground(Color.BLUE);
        civilizationPanel.add(new JLabel("Civilization Panel"));
    }

    public void createArmyPanel() {
        armyPanel = new JPanel();
        armyPanel.setBackground(Color.RED);
        armyPanel.add(new JLabel("Army Panel"));
    }

    public void createBuildingsPanel() {
        buildingsPanel = new JPanel();
        buildingsPanel.setBackground(Color.GREEN);
        buildingsPanel.add(new JLabel("Buildings Panel"));
    }

    public void createStatsPanel() {
        statsPanel = new JPanel();
        statsPanel.setBackground(Color.ORANGE);
        statsPanel.add(new JLabel("Stats Panel"));
    }

    public void createBattlesPanel() {
        battlesPanel = new JPanel();
        battlesPanel.setBackground(Color.PINK);
        battlesPanel.add(new JLabel("Battles Panel"));
    }

  
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (source == civilization) {
            showCivilizationPanel();
        } else if (source == army) {
            showArmyPanel();
        } else if (source == buildings) {
            showBuildingsPanel();
        } else if (source == stats) {
            showStatsPanel();
        } else if (source == battles) {
            showBattlesPanel();
        }
    }

    public void showCivilizationPanel() {
        changeCenterPanel(civilizationPanel);
    }

    public void showArmyPanel() {
        changeCenterPanel(armyPanel);
    }

    public void showBuildingsPanel() {
        changeCenterPanel(buildingsPanel);
    }

    public void showStatsPanel() {
        changeCenterPanel(statsPanel);
    }

    public void showBattlesPanel() {
        changeCenterPanel(battlesPanel);
    }

    public void changeCenterPanel(JPanel panel) {
        centerPanel.removeAll();
        centerPanel.add(panel, BorderLayout.CENTER);
        centerPanel.revalidate();
        centerPanel.repaint();
    }
}