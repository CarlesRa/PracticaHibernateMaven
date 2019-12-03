package view;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import hibernateutil.HibernateUtil;
import model.TTrenes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class VentanaListarTrenes extends JFrame {

	private JPanel contentPane;
	private JTable tVerTrenes;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaListarTrenes frame = new VentanaListarTrenes();
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
	public VentanaListarTrenes() {
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 601, 440);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btFirst = new JButton("<<");
		
		JButton btLast = new JButton("<");
		
		JButton btLastTrain = new JButton(">>");
		
		JButton btNext = new JButton(">");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btFirst)
							.addGap(27)
							.addComponent(btLast)
							.addPreferredGap(ComponentPlacement.RELATED, 240, Short.MAX_VALUE)
							.addComponent(btNext)
							.addGap(34)
							.addComponent(btLastTrain)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 289, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btFirst)
						.addComponent(btLast)
						.addComponent(btLastTrain)
						.addComponent(btNext))
					.addContainerGap(56, Short.MAX_VALUE))
		);
		
		tVerTrenes = new JTable();
		DefaultTableModel dtm = new DefaultTableModel();
		dtm.setColumnIdentifiers(new Object[] {"CodTren","Nombre","Tipo","CodLinea","CodCochera"});
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tr = session.beginTransaction();
		@SuppressWarnings("unchecked")
		Query<TTrenes> query = session.createQuery("from TTrenes");
		ArrayList<TTrenes> trenes =(ArrayList<TTrenes>) query.list();
		for(TTrenes t : trenes) {
			Object[] row = {t.getCodTren(), t.getNombre(), t.getTipo(), t.getTLineas().getCodLinea(), t.getTCocheras().getCodCochera()};
			dtm.addRow(row);
		}
		
		tr.commit();
		
		tVerTrenes.setModel(dtm);
		scrollPane.setViewportView(tVerTrenes);
		contentPane.setLayout(gl_contentPane);
	}
	

}
