package view;

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
import model.TTrenes;

public class NewVentanaListarTrenes extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable tVerTrenes;
	private JTextField tfCodTren;
	private JTextField tfNombre;
	private JTextField tfTipo;
	private JTextField tfLinea;
	private JTextField tfCochera;
	private ArrayList<TTrenes> trenes;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewVentanaListarTrenes frame = new NewVentanaListarTrenes();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	public NewVentanaListarTrenes() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 896, 598);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JLabel lblCodTren = new JLabel("Cod. TREN:");
		
		tfCodTren = new JTextField();
		tfCodTren.setColumns(10);
		
		JLabel lblNombre = new JLabel("NOMBRE:");
		
		tfNombre = new JTextField();
		tfNombre.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("TIPO:");
		
		tfTipo = new JTextField();
		tfTipo.setColumns(10);
		
		JLabel lblLinea = new JLabel("LINEA:");
		
		tfLinea = new JTextField();
		tfLinea.setColumns(10);
		
		JLabel lblCochera = new JLabel("COCHERA:");
		
		tfCochera = new JTextField();
		tfCochera.setColumns(10);
		
		tVerTrenes = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"CodTren","Nombre","Tipo","CodLinea","CodCochera"});
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TTrenes> query = session.createQuery("from TTrenes");
		trenes =(ArrayList<TTrenes>) query.list();
		for(TTrenes t : trenes) {
			Object[] row = {t.getCodTren(), t.getNombre(), t.getTipo(), t.getTLineas().getCodLinea()
					, t.getTCocheras().getCodCochera()};
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
				TTrenes tren = session.load(TTrenes.class, Integer.parseInt(tfCodTren.getText()));
				
				try {
					rellenarCampos(tren);
					tVerTrenes.setRowSelectionInterval(tren.getCodTren()-1, tren.getCodTren()-1);
				}catch(ObjectNotFoundException onfe){
					JOptionPane.showMessageDialog(null,"Ningun tren con ese código");
				}
			}
		});
		
		JButton btNext = new JButton(">");
		btNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					tfCodTren.setText(String.valueOf((Integer.parseInt(tfCodTren.getText()) + 1)));
					System.out.println("Previus" + tfCodTren.getText());
					TTrenes tren = session.load(TTrenes.class, Integer.parseInt(tfCodTren.getText()));
					rellenarCampos(tren);
					tVerTrenes.setRowSelectionInterval(tren.getCodTren() - 1, tren.getCodTren() - 1);
				}catch(ObjectNotFoundException infe) {
					JOptionPane.showMessageDialog(null,"No hay más registros");
					tfCodTren.setText(String.valueOf((Integer.parseInt(tfCodTren.getText()) - 1)));
				}catch(HibernateException he) {
					JOptionPane.showMessageDialog(null,"No hay más registros");
					tfCodTren.setText(String.valueOf((Integer.parseInt(tfCodTren.getText()) - 1)));
				}catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null,"No ha introducido ningun Código de tren");
				}
				
			}
		});
		
		JButton btPrevious = new JButton("<");
		btPrevious.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				SessionFactory sessionF = HibernateUtil.getSessionFactory();
				Session session = sessionF.openSession();
				try {
					
					tfCodTren.setText(String.valueOf((Integer.parseInt(tfCodTren.getText()) - 1)));
					TTrenes tren = session.load(TTrenes.class, Integer.parseInt(tfCodTren.getText()));
					rellenarCampos(tren);
					tVerTrenes.setRowSelectionInterval(tren.getCodTren(), tren.getCodTren());
				}catch(ObjectNotFoundException infe) {
					JOptionPane.showMessageDialog(null,"No hay más registros");
					tfCodTren.setText(String.valueOf((Integer.parseInt(tfCodTren.getText()) + 1)));
				}catch(NumberFormatException nfe) {
					JOptionPane.showMessageDialog(null,"No ha introducido ningun Código de tren");
				}catch(HibernateException he) {
					JOptionPane.showMessageDialog(null,"No hay más registros");
					tfCodTren.setText(String.valueOf((Integer.parseInt(tfCodTren.getText()) + 1)));
				}
			}
		});
		
		JButton btFirst = new JButton("<<");
		btFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TTrenes firstTren = trenes.get(0);
				rellenarCampos(firstTren);
				seleccionarFila(firstTren);
			}
		});
		
		JButton btLastt = new JButton(">>");
		btLastt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TTrenes lastTren = trenes.get(trenes.size()-1);
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
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btFirst)
							.addGap(27)
							.addComponent(btPrevious)
							.addGap(102)
							.addComponent(lblCodTren)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfCodTren, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addGap(48)
							.addComponent(btnLocalizar)
							.addPreferredGap(ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
							.addComponent(btNext)
							.addGap(34)
							.addComponent(btLastt))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNombre)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblLinea)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfLinea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblCochera)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfCochera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btFirst)
								.addComponent(btPrevious)
								.addComponent(btLastt)
								.addComponent(btNext)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCodTren)
								.addComponent(tfCodTren, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnLocalizar))))
					.addGap(50)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNombre)
						.addComponent(tfNombre, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(tfTipo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLinea)
						.addComponent(tfLinea, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCochera)
						.addComponent(tfCochera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(149, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
		
	}
	
	public void rellenarCampos(TTrenes tren) {
		try {
		tfCodTren.setText(String.valueOf(tren.getCodTren()));
		tfNombre.setText(tren.getNombre());
		tfTipo.setText(tren.getTipo());
		tfLinea.setText(String.valueOf(tren.getTLineas().getCodLinea()));
		tfCochera.setText(String.valueOf(tren.getTCocheras().getCodCochera()));
		
		}catch (ObjectNotFoundException onfe) {
			JOptionPane.showMessageDialog(null,"Ningun tren con ese código...");
		}
	}

	public int trainPosition(TTrenes tren) {
		return trenes.indexOf(tren);
	}
	
	public void mensajeNoRegistros() {
		JOptionPane.showMessageDialog(null,"No hay más registros..");
	}

	public void mensajeCampoCodigoVacio() {
		JOptionPane.showMessageDialog(null,"No ha introducido ningun Código de tren");
	}
	
	public void seleccionarFila(TTrenes tren) {
		tVerTrenes.setRowSelectionInterval(trainPosition(tren), trainPosition(tren));
	}
}
