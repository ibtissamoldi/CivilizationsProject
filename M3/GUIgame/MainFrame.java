package M3.GUIgame;


import M3.interfaces.Variables;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;

import javax.imageio.ImageIO;
import javax.swing.*;


import M3.Database.DBConnection;
import M3.GUIgame.Battlepanels.BackgroundPanel;
import M3.GUIgame.Battlepanels.BattlePanel;
import M3.GUIgame.mainPanels.GeneralBuildingPanel;
import M3.GUIgame.mainPanels.GeneralStatsPanel;
import M3.GUIgame.mainPanels.GeneralTechnologyPanel;
import M3.GUIgame.mainPanels.ReportsPanel;
import M3.GUIgame.mainPanels.TopBarPanel;
import M3.GUIgame.mainPanels.SideMenuPanel;
import M3.game.Civilization;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class MainFrame extends JFrame implements Variables{
	
	public static void main(String[] args) {
	    new MainFrame();	    
	}

	private JPanel main_panel;	
	private TopBarPanel topbar_panel;
	private SideMenuPanel menu_panel;
	private JPanel center_switch_panel;
	
	private DialogPanel dialog_panel;
	private Civilization civ;
	private Civilization enemy;
	
	private Timer resources_timer;
	private TimerTask resources_task;
	
    private DBConnection db;
    private int civId = -1;
	

    public MainFrame() {
    	
        setTitle("Civilizations Game");
        setBounds(250,10,1000,800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        loadIcon();
        db = new DBConnection();
        db.connect();
        
        civ = db.loadCivilizationComplete("MyCivilization");
        

        if (civ == null) {

            civ = new Civilization("MyCivilization");
            civ.player();
            db.saveCivilization(civ);
        }
        
        setupDebugCivilization();
        civId = db.getCivilizationId(civ.getName());
        
        createPanels();
        initializeButtonActions();
       
  
        
        SwitchPanel("civilization");
        StartResourcesGeneration();
        
        dialog_panel.AddMessage("⚔ Welcome to Our Civilization!");
        
        
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
            	resources_timer.cancel();
            	db.saveCivilization(civ);
            	db.closeConnection();
            }
        });

        setVisible(true);
    }
    
    
    public void setEnemy(Civilization enemy) {
		this.enemy = enemy;
	}
    
    private void setupDebugCivilization() {

    	
        civ.setFood(999999);
        civ.setWood(999999);
        civ.setIron(999999);
        civ.setMana(999999);

        try {

            civ.newSwordsman(1);
            civ.newSpearman(1);
            civ.newCrossbow(1);
            civ.newCannon(1);

            civ.newArrowTower(3);
            civ.newCatapult(2);
            civ.newRocketLauncher(1);

            civ.newChurch();
            civ.newMagicTower();
            
            civ.newMagician(1);
            civ.newPriest(1);
            
            System.out.println("Food: " + civ.getFood());
            System.out.println("wood: " + civ.getWood());
            System.out.println("iron: " + civ.getIron());
            System.out.println("mana: " + civ.getMana());
            

        } catch (Exception e) {

            e.printStackTrace();
        }
    }
    
    
    public DBConnection getDb() {
		return db;
	}
    


	public int getCivId() {
		return civId;
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
    
    private void SwitchPanel(String panelName) {
    	center_switch_panel.removeAll();
    	
    	JPanel targetPanel;
        switch (panelName) {
            case "army":
                targetPanel = new ArmyPanel(civ, this);
                break;
            case "buildings":
                targetPanel = new BuildingsPanel(civ, this);
                break;
            case "technology":
                targetPanel = new TechnologysPanel(civ, this);
                break;
            case "stats":
                targetPanel = new StatsPanel(civ, this);
                break;
            case "battles":
                targetPanel = new BattlePanel(civ, this);
                break;
            case "reports":
                targetPanel = new ReportsPanel(civ, this);
                break;
            case "civilization":
            default:
                targetPanel = new CivilizationPanel();
                break;
        }
        
        center_switch_panel.add(targetPanel, BorderLayout.CENTER);
        center_switch_panel.revalidate();
        center_switch_panel.repaint();
    }
    
    
    
    public void RefreshInterface() {
        topbar_panel.UpdateResources();
        center_switch_panel.revalidate();
        center_switch_panel.repaint();
    }
    
    public TopBarPanel getTopBarPanel() {
        return topbar_panel;
    }
    private void StartResourcesGeneration() {

        resources_timer = new Timer();

        resources_task = new TimerTask() {

            public void run() {
                GenerateResources();
            	RefreshInterface();
            }
        };

        resources_timer.scheduleAtFixedRate(resources_task, 30000, 30000);
    }
    
    private void GenerateResources() {

        int food = CIVILIZATION_FOOD_GENERATED;
        int wood = CIVILIZATION_WOOD_GENERATED;
        int iron = CIVILIZATION_IRON_GENERATED;
        
        
        

        food += civ.getFarm() * CIVILIZATION_FOOD_GENERATED_PER_FARM;
        wood += civ.getCarpentry() * CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY;
        iron += civ.getSmithy() * CIVILIZATION_IRON_GENERATED_PER_SMITHY;
        int mana = civ.getMagicTower() * CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER;

        civ.setFood(civ.getFood() + food);
        civ.setWood(civ.getWood() + wood);
        civ.setIron(civ.getIron() + iron);
        civ.setMana(civ.getMana() + mana);
        
        
        GameLog.log.clear();

        GameLog.info(
            "+" + food + " food | " +
            "+" + wood + " wood | " +
            "+" + iron + " iron | " +
            "+" + mana + " mana"
        );
        if (civId != -1) {
        	db.saveCivilization(civ);
        }

    }
    
    private void initializeButtonActions() {
    	

        menu_panel.getBtn_Army().addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			SwitchPanel("army");
        		}
        		});
        		
        menu_panel.getBtn_Buildings().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel("buildings");
    		}
    		});
        
        menu_panel.getBtn_technology().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                    SwitchPanel("technology");
            }
            });
        
        menu_panel.getBtn_civilization().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel("civilization");
    		}
    		});
        
        menu_panel.getBtn_Stats().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel("stats");
    		}
    		});
        
        menu_panel.getBtn_Battles().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel("battles");
    		}
    		});
        
        menu_panel.getBtn_battle_reports().addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			SwitchPanel("reports");
    		}
    		});
    }

    
    private void loadIcon() {
    	BufferedImage icon_image;

        try {

            icon_image =
                    ImageIO.read(getClass().getResource("/M3/images/swords_icon.png"));

            setIconImage(icon_image);

        } catch (IOException e) {

            System.out.println("Error loading icon.");
        }
    }
    
    public Civilization getCivilization() {
        return civ;
    }
    
    public void saveGame() {

        db.saveCivilization(civ);
    }
      
}









