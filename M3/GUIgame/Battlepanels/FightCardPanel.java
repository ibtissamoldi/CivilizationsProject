package M3.GUIgame.Battlepanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;

import javax.imageio.ImageIO;
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
        setPreferredSize(new Dimension(180,260));
        setBackground(new Color(70,90,70));
        setOpaque(false);

        name = new JLabel("Unit");
        image = new JLabel();
        damage = new JLabel("Damage: 0");
        armor = new JLabel("Armor: 0");
        
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
    
    

    
    public JLabel getDamageLabel() {
		return damage;
	}




	public JLabel getArmorLabel() {
		return armor;
	}




	protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	
        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(GameColors.TEXT);
        g2.fillRoundRect(6,6,getWidth()-6,getHeight()-6,20,20);

        g2.setColor(GameColors.BACKGROUND);
        g2.fillRoundRect(0,0,getWidth()-6,getHeight()-6,20,20);

        
    }
    
    public void updateCard(String unitName,String imagePath,int dmg,int arm){

        name.setText(unitName);


        try {

            Image img =ImageIO.read(
                getClass().getResource(imagePath)
            );

            Image scaled = img.getScaledInstance(
                60,
                60,
                Image.SCALE_SMOOTH
            );

            image.setIcon(new ImageIcon(scaled));
            image.repaint();

            repaint();

        } catch (Exception e) {

            System.out.println("IMAGE FAILED: " + imagePath);

            e.printStackTrace();
        }

        damage.setText("Damage: " + dmg);
        armor.setText("Armor: " + arm);
    }
}