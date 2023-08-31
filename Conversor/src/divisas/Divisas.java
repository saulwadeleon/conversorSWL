package divisas;

/**
 * Esta clase de tipo registro actúa principalmente como contenedor de datos.
 * Para este caso, el registro Divisas contiene cinco campos de datos: codigo,
 * pais, moneda, simbolo, y bandera.
 *
 * codigo: Código de la moneda (por ejemplo, USD para dólar estadounidense, EUR
 * para euro). pais: Nombre del país al que pertenece la moneda. moneda: Nombre
 * de la moneda. simbolo: Símbolo de la moneda (por ejemplo, $ para dólar, €
 * para euro). bandera: Ruta al archivo de la imagen que contiene la bandera del
 * país. Se usa para mostrar una imagen de la bandera junto a los detalles de la
 * moneda en la interfaz de usuario.
 *
 * Se ha redefinido el método toString() para devolver una cadena que contiene
 * todos los datos del registro.
 */
public record Divisas(String codigo, String pais, String moneda, String simbolo, String bandera) {

	@Override
	public String toString() {
		return codigo + " " + pais + " " + moneda + " " + simbolo + " " + bandera;
	}

}
