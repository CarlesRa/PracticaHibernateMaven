import javax.swing.WindowConstants;

import view.VentanaInsertarRegistros;

public class Main {
	public static void main(String[] args) {
		VentanaInsertarRegistros ventanaInRegistros = new VentanaInsertarRegistros();
		ventanaInRegistros.setVisible(true);
		ventanaInRegistros.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}
}
