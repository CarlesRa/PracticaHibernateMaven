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
import model.TViajes;

public class VentanaListarViajes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tVerTrenes;
	private JTextField tfCodViaje;
	private JTextField tfNombre;
	private JTextField tfOrigen;
	private JTextField tfDestino;
	private ArrayList<TViajes> viajes;
	
	public VentanaListarViajes() {
		setBounds(100, 100, 896, 451);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCodTren = new JLabel("Cod. VIAJE:");
		
		tfCodViaje = new JTextField();
		tfCodViaje.setColumns(10);
		
		JLabel lblNombre = new JLabel("NOMBRE:");
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("ORIGEN:");
		
		tfOrigen = new JTextField();
		tfOrigen.setColumns(10);
		
		JLabel lblLinea = new JLabel("DESTINO:");
		
		tfDestino = new JTextField();
		tfDestino.setColumns(10);
		
		tVerTrenes = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"CodViaje", "Nombre","Origen","Destino"});
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TViajes> query = session.createQuery("from TViajes");
		viajes =(ArrayList<TViajes>) query.list();
		for(TViajes t : viajes) {
			Object[] row = {t.getCodViaje(), t.getNombre(), t.getEstacionorigen(), t.getEstaciondestino()};
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
					TViajes viaje = session.load(TViajes.class, Integer.parseInt(tfCodViaje.getText()));
					rellenarCampos(viaje);
					int posicion = getPosicion(Integer.parseInt(tfCodViaje.getText()));
					tVerTrenes.setRowSelectionInterval(posicion, posicion);
					session.close();
				}catch(ObjectNotFoundException onfe){
					JOptionPane.showMessageDialog(null,"Ningun Viaje con ese código");
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
					int posicion = getPosicion(Integer.parseInt(tfCodViaje.getText()));
					rellenarCampos(viajes.get(posicion + 1));
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
					int posicion = getPosicion(Integer.parseInt(tfCodViaje.getText()));
					rellenarCampos(viajes.get(posicion + 1));
					tVerTrenes.setRowSelectionInterval(posicion + 1, posicion + 1);
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
				TViajes firstTren = viajes.get(0);
				rellenarCampos(firstTren);
				seleccionarFila(firstTren);
			}
		});
		
		JButton btLastt = new JButton(">>");
		btLastt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TViajes lastTren = viajes.get(viajes.size()-1);
				rellenarCampos(lastTren);
				seleccionarFila(lastTren);
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
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_contentPane.createSequentialGroup()
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
									.addComponent(tfCodViaje, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_contentPane.createSequentialGroup()
									.addGap(48)
									.addComponent(btnLocalizar)
									.addPreferredGap(ComponentPlacement.RELATED, 211, Short.MAX_VALUE)
									.addComponent(btNext)
									.addGap(34)
									.addComponent(btLastt))
								.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
									.addGap(84)
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(tfOrigen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblLinea)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfDestino, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(btFirst)
							.addComponent(btPrevious)
							.addComponent(btLastt)
							.addComponent(btNext))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblCodTren)
							.addComponent(tfCodViaje, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLocalizar)))
					.addGap(50)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombre)
						.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfDestino, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLinea)
						.addComponent(tfOrigen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void rellenarCampos(TViajes viaje) {
		try {
			
		tfCodViaje.setText(String.valueOf(viaje.getCodViaje()));
		tfNombre.setText(viaje.getNombre());
		tfOrigen.setText(String.valueOf(viaje.getEstacionorigen()));
		tfDestino.setText(String.valueOf(viaje.getEstaciondestino()));
		
		}catch (ObjectNotFoundException onfe) {
			
		}
	}

	public int tripPosition(TViajes viaje) {
		return viajes.indexOf(viaje);
	}
	
	public void mensajeNoRegistros() {
		JOptionPane.showMessageDialog(null,"No hay más registros..");
	}

	public void mensajeCampoCodigoVacio() {
		JOptionPane.showMessageDialog(null,"No ha introducido ningun Código");
	}
	
	public void seleccionarFila(TViajes viajes) {
		tVerTrenes.setRowSelectionInterval(tripPosition(viajes), tripPosition(viajes));
	}

	public int getPosicion(int viaje) {
		int posicion = 0;
		for(int i=0; i<viajes.size(); i++) {
			if(viajes.get(i).getCodViaje() == viaje) {
				posicion = i;
				return posicion;
			}
		}
		return posicion;
	}
}
