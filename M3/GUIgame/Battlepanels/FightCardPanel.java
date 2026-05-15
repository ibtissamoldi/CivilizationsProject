package M3.GUIgame.Battlepanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import M3.GUIgame.GameColors;

public class FightCardPanel extends JPanel{
	private JLabel name;
	private JLabel image;
	private JLabel damage;
	private JLabel armor;

    public FightCardPanel() {

        setLayout(new GridLayout(4,1));
        setPreferredSize(new Dimension(180,220));
        setBackground(new Color(70,90,70));
        setOpaque(false);
        setBorder(BorderFactory.createCompoundBorder(
        	    BorderFactory.createLineBorder(GameColors.BORDER,2),
        	    BorderFactory.createEmptyBorder(10,10,10,10)
        	));

        name = new JLabel("Unit");
        image = new JLabel("");
        damage = new JLabel("Damage:");
        armor = new JLabel("Armor:");
        
        name.setHorizontalAlignment(JLabel.CENTER);
        image.setHorizontalAlignment(JLabel.CENTER);
        damage.setHorizontalAlignment(JLabel.CENTER);
        armor.setHorizontalAlignment(JLabel.CENTER);
        
        
        name.setForeground(GameColors.TEXT);
        damage.setForeground(GameColors.TEXT);
        armor.setForeground(GameColors.TEXT);

        add(name);
        add(image);
        add(damage);
        add(armor);
    }

    
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(new Color(0,0,0,80));
        g2.fillRoundRect(6,6,getWidth()-6,getHeight()-6,20,20);

        g2.setColor(getBackground());
        g2.fillRoundRect(0,0,getWidth()-6,getHeight()-6,20,20);

        super.paintComponent(g);
    }
    
    public void updateCard(String n, String img, int d, int a){

        name.setText(n);

        image.setIcon(
            new ImageIcon(
                new ImageIcon(img).getImage()
                .getScaledInstance(80,80,Image.SCALE_SMOOTH)
            )
        );

        damage.setText("Damage: " + d);
        armor.setText("Armor: " + a);
    }
}	