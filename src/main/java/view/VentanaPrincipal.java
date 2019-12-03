package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class VentanaPrincipal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		JMenuBar menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		
		JMenuItem mntmNuevaLinea = new JMenuItem("Nueva Linea");
		menuBar.add(mntmNuevaLinea);
		
		JMenuItem mntmListarTablas = new JMenuItem("Listar Tablas");
		menuBar.add(mntmListarTablas);
		
		JMenuItem mntmModificarC = new JMenuItem("Modificar Campos");
		menuBar.add(mntmModificarC);
		mntmNuevaLinea.setBorder(border);
		mntmListarTablas.setBorder(border);
		mntmModificarC.setBorder(border);
		mntmNuevaLinea.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				VentanaInsertarRegistros ventanaInRegistros = new VentanaInsertarRegistros();
				ventanaInRegistros.setVisible(true);
				ventanaInRegistros.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);	
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 430, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGap(0, 260, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}

}
