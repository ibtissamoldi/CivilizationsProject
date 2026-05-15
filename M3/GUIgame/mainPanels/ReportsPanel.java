package M3.GUIgame.mainPanels;

import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import M3.GUIgame.GameColors;
import M3.GUIgame.MainFrame;
import M3.game.Civilization;
import M3.interfaces.MilitaryUnit;

public class ReportsPanel extends JPanel {
	
	private MainFrame frame;
	
	private JPanel main_report_panel;
	private JPanel left_report_panel;
	private JPanel right_report_panel;
	private JPanel center_report_panel;
	
	private Civilization civ;
	private int battle_number;
	    
	private JButton view_details;
	
	public ReportsPanel(Civilization civ, MainFrame frame) {
		this.civ = civ;
		this.frame =frame;
			
		main_report_panel = new JPanel();
	    main_report_panel.setLayout(new BoxLayout(main_report_panel, BoxLayout.X_AXIS));
	    main_report_panel.setBackground(GameColors.PANEL);
	    main_report_panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2),
	    		"Battle #"+String.valueOf(battle_number),0,0,new Font("Serif", Font.BOLD, 16),GameColors.GOLD));    
	    
	    add(main_report_panel);
	    
	    
	    
	    
	    
	}
    
}
