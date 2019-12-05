package view;

import java.awt.EventQueue;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hibernateutil.HibernateUtil;
import model.TEstaciones;
import model.TViajes;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaVerEStacionVIajes extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfIdEstacion;
	private JTextField tfNombreEstación;
	private JTextField tfNumLineas;
	private JTextField tfNumAcceso;
	private JTextField tfViajesDestinos;
	private JTextField tfViajesProcedencia;
	private JTable tIdas;
	private JTable tVueltas;
	private ArrayList<TEstaciones>estaciones;
	private ArrayList<TViajes>viajes;
	private int posicion;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	public VentanaVerEStacionVIajes() {
		posicion = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 578, 452);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		scrollPane = new JScrollPane();
		scrollPane_1 = new JScrollPane();
		
		JLabel lblCdigoEstacin = new JLabel("CÓDIGO ESTACIÓN:");
		
		tfIdEstacion = new JTextField();
		tfIdEstacion.setColumns(10);
		
		JLabel lblNombreEstacin = new JLabel("NOMBRE ESTACIÓN:");
		
		tfNombreEstación = new JTextField();
		tfNombreEstación.setColumns(10);
		
		JLabel lblNumLneas = new JLabel("NUM. LÍNEAS:");
		
		tfNumLineas = new JTextField();
		tfNumLineas.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("NUM. ACCESOS:");
		
		tfNumAcceso = new JTextField();
		tfNumAcceso.setColumns(10);
		
		JLabel lblNumViajesDestino = new JLabel("NUM. VIAJES DESTINO:");
		
		tfViajesDestinos = new JTextField();
		tfViajesDestinos.setColumns(10);
		
		JLabel lblNumviajesProcedencia = new JLabel("NUM.VIAJES PROCEDENCIA:");
		
		tfViajesProcedencia = new JTextField();
		tfViajesProcedencia.setColumns(10);
		
		
		
		JLabel lblDatosViajesDestino = new JLabel("DATOS VIAJES DESTINO");
		
		
		
		
		
		JLabel lblDatosViajesProcedencia = new JLabel("DATOS VIAJES PROCEDENCIA");
	
		
		
		Session session = HibernateUtil.getSessionFactory().openSession();		
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TEstaciones> query = session.createQuery("from TEstaciones");
		estaciones =(ArrayList<TEstaciones>) query.list();


		@SuppressWarnings("unchecked")
		Query<TViajes> query1 = session.createQuery("from TViajes");
		viajes =(ArrayList<TViajes>)query1.list();
		session.close();
		
		llenarDatosEstacion(estaciones.get(0));
		
		JButton button = new JButton("<");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				posicion--;
				try {
					TEstaciones estacion = estaciones.get(posicion);
					llenarDatosEstacion(estacion);
					}catch(IndexOutOfBoundsException npe) {
						JOptionPane.showInternalMessageDialog(null, "No quedan más registros");
					}
			}
		});
		
		JButton button_1 = new JButton(">");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				posicion++;
				try {
					TEstaciones estacion = estaciones.get(posicion);
					llenarDatosEstacion(estacion);
				}catch(IndexOutOfBoundsException npe) {
					JOptionPane.showInternalMessageDialog(null, "No quedan más registros");
				}
			}
		});
		
		JButton button_2 = new JButton("<<");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				posicion = 0;
				llenarDatosEstacion(estaciones.get(0));
			}
		});
		
		JButton button_3 = new JButton(">>");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				posicion = estaciones.size() - 1;
				llenarDatosEstacion(estaciones.get(estaciones.size()-1));
			}
		});
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
										.addComponent(lblCdigoEstacin)
										.addComponent(lblNumLneas)
										.addComponent(lblNewLabel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
										.addComponent(tfNumAcceso, 0, 0, Short.MAX_VALUE)
										.addComponent(tfNumLineas, 0, 0, Short.MAX_VALUE)
										.addComponent(tfIdEstacion, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNumViajesDestino)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfViajesDestinos, 0, 0, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNombreEstacin)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfNombreEstación, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNumviajesProcedencia)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfViajesProcedencia, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))))
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(button_2)
							.addPreferredGap(ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
							.addComponent(button)
							.addGap(18)
							.addComponent(button_1)
							.addGap(176)
							.addComponent(button_3))
						.addComponent(lblDatosViajesDestino)
						.addComponent(lblDatosViajesProcedencia))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCdigoEstacin)
						.addComponent(tfIdEstacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNombreEstacin)
						.addComponent(tfNombreEstación, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumLneas)
						.addComponent(tfNumLineas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(tfNumAcceso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumViajesDestino)
						.addComponent(tfViajesDestinos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNumviajesProcedencia)
						.addComponent(tfViajesProcedencia, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(15)
					.addComponent(lblDatosViajesDestino)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addGap(13)
					.addComponent(lblDatosViajesProcedencia)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(button_2)
						.addComponent(button_3)
						.addComponent(button_1)
						.addComponent(button))
					.addContainerGap())
		);
		
		
		
		
		
		
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void llenarDatosEstacion(TEstaciones estacion) {
		tfIdEstacion.setText(String.valueOf(estacion.getCodEstacion()));
		tfNombreEstación.setText(estacion.getNombre());
		tfNumLineas.setText(String.valueOf(estacion.getNumlineas()));
		tfNumAcceso.setText(String.valueOf(estacion.getNumaccesos()));
		tfViajesDestinos.setText(String.valueOf(estacion.getNumviajesdestino()));
		tfViajesProcedencia.setText(String.valueOf(estacion.getNumviajesprocedencia()));
		llenarDatosViajes(estacion);
	}
	public void llenarDatosViajes(TEstaciones estacion) {
		
		DefaultTableModel dtmIdas = new DefaultTableModel();
		tIdas = new JTable(dtmIdas);
		dtmIdas.setColumnIdentifiers(new Object[] {"Código","Nombre"});
		
		DefaultTableModel dtmVueltas = new DefaultTableModel();
		tVueltas = new JTable(dtmVueltas);
		
		dtmVueltas.setColumnIdentifiers(new Object[] {"Código","Nombre"});
		for(TViajes t : viajes) {
			if(estacion.getCodEstacion() == t.getEstacionorigen()) {
				System.out.println("Idas");
				Object[] row = {t.getCodViaje(), t.getNombre()};
				dtmVueltas.addRow(row);

			}
			if(estacion.getCodEstacion() == t.getEstaciondestino()) {
				System.out.println("Vueltas");
				Object[] row = {t.getCodViaje(), t.getNombre()};
				dtmIdas.addRow(row);
			}
		}
		
		tIdas.setModel(dtmIdas);
		scrollPane = new JScrollPane(tIdas);
		tVueltas.setModel(dtmVueltas);
		scrollPane_1 = new JScrollPane(tVueltas);
		contentPane.add(scrollPane);
		scrollPane.setBounds(17, 141, 544, 93);
		contentPane.add(scrollPane_1);
		scrollPane_1.setBounds(17, 274, 544, 105);
	}
}
