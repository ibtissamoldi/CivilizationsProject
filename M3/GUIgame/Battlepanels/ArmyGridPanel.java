package M3.GUIgame.Battlepanels;

import java.awt.GridLayout;

import javax.swing.JPanel;

import M3.GUIgame.GameColors;

public class ArmyGridPanel extends JPanel{
	public ArmyGridPanel(int rows, int columms) {
        setLayout(new GridLayout(rows, columms, 10, 10));
        setBackground(GameColors.PANEL);
    }
}
