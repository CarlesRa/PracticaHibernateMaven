import javax.swing.JFrame;
import javax.swing.WindowConstants;

import view.VentanaInsertarRegistros;
import view.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		VentanaPrincipal vPrincipal = new VentanaPrincipal();
		vPrincipal.setTitle("Ventana Principal");
		vPrincipal.setVisible(true);
		vPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}