class CivilizationPanel  extends BackgroundPanel{
	public CivilizationPanel() {
		super("/M3/images/bg.png");
		setLayout(new BorderLayout());
    }
}

class ArmyPanel extends JPanel {
	private JTabbedPane tab_army_panel;
    public ArmyPanel(Civilization civ, MainFrame frame) {
    	setLayout(new BorderLayout());
        setBackground(GameColors.PANEL);
        
		tab_army_panel = new JTabbedPane();

		tab_army_panel.setBackground(GameColors.PANEL);
		tab_army_panel.setForeground(GameColors.GOLD);
		
		tab_army_panel.add("Attack Units", new AttackTabPanel(civ,frame));
		tab_army_panel.add("Defense Units", new DefenseTabPanel(civ,frame));
		tab_army_panel.add("Special Units", new SpecialTabPanel(civ,frame));

        add(tab_army_panel, BorderLayout.CENTER);
    }
}

class BuildingsPanel extends JPanel {

    public BuildingsPanel(Civilization civ,MainFrame frame) {
        setLayout(new BorderLayout());
        setBackground(GameColors.PANEL);

        GeneralBuildingPanel generalBuildingPanel = new GeneralBuildingPanel(civ,frame);

        add(generalBuildingPanel, BorderLayout.CENTER);
    }
}

class TechnologysPanel  extends JPanel {
	public TechnologysPanel(Civilization civ,MainFrame frame) {
		setLayout(new BorderLayout());
        setBackground(GameColors.PANEL);
        GeneralTechnologyPanel generaltechnologypanel= new GeneralTechnologyPanel(civ,frame);
        add(generaltechnologypanel, BorderLayout.CENTER);
    }
}


class StatsPanel  extends JPanel {
	public StatsPanel(Civilization civ,MainFrame frame) {
    setLayout(new BorderLayout());
    setBackground(GameColors.BACKGROUND);
    add(new GeneralStatsPanel(civ,frame), BorderLayout.CENTER);
		    
    }
}