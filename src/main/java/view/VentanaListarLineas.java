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
import model.TLineas;

public class VentanaListarLineas extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tVerTrenes;
	private JTextField tfCodLinea;
	private JTextField tfNombre;
	private ArrayList<TLineas> lineas;
	
	public VentanaListarLineas() {
		setBounds(100, 100, 896, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCodTren = new JLabel("Cod. LINEA:");
		
		tfCodLinea = new JTextField();
		tfCodLinea.setColumns(10);
		
		JLabel lblNombre = new JLabel("NOMBRE:");
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		
		tVerTrenes = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"Código Linea", "Nombre Linea"});
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TLineas> query = session.createQuery("from TLineas");
		lineas =(ArrayList<TLineas>) query.list();
		for(TLineas t : lineas) {
			Object[] row = {t.getCodLinea(), t.getNombre()};
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
					TLineas linea = session.load(TLineas.class, Integer.parseInt(tfCodLinea.getText()));
					rellenarCampos(linea);
					if (linea.getCodLinea() == Integer.parseInt(tfCodLinea.getText())) {
						int posicion = getPosicion(Integer.parseInt(tfCodLinea.getText()));
						tVerTrenes.setRowSelectionInterval(posicion, posicion);
					}
					session.close();
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
					
					int posicion = getPosicion(Integer.parseInt(tfCodLinea.getText()));
					rellenarCampos(lineas.get(posicion + 1));
					tVerTrenes.setRowSelectionInterval(posicion + 1, posicion + 1);
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
					int posicion = getPosicion(Integer.parseInt(tfCodLinea.getText()));
					rellenarCampos(lineas.get(posicion - 1));
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
				TLineas firstEstacion = lineas.get(0);
				rellenarCampos(firstEstacion);
				seleccionarFila(firstEstacion);
			}
		});
		
		JButton btLastt = new JButton(">>");
		btLastt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TLineas lastEstacion = lineas.get(lineas.size() - 1);
				rellenarCampos(lastEstacion);
				seleccionarFila(lastEstacion);
			}
		});
		
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
									.addComponent(lblCodTren)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfCodLinea, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
							.addGap(76)
							.addComponent(btnLocalizar)
							.addPreferredGap(ComponentPlacement.RELATED, 178, Short.MAX_VALUE)
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
							.addComponent(btNext))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCodTren)
							.addComponent(tfCodLinea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLocalizar)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombre)
						.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void rellenarCampos(TLineas estacion) {
		try {
			
		tfCodLinea.setText(String.valueOf(estacion.getCodLinea()));
		tfNombre.setText(estacion.getNombre());
		
		
		}catch (ObjectNotFoundException onfe) {
			
		}
	}

	public int tripPosition(TLineas linea) {
		return lineas.indexOf(linea);
	}
	
	public void mensajeNoRegistros() {
		JOptionPane.showMessageDialog(null,"No hay más registros..");
	}

	public void mensajeCampoCodigoVacio() {
		JOptionPane.showMessageDialog(null,"No ha introducido ningun Código");
	}
	
	public void seleccionarFila(TLineas linea) {
		tVerTrenes.setRowSelectionInterval(tripPosition(linea), tripPosition(linea));
	}
	public int getPosicion(int linea) {
		int posicion = 0;
		for(int i=0; i<lineas.size(); i++) {
			if(lineas.get(i).getCodLinea() == linea) {
				posicion = i;
				return posicion;
			}
		}
		return posicion;
	}
}
