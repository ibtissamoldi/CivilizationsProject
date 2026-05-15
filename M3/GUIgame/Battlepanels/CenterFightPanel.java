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

    private FightCardPanel civ_card;
    private FightCardPanel enemy_card;
    private Image bg_image;

    public CenterFightPanel() {

        setLayout(null);
        setBackground(GameColors.BACKGROUND);
        bg_image = new ImageIcon("./M3/images/battle_bg.png").getImage();
        
        setBorder(BorderFactory.createCompoundBorder(
        	    BorderFactory.createLineBorder(GameColors.BORDER,3),
        	    BorderFactory.createEmptyBorder(15,15,15,15)
        	));

        civ_card = new FightCardPanel();
        enemy_card = new FightCardPanel();

        JLabel vs = new JLabel("VS");
        vs.setFont(new Font("Arial", Font.BOLD, 52));
        vs.setHorizontalAlignment(JLabel.CENTER);
        vs.setForeground(GameColors.GOLD);

        civ_card.setBounds(40,160,180,220);
        enemy_card.setBounds(260,160,180,220);
        vs.setBounds(getWidth()/2 - 50, 190, 100, 100);
        
        

        add(civ_card);
        add(enemy_card);
        add(vs);
    }
    
    public void doLayout() {

        int cardWidth = 180;
        int cardHeight = 220;

        int centerX = getWidth() / 2;

        civ_card.setBounds(centerX - 220, 160, cardWidth, cardHeight);

        enemy_card.setBounds(centerX + 40, 160, cardWidth, cardHeight);
    }
    
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(bg_image, 0, 0, getWidth(), getHeight(), this);

        g2.setColor(new Color(0,0,0,140));
        g2.fillRect(0,0,getWidth(),getHeight());
    }

    public void animateCivilAttack(){
    	civ_card.setLocation(100,100);
    }

    public void animateEnemyAttack(){
    	enemy_card.setLocation(220,120);
    }
}
