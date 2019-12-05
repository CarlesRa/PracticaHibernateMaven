package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.HibernateException;
import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hibernateutil.HibernateUtil;
import model.TEstaciones;
import model.TViajes;

public class VentanaListarEstaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tVerTrenes;
	private JTextField tfCodEstacion;
	private JTextField tfNombre;
	private JTextField tfDireccion;
	private JTextField tfAcceso;
	private ArrayList<TEstaciones> estaciones;
	private JTextField tfLineas;
	private JTextField tfDestinos;
	private JTextField tfProcedencias;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaListarEstaciones frame = new VentanaListarEstaciones();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public VentanaListarEstaciones() {
		setBounds(100, 100, 896, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCodTren = new JLabel("Cod. ESTACIÓN:");
		
		tfCodEstacion = new JTextField();
		tfCodEstacion.setColumns(10);
		
		JLabel lblNombre = new JLabel("NOMBRE:");
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("DIRECCIÓN:");
		
		tfDireccion = new JTextField();
		tfDireccion.setColumns(10);
		
		JLabel lblLinea = new JLabel("ACCESOS:");
		
		tfAcceso = new JTextField();
		tfAcceso.setColumns(10);
		
		tVerTrenes = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"CodEstacion", "Nombre","Direccion","Accesos", "Lineas","Destinos","Procedencias"});
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TEstaciones> query = session.createQuery("from TEstaciones");
		estaciones =(ArrayList<TEstaciones>) query.list();
		for(TEstaciones t : estaciones) {
			Object[] row = {t.getCodEstacion(), t.getNombre(), t.getDireccion(), t.getNumaccesos(),t.getNumlineas(),t.getNumviajesdestino(),t.getNumviajesprocedencia()};
			dtm.addRow(row);
		}
		
		tr.commit();
		tVerTrenes.setModel(dtm);
		scrollPane.setViewportView(tVerTrenes);
		
		JButton btnLocalizar = new JButton("LOCALIZAR");
		btnLocalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					TEstaciones estacion = session.load(TEstaciones.class, Integer.parseInt(tfCodEstacion.getText()));
					rellenarCampos(estacion);
					tVerTrenes.setRowSelectionInterval(estacion.getCodEstacion()-1, estacion.getCodEstacion()-1);
				}catch(ObjectNotFoundException onfe){
					mensajeCampoCodigoVacio();
				}catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null,"Dato Erroneo....");
				}
			}
		});
		
		JButton btNext = new JButton(">");
		btNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) + 1)));
					TEstaciones estacion = session.load(TEstaciones.class, Integer.parseInt(tfCodEstacion.getText()));
					tVerTrenes.setRowSelectionInterval(estacion.getCodEstacion()-1, estacion.getCodEstacion()-1);
					rellenarCampos(estacion);
					
				}catch(NumberFormatException nfe){
					mensajeCampoCodigoVacio();
				}
				catch (IllegalArgumentException npe) {
					mensajeNoRegistros();
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) - 1)));
				}
				catch(ObjectNotFoundException infe) {
					mensajeNoRegistros();
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) - 1)));
				}catch(HibernateException he) {
					mensajeNoRegistros();
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) - 1)));
				}
				
			}
		});
		
		JButton btPrevious = new JButton("<");
		btPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) - 1)));
					TEstaciones viaje = session.load(TEstaciones.class, Integer.parseInt(tfCodEstacion.getText()));
					tVerTrenes.setRowSelectionInterval(viaje.getCodEstacion()-1, viaje.getCodEstacion()-1);
					rellenarCampos(viaje);
				}catch (NumberFormatException nfe) {
					mensajeCampoCodigoVacio();
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) + 1)));
				}
				catch(ObjectNotFoundException infe) {
					mensajeNoRegistros();
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) + 1)));
				}catch(IllegalArgumentException iae) {
					mensajeNoRegistros();
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) + 1)));
				}catch(HibernateException he) {
					mensajeNoRegistros();
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) + 1)));
				}
			}
		});
		
		JButton btFirst = new JButton("<<");
		btFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TEstaciones firstEstacion = estaciones.get(0);
				rellenarCampos(firstEstacion);
				seleccionarFila(firstEstacion);
			}
		});
		
		JButton btLastt = new JButton(">>");
		btLastt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TEstaciones lastEstacion = estaciones.get(estaciones.size() - 1);
				rellenarCampos(lastEstacion);
				seleccionarFila(lastEstacion);
			}
		});
		
		tfLineas = new JTextField();
		tfLineas.setColumns(10);
		
		JLabel lblAccesos = new JLabel("LINEAS:");
		
		JLabel lblLineas = new JLabel("DESTINOS:");
		
		tfDestinos = new JTextField();
		tfDestinos.setColumns(10);
		
		JLabel lblProcedencias = new JLabel("PROCEDENCIAS:");
		
		tfProcedencias = new JTextField();
		tfProcedencias.setColumns(10);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(lblLinea)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfAcceso, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
									.addComponent(lblAccesos)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfLineas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(lblNombre)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfNombre))
								.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
									.addComponent(btFirst)
									.addGap(27)
									.addComponent(btPrevious)
									.addGap(102)
									.addComponent(lblCodTren)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfCodEstacion, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
							.addGap(48)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblLineas)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfDestinos, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
									.addGap(61)
									.addComponent(lblProcedencias)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfProcedencias, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfDireccion, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btnLocalizar)
									.addPreferredGap(ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
									.addComponent(btNext)
									.addGap(34)
									.addComponent(btLastt)))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
					.addGap(17)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btFirst)
							.addComponent(btPrevious)
							.addComponent(btLastt)
							.addComponent(btNext))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCodTren)
							.addComponent(tfCodEstacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLocalizar)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNombre)
							.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel)
							.addComponent(tfDireccion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblLinea)
							.addComponent(tfAcceso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(tfLineas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblAccesos))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblLineas)
							.addComponent(tfDestinos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblProcedencias))
						.addComponent(tfProcedencias, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void rellenarCampos(TEstaciones estacion) {
		try {
			
		tfCodEstacion.setText(String.valueOf(estacion.getCodEstacion()));
		tfNombre.setText(estacion.getNombre());
		tfDireccion.setText(String.valueOf(estacion.getDireccion()));
		tfAcceso.setText(String.valueOf(estacion.getNumaccesos()));
		tfLineas.setText(String.valueOf(estacion.getNumlineas()));
		tfDestinos.setText(String.valueOf(estacion.getNumviajesdestino()));
		tfProcedencias.setText(String.valueOf(estacion.getNumviajesprocedencia()));
		
		}catch (ObjectNotFoundException onfe) {
			
		}
	}

	public int tripPosition(TEstaciones estacion) {
		return estaciones.indexOf(estacion);
	}
	
	public void mensajeNoRegistros() {
		JOptionPane.showMessageDialog(null,"No hay más registros..");
	}

	public void mensajeCampoCodigoVacio() {
		JOptionPane.showMessageDialog(null,"No ha introducido ningun Código");
	}
	
	public void seleccionarFila(TEstaciones estacion) {
		tVerTrenes.setRowSelectionInterval(tripPosition(estacion), tripPosition(estacion));
	}
}
