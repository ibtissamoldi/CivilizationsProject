package M3.GUIgame.mainPanels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import M3.GUIgame.GameColors;
import M3.GUIgame.MainFrame;
import M3.game.Civilization;

public class ReportsPanel extends JPanel {
	
	private MainFrame frame;
	private Civilization civ;
	private JPanel container_panel;
	
	public ReportsPanel(Civilization civ, MainFrame frame) {
		this.civ = civ;
		this.frame = frame;
		
		this.setLayout(new BorderLayout());
		
		container_panel = new JPanel();
		container_panel.setLayout(new BoxLayout(container_panel, BoxLayout.Y_AXIS));
		container_panel.setBackground(GameColors.BACKGROUND);
		
		container_panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12)); 
		
		
		List<String[]> simulacionBD = frame.getDb().loadBattleReports(frame.getCivId());
		
		for (String[] registro : simulacionBD) {
			JPanel fila = crearFilaEstiloMenu(registro[0], registro[1], registro[2]);
			container_panel.add(fila);
			
			container_panel.add(Box.createRigidArea(new Dimension(0, 8)));
		}
		
		JScrollPane scrollPane = new JScrollPane(container_panel);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		this.add(scrollPane, BorderLayout.CENTER);
	}
	
	private JPanel crearFilaEstiloMenu(String titulo, String textoColumna1, String textoColumna2) {
		JPanel filaPanel = new JPanel();
		filaPanel.setLayout(new BoxLayout(filaPanel, BoxLayout.X_AXIS));
		
		filaPanel.setBackground(GameColors.PANEL);
		filaPanel.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 1));
		
		filaPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		filaPanel.setPreferredSize(new Dimension(500, 50));
		
		JLabel lblTitulo = new JLabel("   " + titulo);
		lblTitulo.setFont(new Font("Serif", Font.BOLD, 15));
		lblTitulo.setForeground(GameColors.TEXT);
		
		JButton btnVerDetalles = new JButton("VIEW DETAILS");
		btnVerDetalles.setFont(new Font("Serif", Font.BOLD, 11));
		btnVerDetalles.setForeground(GameColors.TEXT);
		btnVerDetalles.setBackground(GameColors.BUTTON);
		
		btnVerDetalles.setContentAreaFilled(false);
		btnVerDetalles.setFocusPainted(false);
		btnVerDetalles.setOpaque(true);
		
		btnVerDetalles.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 1));
		btnVerDetalles.setPreferredSize(new Dimension(120, 28));
		btnVerDetalles.setMaximumSize(new Dimension(120, 28));
		
		btnVerDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirDialogosDetalle(titulo, textoColumna1, textoColumna2);
			}
		});
		
		btnVerDetalles.addMouseListener(new MouseAdapter() {
		    public void mouseEntered(MouseEvent ev) {
		        btnVerDetalles.setBackground(GameColors.BUTTON_HOVER);
		    }
		    public void mouseExited(MouseEvent e) {
		        btnVerDetalles.setBackground(GameColors.BUTTON);
		    }
		});
		
		filaPanel.add(lblTitulo);
		filaPanel.add(Box.createHorizontalGlue());
		filaPanel.add(btnVerDetalles);
		filaPanel.add(Box.createRigidArea(new Dimension(15, 0)));
		
		return filaPanel;
	}
	
	private void abrirDialogosDetalle(String titulo, String col1Text, String col2Text) {
		JDialog dialog1 = crearDialogoPersonalizado("Battle Reports - " + titulo, col1Text);
		JDialog dialog2 = crearDialogoPersonalizado("Battle Log - " + titulo, col2Text);
		
		dialog1.setBounds(frame.getX()+180, frame.getY()+180, 360, 360);
		dialog2.setBounds(frame.getX() + 580, frame.getY()+180,360,360);
		
		dialog1.setVisible(true);
		dialog2.setVisible(true);
	}
	
	private JDialog crearDialogoPersonalizado(String tituloDialogo, String contenidoTexto) {
		JDialog dialog = new JDialog(frame, tituloDialogo, false);
		dialog.setSize(360, 260);
		dialog.setLayout(new BorderLayout(10, 10));
		
		JPanel panelContenido = new JPanel(new BorderLayout(5, 5));
		panelContenido.setBackground(GameColors.PANEL);
		panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JTextArea txtArea = new JTextArea(contenidoTexto);
		txtArea.setEditable(false);
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);
		txtArea.setBackground(GameColors.PANEL);
		txtArea.setForeground(GameColors.TEXT);
		txtArea.setFont(new Font("sansserif", Font.PLAIN, 13));
		
		JScrollPane textScroll = new JScrollPane(txtArea);
		textScroll.setBorder(null);
		
		JButton btnCerrar = new JButton("CLOSE");
		btnCerrar.setFont(new Font("Georgia", Font.BOLD, 12));
		btnCerrar.setForeground(GameColors.TEXT);
		btnCerrar.setBackground(GameColors.BUTTON);
		btnCerrar.setContentAreaFilled(false);
		btnCerrar.setFocusPainted(false);
		btnCerrar.setOpaque(true);
		btnCerrar.setBorder(BorderFactory.createLineBorder(GameColors.BORDER, 1));
		btnCerrar.setPreferredSize(new Dimension(80, 25));
		
		btnCerrar.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			dialog.dispose();
    		}
    		});		
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(GameColors.PANEL);
		southPanel.add(btnCerrar);
		
		panelContenido.add(textScroll, BorderLayout.CENTER);
		panelContenido.add(southPanel, BorderLayout.SOUTH);
		
		dialog.add(panelContenido);
		return dialog;
	}

}