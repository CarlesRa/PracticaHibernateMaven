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
import model.TAccesos;
import model.TLineas;

public class VentanaListarAccesos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tVerTrenes;
	private JTextField tfCodAcceso;
	private JTextField tfDescripcion;
	private ArrayList<TAccesos> accesos;
	private JTextField tfCodEstacion;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaListarAccesos frame = new VentanaListarAccesos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public VentanaListarAccesos() {
		setBounds(100, 100, 896, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCodTren = new JLabel("Cod. ACCESO:");
		
		tfCodAcceso = new JTextField();
		tfCodAcceso.setColumns(10);
		
		JLabel lblNombre = new JLabel("DESCRIPCIÓN:");
		
		tfDescripcion = new JTextField();
		tfDescripcion.setColumns(10);
		
		tVerTrenes = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"CodAcceso", "Descripción", "CodEstación"});
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TAccesos> query = session.createQuery("from TAccesos");
		accesos =(ArrayList<TAccesos>) query.list();
		for(TAccesos t : accesos) {
			Object[] row = {t.getCodAcceso(), t.getDescripcion(), t.getTEstaciones().getCodEstacion()};
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
					TAccesos acceso = session.load(TAccesos.class, Integer.parseInt(tfCodAcceso.getText()));
					rellenarCampos(acceso);
					tVerTrenes.setRowSelectionInterval(acceso.getCodAcceso()-1, acceso.getCodAcceso()-1);
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
					
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) + 1)));
					TAccesos acceso = session.load(TAccesos.class, Integer.parseInt(tfCodAcceso.getText()));
					tVerTrenes.setRowSelectionInterval(acceso.getCodAcceso()-1, acceso.getCodAcceso()-1);
					rellenarCampos(acceso);
					
				}catch(NumberFormatException nfe){
					mensajeCampoCodigoVacio();
				}
				catch (IllegalArgumentException npe) {
					mensajeNoRegistros();
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) - 1)));
				}
				catch(ObjectNotFoundException infe) {
					mensajeNoRegistros();
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) - 1)));
				}catch(HibernateException he) {
					mensajeNoRegistros();
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) - 1)));
				}
				
			}
		});
		
		JButton btPrevious = new JButton("<");
		btPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) - 1)));
					TAccesos linea = session.load(TAccesos.class, Integer.parseInt(tfCodAcceso.getText()));
					tVerTrenes.setRowSelectionInterval(linea.getCodAcceso()-1, linea.getCodAcceso()-1);
					rellenarCampos(linea);
				}catch (NumberFormatException nfe) {
					mensajeCampoCodigoVacio();
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) + 1)));
				}
				catch(ObjectNotFoundException infe) {
					mensajeNoRegistros();
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) + 1)));
				}catch(IllegalArgumentException iae) {
					mensajeNoRegistros();
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) + 1)));
				}catch(HibernateException he) {
					mensajeNoRegistros();
					tfCodAcceso.setText(String.valueOf((Integer.parseInt(tfCodAcceso.getText()) + 1)));
				}
			}
		});
		
		JButton btFirst = new JButton("<<");
		btFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TAccesos firstAcceso = accesos.get(0);
				rellenarCampos(firstAcceso);
				seleccionarFila(firstAcceso);
			}
		});
		
		JButton btLastt = new JButton(">>");
		btLastt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TAccesos lastAcceso = accesos.get(accesos.size() - 1);
				rellenarCampos(lastAcceso);
				seleccionarFila(lastAcceso);
			}
		});
		
		JLabel lblCodEstacin = new JLabel("Cod. ESTACIÓN:");
		
		tfCodEstacion = new JTextField();
		tfCodEstacion.setColumns(10);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblNombre)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfDescripcion))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btFirst)
									.addGap(27)
									.addComponent(btPrevious)
									.addGap(102)
									.addComponent(lblCodTren)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfCodAcceso, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
							.addGap(61)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnLocalizar)
								.addComponent(lblCodEstacin))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
									.addComponent(btNext)
									.addGap(34)
									.addComponent(btLastt))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfCodEstacion, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))))
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
							.addComponent(tfCodAcceso, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLocalizar)))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNombre)
								.addComponent(tfDescripcion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCodEstacin)
								.addComponent(tfCodEstacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void rellenarCampos(TAccesos acceso) {
		try {
			
		tfCodAcceso.setText(String.valueOf(acceso.getCodAcceso()));
		tfDescripcion.setText(acceso.getDescripcion());
		tfCodEstacion.setText(String.valueOf(acceso.getTEstaciones().getCodEstacion()));
		
		}catch (ObjectNotFoundException onfe) {
			
		}
	}

	public int tripPosition(TAccesos acceso) {
		return accesos.indexOf(acceso);
	}
	
	public void mensajeNoRegistros() {
		JOptionPane.showMessageDialog(null,"No hay más registros..");
	}

	public void mensajeCampoCodigoVacio() {
		JOptionPane.showMessageDialog(null,"No ha introducido ningun Código");
	}
	
	public void seleccionarFila(TAccesos acceso) {
		tVerTrenes.setRowSelectionInterval(tripPosition(acceso), tripPosition(acceso));
	}
}
