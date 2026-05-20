package M3.GUIgame.Battlepanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import M3.GUIgame.MainFrame;
import M3.game.Civilization;

public class BattlePanel  extends BackgroundPanel   {
	
	private JPanel startPanel;
    private MainBattlePanel mainBattlePanel;
    
    
    private Civilization civ;
    private MainFrame frame;
    
    
    public BattlePanel(Civilization civ,MainFrame frame) {
    	super("/M3/images/battlebg.png");
    	this.civ = civ;
    	this.frame = frame;
    	
        setLayout(new BorderLayout());
        
        
        buildStartPanel();


        add(startPanel, BorderLayout.CENTER);
    }

    private void buildStartPanel() {

    	startPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 250));
    	startPanel.setOpaque(false);

        JButton btnStart = new JButton("");

        btnStart.setPreferredSize(new Dimension(420, 120));
        btnStart.setFont(new Font("Serif", Font.BOLD, 34));

        
        btnStart.setFocusPainted(false);
        btnStart.setContentAreaFilled(false);
        btnStart.setOpaque(false);
        btnStart.setBorderPainted(false);
        btnStart.setFocusPainted(false);
        
        btnStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	switchToMainBattle();
            }
        });

        startPanel.add(btnStart,BorderLayout.CENTER);

    }

    private void switchToMainBattle() {

    	removeAll();

    	mainBattlePanel = new MainBattlePanel(civ,this,frame);
        

        add(mainBattlePanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
    
    
    public void returnToStartPanel(){

        removeAll();

        buildStartPanel();

        add(startPanel, BorderLayout.CENTER);

        revalidate();

        repaint();
    }
}