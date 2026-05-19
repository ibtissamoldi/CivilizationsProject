package M3.GUIgame.mainPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import M3.GUIgame.GameColors;
import M3.game.Civilization;

public class TopBarPanel extends JPanel {
	
	private Civilization civ;
	
	private JLabel label_food;
    private JLabel label_wood;
    private JLabel label_iron;
    private JLabel label_mana;
        
    private JLabel lbl_timer;
    //private JLabel lbl_threat;
    
    private JLabel lbl_civ_count;
    private JLabel lbl_enemy_count;
    
    private Timer timer;
    private TimerTask task;

    private int time_left = 30;
    
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
		
		label_food = CreateResourcesBox("/M3/images/food.png", civ.getFood());
        label_wood = CreateResourcesBox("/M3/images/wood.png", civ.getWood());
        label_iron = CreateResourcesBox("/M3/images/iron.png", civ.getIron());
        label_mana = CreateResourcesBox("/M3/images/mana.png", civ.getMana());
        
      
        
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
        
        JLabel label_timer = new JLabel("Next Attack Loop: ");
        label_timer.setForeground(GameColors.TEXT);
        label_timer.setFont(new Font("Serif", Font.BOLD, 16));
        label_timer.setHorizontalAlignment(JLabel.CENTER);
        timer_panel.add(label_timer,BorderLayout.NORTH);
        
        
        lbl_timer  = new JLabel("00:30");
        lbl_timer.setForeground(GameColors.TEXT);
        lbl_timer.setFont(new Font("Arial", Font.BOLD, 18));
        lbl_timer.setHorizontalAlignment(JLabel.CENTER);     
        timer_panel.add(lbl_timer,BorderLayout.CENTER);
        
        JPanel threat_panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        threat_panel.setOpaque(false);
        threat_panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GameColors.BORDER, 2),
                        BorderFactory.createEmptyBorder(5,10,5,10)));

        lbl_civ_count = new JLabel("Your Army: 0");
        lbl_civ_count.setFont(new Font("Arial", Font.BOLD, 12));
        lbl_civ_count.setForeground(GameColors.GOLD);
        
        lbl_enemy_count = new JLabel("Enemy Army: 0");
        lbl_enemy_count.setFont(new Font("Arial", Font.BOLD, 12));
        lbl_enemy_count.setForeground(GameColors.ERROR);
        
        threat_panel.add(lbl_civ_count);
        threat_panel.add(lbl_enemy_count);
        
        resources_panel.setPreferredSize(new Dimension(400, 40));
        timer_panel.setPreferredSize(new Dimension(200, 30));
        threat_panel.setPreferredSize(new Dimension(170, 30));
        
        
        add(resources_panel,BorderLayout.WEST);
        add(timer_panel,BorderLayout.CENTER);
        add(threat_panel,BorderLayout.EAST);
        
        /*
        JLabel threat_title = new JLabel("⚠ THREAT");
        threat_title.setForeground(GameColors.ERROR);
        threat_title.setFont(new Font("Serif", Font.BOLD, 16));*/

        /*
        lbl_threat = new JLabel("No enemies detected");
        lbl_threat.setForeground(GameColors.TEXT);*/

        /*
        threat_panel.add(threat_title, BorderLayout.NORTH);
        threat_panel.add(lbl_threat, BorderLayout.CENTER);*/

        UpdateResources();
        StartTimer();
	}
	
	private JLabel CreateResourcesBox(String imagepath, int value) {
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResource(imagepath));
		} catch (IOException e) {
		    System.out.println("Error loading resources images");
		}
		
		 if (image == null) {
		        System.out.println("Failed to load image: " + imagepath);
		        return new JLabel("Missing");
		    }
		
		Image scaled = image.getScaledInstance(18, 18, Image.SCALE_SMOOTH);
		ImageIcon resource_icon = new ImageIcon(scaled);
		
        JLabel label = new JLabel(String.valueOf(value), resource_icon, JLabel.CENTER);
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

	            	time_left = 30;
	            	
	                /*lbl_threat.setText("⚔ ENEMIES ATTACKING!");*/
	                
	            }
	            
	            /*if(time_left == 179) {
	                lbl_threat.setText("No enemies detected");
	            }*/
	            
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
	
	public void updateLiveCounts(int civCount, int enemyCount) {
	    lbl_civ_count.setText("Your Army: " + civCount);
	    lbl_enemy_count.setText("Enemy Army: " + enemyCount);
	}
	
}

