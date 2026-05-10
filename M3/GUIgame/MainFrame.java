package M3.GUIgame;


import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import M3.game.Civilization;

public class MainFrame extends JFrame{
	
	public static void main(String[] args) {
	    new MainFrame();
	}
	
	private JPanel main_panel;	

	private TopBarPanel topbar_panel;
	private SideMenuPanel menu_panel;
	
	private JPanel center_switch_panel;
	
	private ArmyPanel army_panel;
    private BuildingsPanel building_panel;
    private CivilizationPanel civilization_panel;
    private TechnologyPanel technology_panel;
    private StatsPanel stats_panel;
    private BattlePanel battle_panel;
	
    private DialogPanel dialog_panel;
    
	private Civilization civ;
	

    public MainFrame() {
    	
        setTitle("Civilizations Game");
        setBounds(250,100,1000,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        loadIcon();
        
        civ = new Civilization();
        
        civ.setFood(10000);
        civ.setWood(13000);
        civ.setIron(100);
        civ.setMana(0);
        
        createPanels();
        
       
        army_panel = new ArmyPanel(civ);
        building_panel = new BuildingsPanel(civ);
        civilization_panel = new CivilizationPanel();
        technology_panel = new TechnologyPanel();
        stats_panel = new StatsPanel();
        battle_panel = new BattlePanel();
        
        
		
        initializeButtonActions();
        SwitchPanel(civilization_panel);
        
        //dialog_panel.AddMessage("Welcome to Our Civilization!");

		
        add(main_panel);
        setVisible(true);
    }
    private void createPanels() {
    	main_panel = new JPanel(new BorderLayout());
        main_panel.setBackground(GameColors.BACKGROUND);
        
        
        topbar_panel = new TopBarPanel();
        main_panel.add(topbar_panel, BorderLayout.NORTH);
        
        menu_panel = new SideMenuPanel();
        main_panel.add(menu_panel,BorderLayout.WEST);
        

        //center_switch_panel = new BackgroundPanel("./M3/bg.png");
        center_switch_panel = new JPanel(new BorderLayout());
        center_switch_panel.setBackground(GameColors.PANEL);
        main_panel.add(center_switch_panel,BorderLayout.CENTER);
        
        dialog_panel = new DialogPanel();
        main_panel.add(dialog_panel,BorderLayout.SOUTH);
        
        GameLog.log = dialog_panel;
        
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
        
        menu_panel.btn_technology.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    SwitchPanel(technology_panel);
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
                    ImageIO.read(new File("./M3/swords_icon.png"));

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
    JButton btn_technology;
    JButton btn_Stats;
    JButton btn_Battles;
    
    public SideMenuPanel() {
    	setLayout(new GridLayout(6,1,5,5));
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
        btn_technology = createMenuButton("Technology");
        btn_Stats = createMenuButton("Stats");
        btn_Battles = createMenuButton("Battles");
        
        
        add(btn_civilization);
        add(btn_Army);
        add(btn_technology);
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

    private JTextArea info_textarea;
    private JScrollPane scroll;
    public DialogPanel() {
	    setLayout(new BorderLayout());
	    setPreferredSize(new Dimension(0, 120));
	    setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 4));
	
	
	    info_textarea = new JTextArea(10,50);
	    info_textarea.setBackground(GameColors.STEEL_BLUE);
	    info_textarea.setFont(new Font("Monospaced", Font.BOLD, 14));
	
	    scroll = new JScrollPane(info_textarea);
	
	    add(scroll,BorderLayout.CENTER);
    }

	public void AddMessage(String message/*,Color whichColor*/) {
	    //dialog_info.setForeground(whichColor);
		info_textarea.append(message + "\n");
	}
	
    public void clear() {
    	info_textarea.setText("");
        
    }
}



class GameLog {
    public static DialogPanel log;

    public static void info(String msg) {
        log.AddMessage("[INFO] " + msg);
    }

    public static void error(String msg) {
        log.AddMessage("[ERROR] " + msg);
    }

    public static void warning(String msg) {
        log.AddMessage("[WARNING] " + msg);
    }

    public static void battle(String msg) {
        log.AddMessage("[BATTLE] " + msg);
    }
}


class BackgroundPanel extends JPanel{
	BufferedImage bg_image;
	
	 public BackgroundPanel(String path) {
		 try {

			 bg_image =ImageIO.read(new File(path));//*"./M3/bg.png"*/

	     } catch (IOException e) {

	         System.out.println("Error loading background image.");
	     }
	 }

	 protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bg_image.getScaledInstance(getWidth(),getHeight(),Image.SCALE_SMOOTH),0,0,this);
	 }

}



class CivilizationPanel  extends BackgroundPanel{
	public CivilizationPanel() {
		super("./M3/bg.png");
		setLayout(new BorderLayout());
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
		tab_army_panel.add("Special Units", new SpecialTabPanel(civ));

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

class TechnologyPanel  extends JPanel {
	public TechnologyPanel() {

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
