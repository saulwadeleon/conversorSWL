package divisas;

import java.awt.Component;
import java.awt.Image;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

/**
 * La clase BanderaRenderer que hereda de DefaultListCellRenderer. Esta clase se
 * utiliza para personalizar cómo se representan los elementos en una lista
 * JList.
 */
@SuppressWarnings("serial")
public class BanderaRenderer extends DefaultListCellRenderer {

	static final int ICON_SIZE = 32; // Tamaño deseado de los íconos

	/**
	 * El método getListCellRendererComponent() se utiliza cuando el objeto en la
	 * lista es una instancia de Divisas, muestra una bandera, el código de la
	 * divisa, el símbolo y el nombre de la divisa o moneda.
	 *
	 * Cuando se llama a getListCellRendererComponent(), primero es llamado el
	 * método padre, que devuelve un JLabel. Este JLabel se usa para mostrar la
	 * bandera de la divisa y la información de la divisa. Se ajusta el tamaño de la
	 * imagen de la bandera para que sea de 32x32 píxeles, y luego se establece como
	 * ícono del JLabel.
	 *
	 * Finalmente, se establece el texto del JLabel para mostrar el código de la
	 * divisa, el símbolo y el nombre de la moneda.
	 *
	 * Esto proporcionará una visualización más detallada y personalizada de los
	 * elementos de Divisas en una JList en comparación con la representación
	 * predeterminada.
	 */
	@Override
	public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
			boolean cellHasFocus) {
		// Creamos una etiqueta (JLabel) utilizando la implementación por defecto del
		// renderizador de celdas.
		// La etiqueta se utilizará para personalizar la apariencia de cada elemento en
		// la lista.
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

		// Verificamos si el valor del elemento (value) es una instancia de la clase
		// Divisas.
		if (value instanceof Divisas) {
			// Si el valor es una instancia de Divisas, procedemos a personalizar la
			// apariencia de la etiqueta (JLabel).

			// Obtenemos la instancia de Divisas a partir del valor del elemento.
			Divisas divisa = (Divisas) value;

			// Creamos un objeto ImageIcon a partir de la imagen de la bandera de la divisa
			// (divisa.bandera()).
			ImageIcon banderaIcon = new ImageIcon(divisa.bandera());

			// Obtenemos la imagen del ImageIcon.
			Image image = banderaIcon.getImage();

			// Redimensionamos la imagen para el tamaño específico definido (ICON_SIZE)
			// utilizando el método getScaledInstance.
			Image scaledImage = image.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);

			// Configuramos la etiqueta para mostrar el icono redimensionado como el icono
			// del elemento en la lista.
			label.setIcon(new ImageIcon(scaledImage));

			// Establecemos un texto que combina información sobre la divisa (código,
			// símbolo y nombre).
			label.setText(divisa.codigo() + " " + divisa.simbolo() + " - " + divisa.moneda());
		}

		// Devolvemos la etiqueta personalizada con la configuración adecuada para
		// representar el elemento en la lista.
		return label;
	}
}
