package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.persistence.PersistenceException;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.query.Query;

import hibernateutil.HibernateUtil;
import model.TEstaciones;
import model.TLineaEstacion;
import model.TLineaEstacionId;
import model.TLineas;
import model.TViajes;

public class NewVentanaInsertRegistros extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField tfNumLinea;
	private JTextField tfNumEstacion;
	private JTextField tfOrden;
	private ArrayList<TLineaEstacion> lineas;
	public NewVentanaInsertRegistros() {
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
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		

		Query<TLineaEstacion> query = session.createQuery("from TLineaEstacion");
		lineas =(ArrayList<TLineaEstacion>) query.list();
		
		JLabel lblNuevaLnea = new JLabel("Nueva Línea");
		lblNuevaLnea.setFont(new Font("Dialog", Font.BOLD, 24));
		final JTextPane tpNotificaciones = new JTextPane();
		JButton btnInsertarLnea = new JButton("INSERTAR LÍNEA");
		btnInsertarLnea.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					SessionFactory sessionF = HibernateUtil.getSessionFactory();
					Session session = sessionF.openSession();
					org.hibernate.Transaction tr = session.beginTransaction();
					TLineas linea = session.load(TLineas.class, Integer.valueOf(tfNumLinea.getText()));
					TEstaciones estacion = session.load(TEstaciones.class, Integer.valueOf(tfNumEstacion.getText()));
					TLineaEstacionId idLineaEstacion = new TLineaEstacionId(linea.getCodLinea(),estacion.getCodEstacion());
					TLineaEstacion tle = new TLineaEstacion(idLineaEstacion, null, null,Integer.valueOf(tfOrden.getText()));
					boolean esCorrecto = esOrdenCorrecto(Integer.valueOf(tfNumLinea.getText())
							, Integer.valueOf(tfNumEstacion.getText()), Integer.parseInt(tfOrden.getText()));
					if(esCorrecto) {
						session.save(tle);
						tr.commit();
						tpNotificaciones.setText("Insertado correctamente!!");
					}
					else {
						tpNotificaciones.setText("Orden incorrecto!!");
					}
					
				}catch (NumberFormatException nfe) {
					tpNotificaciones.setText("Dato erróneo o nulo");
				}
				catch(ConstraintViolationException cve) {
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

	public boolean esOrdenCorrecto(int codLinea, int codEstacion, int orden) {
		ArrayList<TLineaEstacion> lineasFiltradas = new ArrayList<TLineaEstacion>();
		int contador = 0;
		for(int i=0; i<lineas.size(); i++) {
			if(lineas.get(i).getId().getCodLinea() == codLinea) {
				lineasFiltradas.add(lineas.get(i));
			}
		}
		for(int i=0; i<lineasFiltradas.size(); i++) {
			if(lineasFiltradas.get(i).getOrden() == orden) {
				contador++;
			}
		}
		if(contador > 0) {
			return false;
		}
		else
		return true;
	}
}
