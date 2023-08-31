package divisas;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * La clase SetDivisas implementa el patrón de diseño Singleton y se extiende
 * del HashSet. El uso de este patrón asegura que sólo habrá una única instancia
 * de SetDivisas en todo el programa, lo que es útil para mantener una única
 * lista global de divisas.
 */
@SuppressWarnings("serial")
public class SetDivisas extends HashSet<Divisas> {

	public static SetDivisas instanciaSetDivisas;

	private SetDivisas() {
		super();
	}

	/**
	 * Este método es la implementación clave del patrón Singleton. Si la instancia
	 * única no ha sido creada todavía, la crea. De lo contrario, devuelve la
	 * instancia ya existente
	 *
	 * @return
	 */
	public static SetDivisas getInstancia() {
		if (instanciaSetDivisas == null)
			instanciaSetDivisas = new SetDivisas();
		return instanciaSetDivisas;
	}

	/**
	 * Este método devuelve la divisa que tiene el código proporcionado. Si no se
	 * encuentra ninguna divisa con ese código, devuelve null.
	 *
	 * @param code
	 * @return
	 */
	public Divisas getDivisa(String code) {
		for (Divisas divisa : this)
			if (code.equals(divisa.codigo()))
				return divisa;
		return null;
	}

	/**
	 * Este método busca en el conjunto de divisas por una cadena que puede
	 * coincidir con el código, el país, la moneda o el símbolo de la divisa.
	 * Devuelve un array de todas las divisas que coinciden con el token.
	 *
	 * @param token
	 * @return
	 */
	public Divisas[] busqueda(String token) {
		ArrayList<Divisas> divisas = new ArrayList<>();
		for (Divisas divisa : this) {
			if (token.equalsIgnoreCase(divisa.codigo()))
				divisas.add(divisa);
			if (token.equalsIgnoreCase(divisa.pais()))
				divisas.add(divisa);
			if (divisa.moneda().toLowerCase().contains(token.toLowerCase()))
				divisas.add(divisa);
			if (token.equalsIgnoreCase(divisa.simbolo()))
				divisas.add(divisa);
		}
		return divisas.toArray(new Divisas[0]);
	}
}
