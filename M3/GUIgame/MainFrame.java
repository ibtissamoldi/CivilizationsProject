package GUIgame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import game.Civilization;

public class MainFrame extends JFrame{
	
	public static void main(String[] args) {
	    new MainFrame();
	}
	
	private JPanel main_panel;	

	private TopBarPanel topbar_panel;
	private SideMenuPanel menu_panel;
	
	private  DialogPanel dialog_panel;
	
	private JPanel center_switch_panel;
	
	private ArmyPanel army_panel;
    private BuildingsPanel building_panel;
    private CivilizationPanel civilization_panel;
    private StatsPanel stats_panel;
    private BattlePanel battle_panel;
	
	
	private Civilization civ;
	

    public MainFrame() {
    	
        setTitle("Civilizations Game");
        setBounds(250,100,1000,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        loadIcon();
        
        civ = new Civilization();
        
        civ.setFood(8000);
        civ.setWood(3000);
        civ.setIron(50);
        civ.setMana(0);
        
        createPanels();
        
       
        army_panel = new ArmyPanel(civ);
        building_panel = new BuildingsPanel(civ);
        civilization_panel = new CivilizationPanel();
        stats_panel = new StatsPanel();
        battle_panel = new BattlePanel();
        
        
		
        initializeButtonActions();
        SwitchPanel(civilization_panel);
		
		
        setVisible(true);
    }
    private void createPanels() {
    	main_panel = new JPanel(new BorderLayout());
        main_panel.setBackground(GameColors.BACKGROUND);
        
        
        topbar_panel = new TopBarPanel();
        main_panel.add(topbar_panel, BorderLayout.NORTH);
        
        menu_panel = new SideMenuPanel();
        main_panel.add(menu_panel,BorderLayout.WEST);
        

        center_switch_panel = new JPanel(new BorderLayout());
        center_switch_panel.setBackground(GameColors.PANEL);
        main_panel.add(center_switch_panel,BorderLayout.CENTER);
        
        dialog_panel = new DialogPanel();
        main_panel.add(dialog_panel,BorderLayout.SOUTH);
        
        add(main_panel);
        
   	
    }
    
    private void SwitchPanel(JPanel panel) {
        center_switch_panel.removeAll();
        center_switch_panel.add(panel,BorderLayout.CENTER);
        center_switch_panel.revalidate();
        center_switch_panel.repaint();
    }
    
    
    private void initializeButtonActions() {

        menu_panel.btn_Army.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			SwitchPanel(army_panel);
        		}
        		});
        		
        menu_panel.btn_Buildings.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel(building_panel);
    		}
    		});
        
        menu_panel.btn_civilization.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel(civilization_panel);
    		}
    		});
        
        menu_panel.btn_Stats.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel(stats_panel);
    		}
    		});
        
        menu_panel.btn_Battles.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel(battle_panel);
    		}
    		});
    }

    
    private void loadIcon() {
    	BufferedImage icon_image;

        try {

            icon_image =
                    ImageIO.read(new File("./swords_icon.png"));

            setIconImage(icon_image);

        } catch (IOException e) {

            System.out.println("Error loading icon.");
        }
    }
    
    
    

}




class TopBarPanel extends JPanel {
	
	private JLabel label_food;
    private JLabel label_wood;
    private JLabel label_iron;
    private JLabel label_mana;
    
	public TopBarPanel() {
		setBackground(GameColors.PANEL);
		setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 3));
		
		label_food = new JLabel("Food: 0");
        label_wood = new JLabel("Wood: 0");
        label_iron = new JLabel("Iron: 0");
        label_mana = new JLabel("Mana: 0");
        
        label_food.setForeground(new Color(120, 200, 120));
        label_wood.setForeground(new Color(160, 110, 70));
        label_iron.setForeground(new Color(180, 180, 190));
        label_mana.setForeground(GameColors.STEEL_BLUE);
        
        
        add(label_food);
        add(label_wood);
        add(label_iron);
        add(label_mana);
	}
	
}

class SideMenuPanel extends JPanel {
	
	JButton btn_civilization;
    JButton btn_Army;
    JButton btn_Buildings;
    JButton btn_Stats;
    JButton btn_Battles;
    
    public SideMenuPanel() {
    	setLayout(new GridLayout(5,1,5,5));
        setBackground(GameColors.PANEL);
        setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
        
        btn_civilization = createMenuButton("Civilization");
        btn_civilization.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GameColors.BORDER, 2),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)
                )
        );
        
        
        btn_Army = createMenuButton("Army");
        btn_Buildings = createMenuButton("Buildings");
        btn_Stats = createMenuButton("Stats");
        btn_Battles = createMenuButton("Battles");
        
        
        add(btn_civilization);
        add(btn_Army);
        add(btn_Buildings);
        add(btn_Stats);
        add(btn_Battles);
    }
    
    
    private JButton createMenuButton(String text) {

        JButton btn = new JButton(text);

        btn.setBackground(GameColors.BUTTON);
        btn.setForeground(GameColors.TEXT);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
        btn.setOpaque(true);
        btn.setFont(new Font("Serif", Font.BOLD, 16));
        btn.setMargin(new Insets(20, 20, 20, 20));

        return btn;
    }
}


class DialogPanel extends JPanel {

    public DialogPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.DARK_GRAY);
        setPreferredSize(new Dimension(0, 80));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
    }
}








class ArmyPanel extends JPanel {
	private JTabbedPane tab_army_panel;
    public ArmyPanel(Civilization civ) {
    	setLayout(new BorderLayout());
        setBackground(GameColors.PANEL);
        
		tab_army_panel = new JTabbedPane();

		tab_army_panel.setBackground(GameColors.PANEL);
		tab_army_panel.setForeground(GameColors.GOLD);
		
		tab_army_panel.add("Attack Units", new AttackTabPanel(civ));
		tab_army_panel.add("Defense Units", new DefenseTabPanel(civ));
		tab_army_panel.add("Special Units", new SpecialTabPanel());

        add(tab_army_panel, BorderLayout.CENTER);
    }
}

class BuildingsPanel extends JPanel {

    public BuildingsPanel(Civilization civ) {
        setLayout(new BorderLayout());
        setBackground(GameColors.PANEL);

        GeneralBuildingPanel generalBuildingPanel = new GeneralBuildingPanel(civ);

        add(generalBuildingPanel, BorderLayout.CENTER);
    }
}



class CivilizationPanel  extends JPanel {
	public CivilizationPanel() {

        setBackground(GameColors.PANEL);
    }
}



class StatsPanel  extends JPanel {
	public StatsPanel() {

        setBackground(GameColors.PANEL);
    }
}



class BattlePanel  extends JPanel {
	public BattlePanel() {

        setBackground(GameColors.PANEL);
    }
}
