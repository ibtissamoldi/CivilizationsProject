package M3.GUIgame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class DialogPanel extends JPanel {

    private JTextArea info_textarea;
    private JScrollPane scroll;
    public DialogPanel() {
	    setLayout(new BorderLayout());
	    setPreferredSize(new Dimension(0, 120));
	    setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 2));
	
	
	    info_textarea = new JTextArea(10,50);
	    info_textarea.setEditable(false);
	    info_textarea.setBackground(GameColors.PANEL);
	    info_textarea.setForeground(GameColors.TEXT_DARK);
	    info_textarea.setCaretColor(GameColors.TEXT);
	    info_textarea.setFont(new Font("Monospaced", Font.BOLD, 14));
	    info_textarea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	
	    scroll = new JScrollPane(info_textarea);
	
	    add(scroll,BorderLayout.CENTER);
    }

	public void AddMessage(String message) {
		info_textarea.append(message + "\n");
	}
	
    public void clear() {
    	info_textarea.setText("");
        
    }
}