package divisas;

import java.awt.Insets;

import javax.swing.JTextField;

/**
 * Esta clase que extiende JTextField, permite que JTextFieldConMargen
 * sobrescriba el método getInsets() para agregar un margen izquierdo adicional
 * al JTextField.
 *
 * Los márgenes se definen mediante un objeto Insets, que especifica la cantidad
 * de espacio en la parte superior, inferior, izquierda y derecha.
 */
@SuppressWarnings("serial")
public class JTextFieldConMargen extends JTextField {
	private int margenIzquierdo;

	public JTextFieldConMargen(int margenIzquierdo) {
		this.margenIzquierdo = margenIzquierdo;
	}

	/**
	 * El método getInsets(), obtiene los márgenes originales del JTextField
	 * llamando al método super.getInsets(). Luego, crea un nuevo objeto Insets con
	 * los mismos márgenes superior, inferior y derecho que los originales, pero con
	 * un margen izquierdo que es la suma del margen izquierdo original y
	 * margenIzquierdo.
	 */
	@Override
	public Insets getInsets() {
		Insets originales = super.getInsets();
		return new Insets(originales.top, originales.left + margenIzquierdo, originales.bottom, originales.right);
	}
}
