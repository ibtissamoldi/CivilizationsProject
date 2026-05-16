package M3.GUIgame.Battlepanels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import M3.GUIgame.GameColors;

public class CenterFightPanel extends JPanel {

    private FightCardPanel attackerCard;
    private FightCardPanel defenderCard;

    public CenterFightPanel() {

        setLayout(null);
        setBackground(GameColors.PANEL);
        
        attackerCard = new FightCardPanel();
        defenderCard = new FightCardPanel();

        attackerCard.setBounds(10, 120, 180, 220);
        defenderCard.setBounds(200, 220, 180, 220);
        
        

        add(attackerCard);
        add(defenderCard);
    }
    
    public FightCardPanel getAttackerCard() {
        return attackerCard;
    }

    public FightCardPanel getDefenderCard() {
        return defenderCard;
    }
    
    public void showAttackerOnTop() {

        attackerCard.setLocation(10,120);
        defenderCard.setLocation(200,220);
        repaint();
    }
    
    public void showDefenderOnTop() {

        attackerCard.setLocation(200,220);
        defenderCard.setLocation(10,120);

        repaint();
    }
    
    
    public void test() {
    	
    }
}
