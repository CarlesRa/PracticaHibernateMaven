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
import model.TCocheras;
import model.TLineaEstacion;
import model.TLineaEstacionId;

public class VentanaListarLineasEstacion extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tVerTrenes;
	private JTextField tfCodLinea;
	private ArrayList<TLineaEstacion> lineasEstaciones;
	private JTextField tfOrden;
	private JTextField tfCodEstacion;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaListarLineasEstacion frame = new VentanaListarLineasEstacion();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public VentanaListarLineasEstacion() {
		setBounds(100, 100, 896, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCodCochera = new JLabel("Cod.LINEA:");
		
		tfCodLinea = new JTextField();
		tfCodLinea.setColumns(10);
		
		tVerTrenes = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"CodLinea", "CodEstación", "Orden"});
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TLineaEstacion> query = session.createQuery("from TLineaEstacion");
		lineasEstaciones =(ArrayList<TLineaEstacion>) query.list();
		for(TLineaEstacion t : lineasEstaciones) {
			Object[] row = {t.getId().getCodLinea(), t.getId().getCodEstacion(), t.getOrden()};
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
					TLineaEstacion lineaEstacion = session.load(TLineaEstacion.class, new TLineaEstacionId(Integer.parseInt(tfCodLinea.getText()),Integer.parseInt(tfCodEstacion.getText())));
					rellenarCampos(lineaEstacion);
					tVerTrenes.setRowSelectionInterval(lineaEstacion.getId().getCodLinea()-1, lineaEstacion.getId().getCodEstacion()-1);
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
					
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) + 1)));
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) + 1)));
					TLineaEstacion lineaEstacion = session.load(TLineaEstacion.class, new TLineaEstacionId(Integer.parseInt(tfCodLinea.getText()),Integer.parseInt(tfCodEstacion.getText())));
					tVerTrenes.setRowSelectionInterval(lineaEstacion.getId().getCodLinea()-1, lineaEstacion.getId().getCodEstacion()-1);
					rellenarCampos(lineaEstacion);
					
				}catch(NumberFormatException nfe){
					mensajeCampoCodigoVacio();
				}
				catch (IllegalArgumentException npe) {
					mensajeNoRegistros();
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) - 1)));
				}
				catch(ObjectNotFoundException infe) {
					mensajeNoRegistros();
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) - 1)));
				}catch(HibernateException he) {
					mensajeNoRegistros();
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) - 1)));
				}
				
			}
		});
		
		JButton btPrevious = new JButton("<");
		btPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) - 1)));
					tfCodEstacion.setText(String.valueOf((Integer.parseInt(tfCodEstacion.getText()) - 1)));
					TLineaEstacion lineaEstacion = session.load(TLineaEstacion.class, new TLineaEstacionId(Integer.parseInt(tfCodLinea.getText()),Integer.parseInt(tfCodEstacion.getText())));
					tVerTrenes.setRowSelectionInterval(lineaEstacion.getId().getCodLinea()-1, lineaEstacion.getId().getCodEstacion()-1);
					rellenarCampos(lineaEstacion);
				}catch (NumberFormatException nfe) {
					mensajeCampoCodigoVacio();
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) + 1)));
				}
				catch(ObjectNotFoundException infe) {
					mensajeNoRegistros();
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) + 1)));
				}catch(IllegalArgumentException iae) {
					mensajeNoRegistros();
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) + 1)));
				}catch(HibernateException he) {
					mensajeNoRegistros();
					tfCodLinea.setText(String.valueOf((Integer.parseInt(tfCodLinea.getText()) + 1)));
				}
			}
		});
		
		JButton btFirst = new JButton("<<");
		btFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TLineaEstacion firstAcceso = lineasEstaciones.get(0);
				rellenarCampos(firstAcceso);
				seleccionarFila(firstAcceso);
			}
		});
		
		JButton btLastt = new JButton(">>");
		btLastt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TLineaEstacion lastAcceso = lineasEstaciones.get(lineasEstaciones.size() - 1);
				rellenarCampos(lastAcceso);
				seleccionarFila(lastAcceso);
			}
		});
		
		JLabel lblCodEstacin = new JLabel("ORDEN:");
		
		tfOrden = new JTextField();
		tfOrden.setColumns(10);
		
		JLabel lblCodestacin = new JLabel("Cod.ESTACIÓN");
		
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
							.addComponent(btFirst)
							.addGap(27)
							.addComponent(btPrevious)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblCodCochera)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfCodLinea, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(lblCodestacin)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfCodEstacion, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(lblCodEstacin)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfOrden, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)))
							.addGap(35)
							.addComponent(btnLocalizar)
							.addPreferredGap(ComponentPlacement.RELATED, 165, Short.MAX_VALUE)
							.addComponent(btNext)
							.addGap(34)
							.addComponent(btLastt)))
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
							.addComponent(btNext)
							.addComponent(lblCodCochera)
							.addComponent(tfCodLinea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblCodestacin)
							.addComponent(tfCodEstacion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnLocalizar))
					.addGap(26)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfOrden, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCodEstacin))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void rellenarCampos(TLineaEstacion lineaEstacion) {
		try {
			
		tfCodLinea.setText(String.valueOf(lineaEstacion.getId().getCodLinea()));
		tfCodEstacion.setText(String.valueOf(lineaEstacion.getId().getCodEstacion()));
		tfOrden.setText(String.valueOf(String.valueOf(lineaEstacion.getOrden())));
		
		}catch (ObjectNotFoundException onfe) {
			
		}
	}

	public int tripPosition(TLineaEstacion cochera) {
		return lineasEstaciones.indexOf(cochera);
	}
	
	public void mensajeNoRegistros() {
		JOptionPane.showMessageDialog(null,"No hay más registros..");
	}

	public void mensajeCampoCodigoVacio() {
		JOptionPane.showMessageDialog(null,"No ha introducido ningun Código");
	}
	
	public void seleccionarFila(TLineaEstacion lineaEstacion) {
		tVerTrenes.setRowSelectionInterval(tripPosition(lineaEstacion), tripPosition(lineaEstacion));
	}


}
