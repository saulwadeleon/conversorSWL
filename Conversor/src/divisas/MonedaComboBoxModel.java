package divisas;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

/**
 * Esta es un divisas de datos personalizado para el componente JComboBox que
 * maneja objetos de la clase Divisas. Esta clase extiende AbstractListModel, lo
 * que significa que puede ser usada como una fuente de datos para componentes
 * de listas, como JComboBox, en Swing.
 *
 * La clase mantiene una lista de objetos Divisas y define métodos para obtener
 * el tamaño de la lista y para obtener un elemento en un índice particular.
 */
@SuppressWarnings("serial")
public class MonedaComboBoxModel extends AbstractListModel<Divisas> {

	private final ArrayList<Divisas> divisasList;

	public MonedaComboBoxModel(ArrayList<Divisas> divisasList) {
		this.divisasList = divisasList;
	}

	/**
	 * Este método se sobrescribe de AbstractListModel y retorna el tamaño de la
	 * lista de divisas.
	 */
	@Override
	public int getSize() {
		return divisasList.size();
	}

	/**
	 * Este método también se sobrescribe de AbstractListModel y retorna el elemento
	 * en el índice especificado en la lista de divisas.
	 */
	@Override
	public Divisas getElementAt(int index) {
		return divisasList.get(index);
	}

	/**
	 * Este primero limpia la lista de divisas existente, luego agrega todas las
	 * nuevas divisas a la lista. Después, llama a fireContentsChanged, que notifica
	 * a cualquier EventListener que los elementos en el rango especificado han
	 * cambiado, con la finalidad de informar a la interfaz de usuario que debe
	 * actualizar cualquier vista que esté mostrando estos datos, ya que estos
	 * pueden haber cambiado.
	 *
	 * @param nuevasDivisas
	 */
	public void actualizarDivisas(ArrayList<Divisas> nuevasDivisas) {
		divisasList.clear();
		divisasList.addAll(nuevasDivisas);
		fireContentsChanged(this, 0, nuevasDivisas.size() - 1);
	}
}
