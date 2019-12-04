package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class NewVentanaPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewVentanaPrincipal frame = new NewVentanaPrincipal();
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
	public NewVentanaPrincipal() {
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
				NewVentanaInsertRegistros ventanaInRegistros = new NewVentanaInsertRegistros();
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
