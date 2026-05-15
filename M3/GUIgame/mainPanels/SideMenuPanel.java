package M3.GUIgame.mainPanels;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import M3.GUIgame.GameColors;


public class SideMenuPanel extends JPanel {
	
	private  JButton btn_civilization;
	private  JButton btn_Army;
	private  JButton btn_Buildings;
	private  JButton btn_technology;
	private  JButton btn_Stats;
	private  JButton btn_Battles;
	private  JButton btn_battle_reports;
    
    public SideMenuPanel() {
    	setLayout(new GridLayout(7,1,5,5));
    	setBackground(new Color(235,243,231));
        setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
        
        //btn_civilization = createMenuButton("Civilization");
        
        BufferedImage image = null;
		
		try {
			image = ImageIO.read(new File("./M3/images/logo_.png"));
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
        btn_civilization.setBackground(GameColors.PANEL);
        btn_civilization.setPreferredSize(new Dimension(140, 40));
        
        btn_Army = createMenuButton("Army");
        btn_Buildings = createMenuButton("Buildings");
        btn_technology = createMenuButton("Technology");
        btn_Stats = createMenuButton("Stats");
        btn_Battles = createMenuButton("Battles");
        btn_battle_reports = createMenuButton("Reports");
        
        
        add(btn_civilization);
        add(btn_Army);
        add(btn_technology);
        add(btn_Buildings);
        add(btn_Stats);
        add(btn_Battles);
        add(btn_battle_reports);
    }
    
    
    
    
    public JButton getBtn_battle_reports() {
		return btn_battle_reports;
	}




	public JButton getBtn_civilization() {
		return btn_civilization;
	}




	public JButton getBtn_Army() {
		return btn_Army;
	}




	public JButton getBtn_Buildings() {
		return btn_Buildings;
	}




	public JButton getBtn_technology() {
		return btn_technology;
	}




	public JButton getBtn_Stats() {
		return btn_Stats;
	}




	public JButton getBtn_Battles() {
		return btn_Battles;
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
