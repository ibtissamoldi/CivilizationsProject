package M3.GUIgame.Battlepanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import M3.GUIgame.GameColors;

public class MainBattlePanel extends JPanel {

    public MainBattlePanel(){

        setLayout(new BorderLayout(10,10));

        JPanel left = new JPanel(new GridLayout(3,3,10,10));
        JScrollPane scroll = new JScrollPane(left);
        scroll.setBorder(null);
        scroll.getViewport().setBackground(GameColors.PANEL);
        left.setPreferredSize(new Dimension(330, 0));

        JPanel right = new JPanel(new GridLayout(0,1,15,15));
        right.setPreferredSize(new Dimension(90, 0));

        left.setBackground(GameColors.PANEL);
        right.setBackground(GameColors.PANEL);
        
        CenterFightPanel center = new CenterFightPanel();
        center.setMinimumSize(new Dimension(400,0));

        String[] civUnits = {"Swordsman","Spearman","Crossbow","Cannon","ArrowTower","Catapult","RocketLauncherTower","Magician","Priest"};
        
        for(String u : civUnits)
            left.add(new UnitCardPanel(u,true,true));

        String[] enemyUnits = {"Swordsman","Spearman","Crossbow","Cannon" };
        
        for(String u : enemyUnits)
            right.add(new UnitCardPanel(u,false,false));

        add(scroll, BorderLayout.WEST);
        
        add(center, BorderLayout.CENTER);
        add(right, BorderLayout.EAST);

    }
}