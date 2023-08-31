package divisas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JComboBox;

public class OperacionComboBox {

	/**
	 * Este método se utiliza para llenar un JComboBox con los objetos Divisas
	 * almacenados en la instancia de SetDivisas. Primero, crea una lista a partir
	 * del conjunto de Divisas. Luego ordena esta lista alfabéticamente en función
	 * de su código de divisa. Finalmente, limpia el JComboBox y añade las divisas
	 * ordenadas a él.
	 *
	 * @param comboBox
	 */
	public void llenarComboBox(JComboBox<Divisas> comboBox) {
		// Copiar el contenido de SetDivisas a una lista para poder ordenarla
		List<Divisas> divisasList = new ArrayList<>(SetDivisas.getInstancia());

		// Ordenar la lista de divisas alfabéticamente por su código
		Collections.sort(divisasList, (divisa1, divisa2) -> divisa1.codigo().compareTo(divisa2.codigo()));

		// Limpiar el combo box antes de agregar los elementos
		comboBox.removeAllItems();

		// Agregar los elementos ordenados al combo box
		for (Divisas currency : divisasList) {
			comboBox.addItem(currency);
		}
	}

	/**
	 * Este método se utiliza para buscar una divisa en la instancia SetDivisas
	 * mediante el código de la divisa. Recorre el conjunto de divisas y devuelve la
	 * primera divisa que coincide con el código proporcionado. Si no encuentra
	 * ninguna divisa con el código dado, se devuelve null.
	 *
	 * @param codigo
	 * @return
	 */
	public static Divisas buscarDivisaPorCodigo(String codigo) {
		for (Divisas divisa : SetDivisas.getInstancia()) {
			if (divisa.codigo().equals(codigo)) {
				return divisa;
			}
		}
		return null; // Devuelve null si no se encuentra la divisa con el código dado
	}

}
