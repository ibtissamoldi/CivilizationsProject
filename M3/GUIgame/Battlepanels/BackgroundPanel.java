package M3.GUIgame.Battlepanels;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import M3.GUIgame.GameColors;

public class BackgroundPanel extends JPanel{
	BufferedImage bg_image;
	
	 public BackgroundPanel(String path) {
		 try {

			 bg_image =ImageIO.read(getClass().getResource(path));
			 

	     } catch (IOException e) {

	         System.out.println("Error loading background image.");
	     }
	 }

	 protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(bg_image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
		g2d.setColor(GameColors.BORDER);
		int thickness = 2;
		g2d.setStroke(new BasicStroke(thickness));
	    g2d.drawRect(thickness / 2, thickness / 2, getWidth() - thickness, getHeight() - thickness);
		
		}
}