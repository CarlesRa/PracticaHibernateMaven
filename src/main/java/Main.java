import javax.swing.JFrame;
import view.NewVentanaPrincipal;


public class Main {
	public static void main(String[] args) {
		NewVentanaPrincipal vPrincipal = new NewVentanaPrincipal();
		vPrincipal.setTitle("Ventana Principal");
		vPrincipal.setVisible(true);
		vPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
