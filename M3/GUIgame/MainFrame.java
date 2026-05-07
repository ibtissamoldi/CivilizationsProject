package GUIgame;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class MainFrame extends JFrame{
	
	public static void main(String[] args) {
	    new MainFrame();
	}
	
	private JPanel main_panel;
	private JPanel topbar_panel;
	private JPanel menu_panel;
	private JPanel center_switch_panel;
	private JTabbedPane tab_army_panel;
	
	private BufferedImage icon_image;
	
	private ContentPanel building_panel;
	private ContentPanel army_panel;
	private ContentPanel civilization_panel;
	private ContentPanel stats_panel;
	private ContentPanel battle_panel;
	
	private JToolBar atck_buttonbar;
	
	private JLabel label_food;
	private JLabel label_wood;
	private JLabel label_iron;
	private JLabel label_mana;
	

    public MainFrame() {
        setTitle("Civilizations Game");
        setBounds(250,100,1000,650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        try {
        	icon_image = ImageIO.read(new File("./swords.png"));
		} 
        catch (IOException e) {
			System.out.println("We have some problems trying to add the icon image");
		}
        
        main_panel = new JPanel();
        main_panel.setLayout(new BorderLayout());
        main_panel.setBackground(Color.BLACK);
        
        
        topbar_panel = new JPanel();
        //topbar_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topbar_panel.setBackground(Color.black);
        topbar_panel.setBorder(BorderFactory.createLineBorder(new Color(96,96,96), 3));
        
        
        label_food= new JLabel("Food: 0");
        label_wood= new JLabel("Wood: 0");
        label_iron= new JLabel("Iron: 0");
        label_mana= new JLabel("Mana: 0");
        
        label_food.setForeground(Color.white);
        label_wood.setForeground(Color.white);
        label_iron.setForeground(Color.white);
        label_mana.setForeground(Color.white);
        
        topbar_panel.add(label_food);
        topbar_panel.add(label_wood);
        topbar_panel.add(label_iron);
        topbar_panel.add(label_mana);
        
        	
        
        main_panel.add(topbar_panel, BorderLayout.NORTH);
        add(main_panel);

        
        
        menu_panel = new JPanel();
        menu_panel.setLayout(new GridLayout(5,1,5,5));
        menu_panel.setBackground(new Color(96,96,96));
        menu_panel.setBorder(BorderFactory.createLineBorder(new Color(96,96,96), 3));
        
        
        JButton btn_civilization = new JButton("Civilization");
        JButton btn_Army = new JButton("Army");
        JButton btn_Buildings = new JButton("Buildings");
        JButton btn_Stats = new JButton("Stats");
        JButton btn_Battles = new JButton("Battles");
        
        JButton[] buttons = {btn_Army, btn_Buildings, btn_civilization, btn_Stats, btn_Battles};
        
        for (JButton btn : buttons) {
            btn.setBackground(Color.BLACK);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setBorderPainted(false);
            btn.setOpaque(true);
        }
        
        menu_panel.add(btn_civilization);
        menu_panel.add(btn_Army);
        menu_panel.add(btn_Buildings);
        menu_panel.add(btn_Stats);
        menu_panel.add(btn_Battles);
        
        
        
        main_panel.add(menu_panel,BorderLayout.WEST);
        
        
        
        
        center_switch_panel = new JPanel();
        center_switch_panel.setBackground(Color.white);
        center_switch_panel.setLayout(new BorderLayout());
        main_panel.add(center_switch_panel,BorderLayout.CENTER);
        
        
       
        building_panel = new ContentPanel();
        army_panel = new ContentPanel();
        civilization_panel = new ContentPanel();
        stats_panel = new ContentPanel();
        battle_panel = new ContentPanel();
        
        
        btn_Buildings.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				SwitchPanel(building_panel);
				building_panel.setBackground(Color.green);
				
			}
		});
		        
		btn_Army.addActionListener(new ActionListener() {
					
					public void actionPerformed(ActionEvent e) {
						SwitchPanel(army_panel);						
					}
				});
		
		btn_civilization.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				SwitchPanel(civilization_panel);
				civilization_panel.setBackground(Color.blue);
				
			}
		});
		

		btn_Stats.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				SwitchPanel(stats_panel);
				stats_panel.setBackground(Color.yellow);
				
			}
		});
		
		btn_Battles.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				SwitchPanel(battle_panel);
				battle_panel.setBackground(Color.pink);
				
			}
		});
		tab_army_panel = new JTabbedPane();
		
		JPanel tab_defense_panel = new JPanel(new BorderLayout());
		JPanel tab_attack_panel = new JPanel(new BorderLayout());
		JPanel tab_special_panel = new JPanel(new BorderLayout());
		
		tab_army_panel.add("Attack Units",tab_attack_panel);
		tab_army_panel.add("Defense Units",tab_defense_panel);
		tab_army_panel.add("Special Units",tab_special_panel);
		tab_army_panel.setBackground(Color.black);
		
		army_panel.setLayout(new BorderLayout());
		army_panel.add(tab_army_panel,BorderLayout.CENTER);
		
		
		
		
		
		
		
		
		
        setIconImage(icon_image);
        setVisible(true);
    }
    
    private void SwitchPanel(JPanel panel) {
        center_switch_panel.removeAll();
        center_switch_panel.add(panel);
        center_switch_panel.revalidate();
        center_switch_panel.repaint();
    }

}





class ContentPanel extends JPanel {
	private JLabel label;
    public ContentPanel() {
        //setBackground(Color.green);
        label = new JLabel("ContentPanel");
        add(label);
    }
}
