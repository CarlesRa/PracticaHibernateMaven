package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.EmptyBorder;
import javax.swing.JMenu;

public class NewVentanaPrincipal extends JFrame implements WindowListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public NewVentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		JMenuBar menuBar = new JMenuBar();
		
		setJMenuBar(menuBar);
		
		JMenu mnConsultas = new JMenu("Consultas");
		menuBar.add(mnConsultas);
		
		JMenuItem mntmConsultarTrenes = new JMenuItem("Consultar Trenes");
		mnConsultas.add(mntmConsultarTrenes);
		mntmConsultarTrenes.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				NewVentanaListarTrenes verTrenes = new NewVentanaListarTrenes();
				verTrenes.setTitle("Econtrar trenes");
				verTrenes.setVisible(true);
				verTrenes.setDefaultCloseOperation(HIDE_ON_CLOSE);
				
			}
		});
		
		JMenuItem mntmConsultarAccesos = new JMenuItem("Consultar Accesos");
		mnConsultas.add(mntmConsultarAccesos);
		mntmConsultarAccesos.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				VentanaListarAccesos verAccesos = new VentanaListarAccesos();
				verAccesos.setTitle("Encontrar accesos");
				verAccesos.setVisible(true);
				verAccesos.setDefaultCloseOperation(HIDE_ON_CLOSE);
				
			}
		});
		
		JMenuItem mntmConsultarLineas = new JMenuItem("Consultar Lineas");
		mnConsultas.add(mntmConsultarLineas);
		mntmConsultarLineas.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				VentanaListarLineas verLineas = new VentanaListarLineas();
				verLineas.setTitle("Encontrar Lineas");
				verLineas.setVisible(true);
				verLineas.setDefaultCloseOperation(HIDE_ON_CLOSE);
				
			}
		});
		
		JMenuItem mntmConsultarEstaciones = new JMenuItem("Consultar Estaciones");
		mnConsultas.add(mntmConsultarEstaciones);
		mntmConsultarEstaciones.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				VentanaListarEstaciones verEstaciones = new VentanaListarEstaciones();
				verEstaciones.setTitle("Encontrar Estaciones");
				verEstaciones.setVisible(true);
				verEstaciones.setDefaultCloseOperation(HIDE_ON_CLOSE);
				
			}
		});
		
		JMenuItem mntmConsultarCocheras = new JMenuItem("Consultar Cocheras");
		mnConsultas.add(mntmConsultarCocheras);
		mntmConsultarCocheras.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				VentanaListarCocheras verCocheras = new VentanaListarCocheras();
				verCocheras.setTitle("Encontrar Cocheras");
				verCocheras.setVisible(true);
				verCocheras.setDefaultCloseOperation(HIDE_ON_CLOSE);
				
			}
		});
		
		JMenuItem mntmConsultarLineasestacin = new JMenuItem("Consultar LineasEstación");
		mnConsultas.add(mntmConsultarLineasestacin);
		mntmConsultarLineasestacin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				VentanaListarLineasEstacion verLineasEstacion = new VentanaListarLineasEstacion();
				verLineasEstacion.setTitle("Encontrar LineaEstación");
				verLineasEstacion.setVisible(true);
				verLineasEstacion.setDefaultCloseOperation(HIDE_ON_CLOSE);
				
			}
		});
		
		JMenuItem mntmConsultarViajes = new JMenuItem("Consultar Viajes");
		mnConsultas.add(mntmConsultarViajes);
		mntmConsultarViajes.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				VentanaListarViajes verViajes = new VentanaListarViajes();
				verViajes.setTitle("Encontrar Viajes");
				verViajes.setVisible(true);
				verViajes.setDefaultCloseOperation(HIDE_ON_CLOSE);
				
			}
		});
		
		JMenu mnInsertar = new JMenu("Insertar");
		menuBar.add(mnInsertar);
		
		JMenuItem mntmInsertarLineaEstacin = new JMenuItem("Insertar Linea Estación");
		mnInsertar.add(mntmInsertarLineaEstacin);
		mntmInsertarLineaEstacin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				NewVentanaInsertRegistros verInsertar = new NewVentanaInsertRegistros();
				verInsertar.setTitle("Ingresar Linea");
				verInsertar.setVisible(true);
				verInsertar.setDefaultCloseOperation(HIDE_ON_CLOSE);
			}
		});
		
		JMenu mnModificar = new JMenu("Modificar");
		menuBar.add(mnModificar);
		
		JMenuItem mntmModificarEstacin = new JMenuItem("Modificar Estación");
		mnModificar.add(mntmModificarEstacin);
		mntmModificarEstacin.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				VentanaModEstaciones modEstacion = new VentanaModEstaciones();
				modEstacion.setTitle("Modificar Estación");
				modEstacion.setVisible(true);
				modEstacion.setDefaultCloseOperation(HIDE_ON_CLOSE);
				
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

	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
