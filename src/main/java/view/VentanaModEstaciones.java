package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hibernateutil.HibernateUtil;
import model.TEstaciones;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaModEstaciones extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfIdEstacion;
	private JTextField tfNumAccesos;
	private JTextField tfNumLinea;
	private JTextField tfNumDestinos;
	private JTextField tfNumViajes;

	public VentanaModEstaciones() {
		setBounds(100, 100, 536, 203);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblId = new JLabel("Id Estacion:");
		
		tfIdEstacion = new JTextField();
		tfIdEstacion.setColumns(10);
		
		JButton btLocalizar = new JButton("LOCALIZAR");
		btLocalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					TEstaciones estacion = session.load(TEstaciones.class, Integer.parseInt(tfIdEstacion.getText()));
					llenarCampos(estacion);
					session.close();
				}catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Los Campos No estan llenos...");
				}
				catch(HibernateException he) {
					JOptionPane.showMessageDialog(null, "La linea no existe");
				}
			}
		});
		
		JButton btnModificar = new JButton("MODIFICAR");
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				Transaction tr = session.beginTransaction();
				try {
					TEstaciones estacion = session.load(TEstaciones.class, Integer.parseInt(tfIdEstacion.getText()));
					estacion.setNumaccesos(Integer.parseInt(tfNumAccesos.getText()));
					estacion.setNumlineas(Integer.valueOf(tfNumLinea.getText()));
					estacion.setNumviajesdestino(Integer.parseInt(tfNumDestinos.getText()));
					estacion.setNumviajesprocedencia(Integer.parseInt(tfNumViajes.getText()));
					session.update(estacion);
					tr.commit();
					JOptionPane.showMessageDialog(null, "Campos modificados correctamente!!");
					llenarCampos(estacion);
					session.close();
					sessionF.close();
				}catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null, "Los Campos No estan llenos...");
				}
				catch(HibernateException he) {
					JOptionPane.showMessageDialog(null, "La linea no existe");
				}
			}
		});
		
		JLabel lblNumaccesos = new JLabel("NumAccesos");
		
		JLabel lblNumlineas = new JLabel("NumLineas");
		
		JLabel lblNumviajesdestino = new JLabel("NumViajesDestino");
		
		JLabel lblNumviajesprocedencia = new JLabel("NumViajesProcedencia");
		
		tfNumAccesos = new JTextField();
		tfNumAccesos.setColumns(10);
		
		tfNumLinea = new JTextField();
		tfNumLinea.setColumns(10);
		
		tfNumDestinos = new JTextField();
		tfNumDestinos.setColumns(10);
		
		tfNumViajes = new JTextField();
		tfNumViajes.setColumns(10);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblId)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfIdEstacion, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btLocalizar))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(tfNumAccesos, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(lblNumaccesos, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tfNumLinea, 0, 0, Short.MAX_VALUE)
								.addComponent(lblNumlineas, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tfNumDestinos)
								.addComponent(lblNumviajesdestino, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnModificar))
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tfNumViajes)
								.addComponent(lblNumviajesprocedencia, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addContainerGap(7, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblId)
						.addComponent(tfIdEstacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btLocalizar))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumaccesos)
						.addComponent(lblNumlineas)
						.addComponent(lblNumviajesdestino)
						.addComponent(lblNumviajesprocedencia))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfNumAccesos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNumLinea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNumDestinos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfNumViajes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(29)
					.addComponent(btnModificar)
					.addContainerGap(117, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}
	
	public void llenarCampos(TEstaciones estacion) {
		tfNumAccesos.setText(String.valueOf(estacion.getNumaccesos()));
		tfNumLinea.setText(String.valueOf(estacion.getNumlineas()));
		tfNumDestinos.setText(String.valueOf(estacion.getNumviajesdestino()));
		tfNumViajes.setText(String.valueOf(estacion.getNumviajesprocedencia()));
	}
}
