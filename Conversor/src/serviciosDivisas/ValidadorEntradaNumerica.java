package serviciosDivisas;

/**
 * La clase ValidadorEntradaNumerica es un validador simple que se utiliza para
 * verificar si una cadena de entrada puede ser parseada a un Double.
 */
public class ValidadorEntradaNumerica {

	public static boolean esEntradaNumerica(String entrada) {
		try {
			Double.parseDouble(entrada);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
