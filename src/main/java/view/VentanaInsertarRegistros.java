package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.service.ServiceRegistry;
import hibernateutil.HibernateUtil;
import model.TEstaciones;
import model.TLineaEstacion;
import model.TLineaEstacionId;
import model.TLineas;

import javax.persistence.PersistenceException;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaInsertarRegistros extends JFrame {

	private JPanel contentPane;
	private JTextField tfNumLinea;
	private JTextField tfNumEstacion;
	private JTextField tfOrden;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaInsertarRegistros frame = new VentanaInsertarRegistros();
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
	public VentanaInsertarRegistros() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JLabel lblNmeroEstacion = new JLabel("Número Estación:");
		
		JLabel lblNumeroLnea = new JLabel("Número Línea:");
		
		JLabel lblOrden = new JLabel("Orden:");
		
		tfNumLinea = new JTextField();
		tfNumLinea.setColumns(10);
		
		tfNumEstacion = new JTextField();
		tfNumEstacion.setColumns(10);
		
		tfOrden = new JTextField();
		tfOrden.setColumns(10);
		
		JLabel lblNuevaLnea = new JLabel("Nueva Línea");
		lblNuevaLnea.setFont(new Font("Dialog", Font.BOLD, 24));
		final JTextPane tpNotificaciones = new JTextPane();
		JButton btnInsertarLnea = new JButton("INSERTAR LÍNEA");
		btnInsertarLnea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				org.hibernate.Transaction tr = session.beginTransaction();
				//TLineaEstacionId idLinea = new TLineaEstacionId(Integer.valueOf(tfNumLinea.getText()), Integer.valueOf(tfNumEstacion.getText()));
				TLineas linea = session.load(TLineas.class, Integer.valueOf(tfNumLinea.getText()));
				TEstaciones estacion = session.load(TEstaciones.class, Integer.valueOf(tfNumEstacion.getText()));
				TLineaEstacionId idLineaEstacion = new TLineaEstacionId(linea.getCodLinea(),estacion.getCodEstacion());
				
				TLineaEstacion tle = new TLineaEstacion(idLineaEstacion, null, null,Integer.valueOf(tfOrden.getText()));
				try {
					session.save(tle);
					tr.commit();
					tpNotificaciones.setText("Insertado correctamente!!");
				}catch(ConstraintViolationException cve) {
					tpNotificaciones.setText("Error al insertar!!");
				}catch(PersistenceException pe) {
					tpNotificaciones.setText("Error al insertar!!");
				}
			}
		});
		
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnInsertarLnea)
							.addGap(18)
							.addComponent(tpNotificaciones, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNuevaLnea)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNumeroLnea)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNumLinea, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblOrden)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfOrden, 0, 0, Short.MAX_VALUE))
								.addComponent(lblNmeroEstacion, Alignment.LEADING))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNumEstacion, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblNuevaLnea)
					.addGap(24)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNumeroLnea)
						.addComponent(tfNumLinea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNmeroEstacion)
						.addComponent(tfNumEstacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOrden)
						.addComponent(tfOrden, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 101, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(tpNotificaciones)
						.addComponent(btnInsertarLnea, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPane.setLayout(gl_contentPane);
	}
}
