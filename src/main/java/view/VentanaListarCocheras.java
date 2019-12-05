package view;

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

import org.hibernate.ObjectNotFoundException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hibernateutil.HibernateUtil;
import model.TCocheras;


public class VentanaListarCocheras extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tVerTrenes;
	private JTextField tfCodCochera;
	private JTextField tfNombre;
	private ArrayList<TCocheras> cocheras;
	private JTextField tfDireccion;
	
	public VentanaListarCocheras() {
		setBounds(100, 100, 896, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCodCochera = new JLabel("Cod.COCHERA:");
		
		tfCodCochera = new JTextField();
		tfCodCochera.setColumns(10);
		
		JLabel lblNombre = new JLabel("NOMBRE:");
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		
		tVerTrenes = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"CodCochera", "Nombre", "Dirección"});
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TCocheras> query = session.createQuery("from TCocheras");
		cocheras =(ArrayList<TCocheras>) query.list();
		for(TCocheras t : cocheras) {
			Object[] row = {t.getCodCochera(), t.getNombre(), t.getDireccion()};
			dtm.addRow(row);
		}
		
		tr.commit();
		session.close();
		tVerTrenes.setModel(dtm);
		scrollPane.setViewportView(tVerTrenes);
		
		JButton btnLocalizar = new JButton("LOCALIZAR");
		btnLocalizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					TCocheras cochera = session.load(TCocheras.class, Integer.parseInt(tfCodCochera.getText()));
					rellenarCampos(cochera);
					if(cochera.getCodCochera() == Integer.parseInt(tfCodCochera.getText())) {
						int posicion = getPosicion(Integer.parseInt(tfCodCochera.getText()));
						tVerTrenes.setRowSelectionInterval(posicion, posicion);
					}
					session.close();
				}catch(ObjectNotFoundException onfe){
					JOptionPane.showMessageDialog(null,"No exista cochera con ese ID");
				}catch(NumberFormatException nfe) {
					mensajeCampoCodigoVacio();
				}catch(NullPointerException npe) {
					mensajeNoRegistros();
				}
			}
		});
		
		JButton btNext = new JButton(">");
		btNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					int posicion = getPosicion(Integer.parseInt(tfCodCochera.getText()));
					rellenarCampos(cocheras.get(posicion + 1));
					tVerTrenes.setRowSelectionInterval(posicion + 1, posicion +1);
					session.close();
				}catch(IndexOutOfBoundsException iobe) {
					mensajeNoRegistros();
				}catch(NumberFormatException nfe) {
					mensajeCampoCodigoVacio();
				}
			}
		});
		
		JButton btPrevious = new JButton("<");
		btPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					
					int posicion = getPosicion(Integer.parseInt(tfCodCochera.getText()));
					rellenarCampos(cocheras.get(posicion - 1));
					tVerTrenes.setRowSelectionInterval(posicion - 1, posicion - 1);
					session.close();
				}catch(IndexOutOfBoundsException iobe) {
					mensajeNoRegistros();
				}catch(NumberFormatException nfe) {
					mensajeCampoCodigoVacio();
				}
			}
		});
		
		JButton btFirst = new JButton("<<");
		btFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TCocheras firstAcceso = cocheras.get(0);
				rellenarCampos(firstAcceso);
				seleccionarFila(firstAcceso);
			}
		});
		
		JButton btLastt = new JButton(">>");
		btLastt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TCocheras lastAcceso = cocheras.get(cocheras.size() - 1);
				rellenarCampos(lastAcceso);
				seleccionarFila(lastAcceso);
			}
		});
		
		JLabel lblCodEstacin = new JLabel("DIRECCIÓN:");
		
		tfDireccion = new JTextField();
		tfDireccion.setColumns(10);
		
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
									.addComponent(tfNombre))
								.addGroup(gl_contentPane.createSequentialGroup()
									.addComponent(btFirst)
									.addGap(27)
									.addComponent(btPrevious)
									.addGap(102)
									.addComponent(lblCodCochera)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfCodCochera, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
							.addGap(61)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnLocalizar)
								.addComponent(lblCodEstacin))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addComponent(btNext)
									.addGap(34)
									.addComponent(btLastt))
								.addComponent(tfDireccion, GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))))
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
							.addComponent(lblCodCochera)
							.addComponent(tfCodCochera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLocalizar)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNombre)
							.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCodEstacin)
							.addComponent(tfDireccion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void rellenarCampos(TCocheras cochera) {
		try {
			
		tfCodCochera.setText(String.valueOf(cochera.getCodCochera()));
		tfNombre.setText(cochera.getNombre());
		tfDireccion.setText(String.valueOf(cochera.getDireccion()));
		
		}catch (ObjectNotFoundException onfe) {
			
		}
	}

	public int tripPosition(TCocheras cochera) {
		return cocheras.indexOf(cochera);
	}
	
	public void mensajeNoRegistros() {
		JOptionPane.showMessageDialog(null,"No hay más registros..");
	}

	public void mensajeCampoCodigoVacio() {
		JOptionPane.showMessageDialog(null,"No ha introducido ningun Código");
	}
	
	public void seleccionarFila(TCocheras cochera) {
		tVerTrenes.setRowSelectionInterval(tripPosition(cochera), tripPosition(cochera));
	}
	public int getPosicion(int cochera) {
		int posicion = 0;
		for(int i=0; i<cocheras.size(); i++) {
			if(cocheras.get(i).getCodCochera() == cochera) {
				posicion = i;
				return posicion;
			}
		}
		return posicion;
	}

}
