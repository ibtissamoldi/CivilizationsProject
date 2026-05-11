package M3.GUIgame;

import java.awt.BasicStroke;


import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;

import M3.GUIgame.mainPanels.GeneralBuildingPanel;
import M3.GUIgame.mainPanels.GeneralTechnologyPanel;
import M3.GUIgame.mainPanels.TopBarPanel;
import M3.GUIgame.mainPanels.SideMenuPanel;
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
    private TechnologysPanel technology_panel;
    private StatsPanel stats_panel;
    private BattlePanel battle_panel;
	
    private DialogPanel dialog_panel;
    
	private Civilization civ;
	
	private Timer resources_timer;
	private TimerTask resources_task;
	

    public MainFrame() {
    	
        setTitle("Civilizations Game");
        setBounds(250,10,1000,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        loadIcon();
        
        civ = new Civilization();
        
        civ.setFood(1000000);
        civ.setWood(1300000);
        civ.setIron(1000000);
        civ.setMana(0);
        
        createPanels();
        
       
        army_panel = new ArmyPanel(civ);
        building_panel = new BuildingsPanel(civ);
        civilization_panel = new CivilizationPanel();
        technology_panel = new TechnologysPanel(civ);
        stats_panel = new StatsPanel();
        battle_panel = new BattlePanel();
        
        
		
        initializeButtonActions();
        SwitchPanel(civilization_panel);
        
        //dialog_panel.AddMessage("Welcome to Our Civilization!");

        setVisible(true);
    }
    private void createPanels() {
    	main_panel = new JPanel(new BorderLayout());
        main_panel.setBackground(new Color(30, 36, 40));
        
        topbar_panel = new TopBarPanel(civ);
        main_panel.add(topbar_panel, BorderLayout.NORTH);
        
        menu_panel = new SideMenuPanel();
        main_panel.add(menu_panel,BorderLayout.WEST);
        

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
    
    private void StartResourcesGeneration() {

        resources_timer = new Timer();

        resources_task = new TimerTask() {

            @Override
            public void run() {

                GenerateResources();
            }
        };

        resources_timer.scheduleAtFixedRate(resources_task, 0, 5000);
    }
    
    private void GenerateResources() {

        int generated_food = civ.getFarm() * 100;
        int generated_wood = civ.getCarpentry() * 100;
        int generated_iron = civ.getSmithy() * 100;
        int generated_mana = civ.getMagicTower() * 50;

        civ.setFood(civ.getFood() + generated_food);
        civ.setWood(civ.getWood() + generated_wood);
        civ.setIron(civ.getIron() + generated_iron);
        civ.setMana(civ.getMana() + generated_mana);

        if (generated_food > 0) {
            GameLog.info("+" + generated_food + " food generated");
        }

        if (generated_wood > 0) {
            GameLog.info("+" + generated_wood + " wood generated");
        }

        if (generated_iron > 0) {
            GameLog.info("+" + generated_iron + " iron generated");
        }

        if (generated_mana > 0) {
            GameLog.info("+" + generated_mana + " mana generated");
        }
    }
    
    
    private void initializeButtonActions() {
    	

        menu_panel.getBtn_Army().addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			SwitchPanel(army_panel);
        		}
        		});
        		
        menu_panel.getBtn_Buildings().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel(building_panel);
    		}
    		});
        
        menu_panel.getBtn_technology().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    SwitchPanel(technology_panel);
            }
            });
        
        menu_panel.getBtn_civilization().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel(civilization_panel);
    		}
    		});
        
        menu_panel.getBtn_Stats().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel(stats_panel);
    		}
    		});
        
        menu_panel.getBtn_Battles().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel(battle_panel);
    		}
    		});
    }

    
    private void loadIcon() {
    	BufferedImage icon_image;

        try {

            icon_image =
                    ImageIO.read(new File("./M3/images/swords_icon.png"));

            setIconImage(icon_image);

        } catch (IOException e) {

            System.out.println("Error loading icon.");
        }
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
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg_image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
		g2d.setColor(GameColors.BORDER);
		int thickness = 2;
		g2d.setStroke(new BasicStroke(thickness));
	    g2d.drawRect(thickness / 2, thickness / 2, getWidth() - thickness, getHeight() - thickness);
		
		}

}

class CivilizationPanel  extends BackgroundPanel{
	public CivilizationPanel() {
		super("./M3/images/bg.png");
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

class TechnologysPanel  extends JPanel {
	public TechnologysPanel(Civilization civ) {
		setLayout(new BorderLayout());
        setBackground(GameColors.PANEL);
        GeneralTechnologyPanel generaltechnologypanel= new GeneralTechnologyPanel(civ);
        add(generaltechnologypanel, BorderLayout.CENTER);
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
