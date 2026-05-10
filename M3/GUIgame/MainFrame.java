package M3.GUIgame;


import java.awt.BasicStroke;
import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;
import java.util.Timer;

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
        setBounds(250,100,1000,650);;
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
	
	private Civilization civ;
	
	private JLabel label_food;
    private JLabel label_wood;
    private JLabel label_iron;
    private JLabel label_mana;
        
    private JLabel lbl_timer;
    private JLabel lbl_threat;
    
    private Timer timer;
    private TimerTask task;

    private int time_left = 180;
    
	public TopBarPanel(Civilization civ) {
		
		this.civ = civ;
		
		setBackground(GameColors.PANEL);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(0, 0, 3, 0, GameColors.BORDER),
                        BorderFactory.createEmptyBorder(8, 15, 8, 15)));
		setPreferredSize(new Dimension(0, 70));
		
		JPanel resources_panel = new JPanel();
		resources_panel.setLayout(new FlowLayout(FlowLayout.LEFT, 8, 8));
		resources_panel.setOpaque(false);
		resources_panel.setBorder(
			    BorderFactory.createCompoundBorder(
			        BorderFactory.createLineBorder(GameColors.BORDER, 2),
			        BorderFactory.createEmptyBorder(5,10,5,10)
			    )
			);
		
		label_food = CreateResourcesBox("./M3/food.png", civ.getFood());
        label_wood = CreateResourcesBox("./M3/wood.png", civ.getWood());
        label_iron = CreateResourcesBox("./M3/iron.png", civ.getIron());
        label_mana = CreateResourcesBox("./M3/mana.png", civ.getMana());
        
        
        resources_panel.add(label_food);
        resources_panel.add(label_wood);
        resources_panel.add(label_iron);
        resources_panel.add(label_mana);
        
        
        JPanel timer_panel = new JPanel(new BorderLayout());
        timer_panel.setBackground(GameColors.PANEL_LIGHT);
        timer_panel.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GameColors.BORDER, 2),
                BorderFactory.createEmptyBorder(5, 20, 5, 20)
            )
        );
        timer_panel.setOpaque(true);
        
        JLabel label_timer = new JLabel("Next Attack: ");
        label_timer.setForeground(GameColors.TEXT);
        label_timer.setFont(new Font("Serif", Font.BOLD, 16));
        label_timer.setHorizontalAlignment(JLabel.CENTER);
        
        timer_panel.add(label_timer,BorderLayout.NORTH);
        
        lbl_timer  = new JLabel("03:00");
        lbl_timer.setForeground(GameColors.TEXT);
        lbl_timer.setFont(new Font("Arial", Font.BOLD, 18));
        lbl_timer.setHorizontalAlignment(JLabel.CENTER);
        
        timer_panel.add(lbl_timer,BorderLayout.CENTER);
        
        JPanel threat_panel = new JPanel(new BorderLayout());
        threat_panel.setOpaque(false);
        threat_panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GameColors.BORDER, 2),
                        BorderFactory.createEmptyBorder(5,10,5,10)));

        JLabel threat_title = new JLabel("⚠ THREAT");
        threat_title.setForeground(GameColors.ERROR);
        threat_title.setFont(new Font("Serif", Font.BOLD, 16));

        lbl_threat = new JLabel("No enemies detected");
        lbl_threat.setForeground(GameColors.TEXT);

        threat_panel.add(threat_title, BorderLayout.NORTH);
        threat_panel.add(lbl_threat, BorderLayout.CENTER);
        
        resources_panel.setPreferredSize(new Dimension(350, 30));
        timer_panel.setPreferredSize(new Dimension(200, 30));
        threat_panel.setPreferredSize(new Dimension(170, 30));
        
        
        add(resources_panel,BorderLayout.WEST);
        add(timer_panel,BorderLayout.CENTER);
        add(threat_panel,BorderLayout.EAST);
        
        UpdateResources();
        
        StartTimer();
	}
	
	private JLabel CreateResourcesBox(String imagepath, int value) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File(imagepath));
		} catch (IOException e) {
		    System.out.println("Error loading resources images");
		}
		
		Image scaled = image.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		ImageIcon resource_icon = new ImageIcon(scaled);
		
        String v = String.valueOf(value);

        JLabel label = new JLabel(v, resource_icon, JLabel.CENTER);
        label.setHorizontalTextPosition(JLabel.RIGHT);
        label.setForeground(GameColors.GOLD);
        label.setFont(new Font("Serif", Font.BOLD, 14));
        label.setIconTextGap(5);
        label.setBorder(
        	    BorderFactory.createEmptyBorder(0, 5, 0, 5)
        	);

        return label;
    }
	
	private void StartTimer() {

	    timer = new Timer();

	    task = new TimerTask() {

	        public void run() {

	            time_left--;

	            UpdateTimer(time_left);

	            if(time_left <= 0) {

	                lbl_threat.setText("⚔ ENEMIES ATTACKING!");

	                timer.cancel();
	            }
	        }
	    };

	    timer.scheduleAtFixedRate(task, 0, 1000);
	}
	
	
	public void UpdateResources() {
		label_food.setText(String.valueOf(civ.getFood()));
		label_wood.setText(String.valueOf(civ.getWood()));
		label_iron.setText(String.valueOf(civ.getIron()));
		label_mana.setText(String.valueOf(civ.getMana()));
    }

	
	public void UpdateTimer(int seconds) {
		
        int minutes = seconds / 60;
        int secs = seconds % 60;

        lbl_timer.setText(String.format("%02d:%02d", minutes, secs));
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
    	setBackground(new Color(235,243,231));
        setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
        
        //btn_civilization = createMenuButton("Civilization");
        
        BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("./M3/logo_.png"));
		} catch (IOException e) {
		    System.out.println("Error loading logo");
		}
		
		Image scaled = image.getScaledInstance(120, 80, Image.SCALE_SMOOTH);
		ImageIcon logo = new ImageIcon(scaled);
		btn_civilization = new JButton(logo);
		
        /*btn_civilization.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GameColors.BORDER, 2),
                        BorderFactory.createEmptyBorder(5, 15, 5, 15)));*/
        btn_civilization.setFocusPainted(false);
        btn_civilization.setBorderPainted(false);
        btn_civilization.setBackground(new Color(235,243,231));
        btn_civilization.setPreferredSize(new Dimension(140, 40));
        
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
        btn.setPreferredSize(new Dimension(140, 40));
        btn.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(GameColors.BORDER, 2),
                    BorderFactory.createEmptyBorder(12, 20, 12, 20)
                )
            );
        btn.setFont(new Font("Serif", Font.BOLD, 16));
        
        
        btn.addMouseListener(new MouseAdapter() {
        	public void mouseEntered(MouseEvent e) {
        		btn.setBackground(GameColors.BUTTON_HOVER);
        		}
        	public void mouseExited(MouseEvent e) {
        		btn.setBackground(GameColors.BUTTON);
        		}
        	});
        		

        return btn;
    }
}


class DialogPanel extends JPanel {

    private JTextArea info_textarea;
    private JScrollPane scroll;
    public DialogPanel() {
	    setLayout(new BorderLayout());
	    setPreferredSize(new Dimension(0, 120));
	    setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
	
	
	    info_textarea = new JTextArea("⚔ Game log...",10,50);
	    info_textarea.setEditable(false);
	    info_textarea.setBackground(new Color(30, 36, 40));
	    info_textarea.setForeground(GameColors.TEXT_DARK);
	    info_textarea.setCaretColor(GameColors.TEXT);
	    info_textarea.setFont(new Font("Monospaced", Font.BOLD, 14));
	    info_textarea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    scroll = new JScrollPane(info_textarea);
	
	    add(scroll,BorderLayout.CENTER);
    }

	public void AddMessage(String message) {
		//info_textarea.setForeground(whichColor);
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
        log.AddMessage("⚔ [BATTLE] " + msg);
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
		int thickness = 6;
		g2d.setStroke(new BasicStroke(thickness));
	    g2d.drawRect(thickness / 2, thickness / 2, getWidth() - thickness, getHeight() - thickness);
		
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
