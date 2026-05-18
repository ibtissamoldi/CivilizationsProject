package M3.GUIgame.mainPanels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
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
	
	private static final Color VERDE_BOTON = new Color(74, 93, 58);
	private static final Color ROSA_SEPARADOR = new Color(230, 215, 225);
	private static final Color TEXTO_DORADO = new Color(243, 219, 142);

	public ReportsPanel(Civilization civ, MainFrame frame) {
		this.civ = civ;
		this.frame = frame;
		
		this.setLayout(new java.awt.BorderLayout());
		
		container_panel = new JPanel();
		container_panel.setLayout(new BoxLayout(container_panel, BoxLayout.Y_AXIS));
		container_panel.setBackground(ROSA_SEPARADOR); 
		
		container_panel.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6)); 
		
		//List<String[]> simulacionBD = obtenerDatosSimulados();
		
		List<String[]> simulacionBD = frame.getDb().loadBattleReports(frame.getCivId());
		
		for (String[] registro : simulacionBD) {
			JPanel fila = crearFilaEstiloMenu(registro[0], registro[1], registro[2]);
			container_panel.add(fila);
			
			container_panel.add(javax.swing.Box.createRigidArea(new Dimension(0, 6)));
		}
		
		JScrollPane scrollPane = new JScrollPane(container_panel);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		
		this.add(scrollPane, java.awt.BorderLayout.CENTER);
	}
	
	private JPanel crearFilaEstiloMenu(String titulo, String textoColumna1, String textoColumna2) {
		JPanel filaPanel = new JPanel();
		filaPanel.setLayout(new BoxLayout(filaPanel, BoxLayout.X_AXIS));
		
		filaPanel.setBackground(VERDE_BOTON);
		
		filaPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
		filaPanel.setPreferredSize(new Dimension(500, 50));
		
		JLabel lblTitulo = new JLabel("   " + titulo);
		lblTitulo.setFont(new Font("Georgia", Font.BOLD, 15));
		lblTitulo.setForeground(TEXTO_DORADO);
		
		JButton btnVerDetalles = new JButton("VIEW DETAILS");
		btnVerDetalles.setFont(new Font("Georgia", Font.BOLD, 11));
		btnVerDetalles.setForeground(TEXTO_DORADO);
		btnVerDetalles.setBackground(VERDE_BOTON);
		
		btnVerDetalles.setContentAreaFilled(false);
		btnVerDetalles.setFocusPainted(false);
		btnVerDetalles.setOpaque(true);
		
		btnVerDetalles.setBorder(BorderFactory.createLineBorder(TEXTO_DORADO, 1));
		btnVerDetalles.setPreferredSize(new Dimension(120, 28));
		btnVerDetalles.setMaximumSize(new Dimension(120, 28));
		
		btnVerDetalles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abrirDialogosDetalle(titulo, textoColumna1, textoColumna2);
			}
		});
		
		filaPanel.add(lblTitulo);
		filaPanel.add(javax.swing.Box.createHorizontalGlue());
		filaPanel.add(btnVerDetalles);
		filaPanel.add(javax.swing.Box.createRigidArea(new Dimension(15, 0)));
		
		return filaPanel;
	}
	
	private void abrirDialogosDetalle(String titulo, String col1Text, String col2Text) {
		JDialog dialog1 = crearDialogoPersonalizado("Detalle A - " + titulo, col1Text);
		JDialog dialog2 = crearDialogoPersonalizado("Detalle B - " + titulo, col2Text);
		
		dialog1.setLocationRelativeTo(frame);
		dialog2.setLocationRelativeTo(frame);
		dialog2.setLocation(dialog2.getX() + 160, dialog2.getY() + 60); 
		
		dialog1.setVisible(true);
		dialog2.setVisible(true);
	}
	
	private JDialog crearDialogoPersonalizado(String tituloDialogo, String contenidoTexto) {
		JDialog dialog = new JDialog(frame, tituloDialogo, false);
		dialog.setSize(360, 260);
		dialog.setLayout(new java.awt.BorderLayout(10, 10));
		
		JPanel panelContenido = new JPanel(new java.awt.BorderLayout(5, 5));
		panelContenido.setBackground(VERDE_BOTON);
		panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		JTextArea txtArea = new JTextArea(contenidoTexto);
		txtArea.setEditable(false);
		txtArea.setLineWrap(true);
		txtArea.setWrapStyleWord(true);
		txtArea.setBackground(VERDE_BOTON);
		txtArea.setForeground(TEXTO_DORADO);
		txtArea.setFont(new Font("sansserif", Font.PLAIN, 13));
		
		JScrollPane textScroll = new JScrollPane(txtArea);
		textScroll.setBorder(null);
		
		JButton btnCerrar = new JButton("CLOSE");
		btnCerrar.setFont(new Font("Georgia", Font.BOLD, 12));
		btnCerrar.setForeground(TEXTO_DORADO);
		btnCerrar.setBackground(VERDE_BOTON);
		btnCerrar.setContentAreaFilled(false);
		btnCerrar.setFocusPainted(false);
		btnCerrar.setOpaque(true);
		btnCerrar.setBorder(BorderFactory.createLineBorder(TEXTO_DORADO, 1));
		btnCerrar.setPreferredSize(new Dimension(80, 25));
		btnCerrar.addActionListener(e -> dialog.dispose());
		
		JPanel southPanel = new JPanel();
		southPanel.setBackground(VERDE_BOTON);
		southPanel.add(btnCerrar);
		
		panelContenido.add(textScroll, java.awt.BorderLayout.CENTER);
		panelContenido.add(southPanel, java.awt.BorderLayout.SOUTH);
		
		dialog.add(panelContenido);
		return dialog;
	}
	/*
	private List<String[]> obtenerDatosSimulados() {
		List<String[]> datos = new ArrayList<>();
		datos.add(new String[]{"Report #1", "TEXT COLUMN 1: Enemy movement detected near coordinates [X:42, Y:12].", "TEXT COLUMN 2: 5 Scout units spotted. No engagement reported."});
		datos.add(new String[]{"Report #2", "TEXT COLUMN 1: Resource extraction complete at Gold Mine alpha.", "TEXT COLUMN 2: Total revenue +1,500 gold added to treasury."});
		datos.add(new String[]{"Report #3", "TEXT COLUMN 1: Research on Gunpowder completed successfully.", "TEXT COLUMN 2: New military assets unlocked at the Barracks."});
		return datos;
	}*/
}