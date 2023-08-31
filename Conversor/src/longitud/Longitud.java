package longitud;

public class Longitud {

	private double valor;
	private String unidad;

	public Longitud(double valor, String unidad) {
		this.valor = valor;
		this.unidad = unidad;
	}

	public double getValor() {
		return valor;
	}

	public String getUnidad() {
		return unidad;
	}

	/**
	 * Convierte la longitud a la unidad especificada.
	 *
	 * @param unidadDestino La unidad de destino a la cual se debe convertir la
	 *                      longitud.
	 * @return El valor de la longitud en la unidad de destino.
	 * @throws UnidadDesconocidaException Si la unidad de destino no es reconocida.
	 *
	 */
	public double convertirA(String unidadDestino) throws UnidadDesconocidaException {
		double factorConversion = obtenerFactorConversion(unidad, unidadDestino);
		if (factorConversion == -1) {
			throw new UnidadDesconocidaException("Unidad de conversión desconocida: " + unidad + " a " + unidadDestino);
		}

		return valor * factorConversion;
	}

	/**
	 * Obtiene el factor de conversión entre unidades de longitud.
	 *
	 * @param unidadOrigen  La unidad de origen de la conversión.
	 * @param unidadDestino La unidad de destino de la conversión.
	 * @return El factor de conversión entre las unidades.
	 *
	 */
	public double obtenerFactorConversion(String unidadOrigen, String unidadDestino) {
		switch (unidadOrigen) {
		case "mm":
			return conversionMilimetros(unidadDestino);
		case "cm":
			return conversionCentimetros(unidadDestino);
		case "m":
			return conversionMetros(unidadDestino);
		case "km":
			return conversionKilometros(unidadDestino);
		case "in":
			return conversionPulgadas(unidadDestino);
		case "ft":
			return conversionPies(unidadDestino);
		case "yd":
			return conversionYardas(unidadDestino);
		case "mi":
			return conversionMillas(unidadDestino);
		case "NM":
			return conversionMillasNauticas(unidadDestino);
		default:
			return -1; // Unidad desconocida
		}
	}

	// Implementación de conversiones para logitudes
	private double conversionMilimetros(String unidadDestino) {
		switch (unidadDestino) {
		case "cm":
			return 0.1;
		case "m":
			return 0.001;
		case "km":
			return 1e-6;
		case "in":
			return 0.0393701;
		case "ft":
			return 0.00328084;
		case "yd":
			return 0.00109361;
		case "mi":
			return 6.2137e-7;
		case "NM":
			return 5.3996e-7;
		case "mm":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionCentimetros(String unidadDestino) {
		switch (unidadDestino) {
		case "mm":
			return 10;
		case "m":
			return 0.01;
		case "km":
			return 1e-5;
		case "in":
			return 0.393701;
		case "ft":
			return 0.0328084;
		case "yd":
			return 0.0109361;
		case "mi":
			return 6.2137e-6;
		case "NM":
			return 5.3996e-6;
		case "cm":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionMetros(String unidadDestino) {
		switch (unidadDestino) {
		// ... (conversiones para otras unidades)
		case "mm":
			return 1000;
		case "cm":
			return 100;
		case "km":
			return 0.001;
		case "in":
			return 39.3701;
		case "ft":
			return 3.28084;
		case "yd":
			return 1.09361;
		case "mi":
			return 0.000621371;
		case "NM":
			return 0.000539957;
		case "m":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionKilometros(String unidadDestino) {
		switch (unidadDestino) {
		// ... (conversiones para otras unidades)
		case "mm":
			return 1e+6;
		case "cm":
			return 1e+5;
		case "m":
			return 1000;
		case "in":
			return 39370.1;
		case "ft":
			return 3280.84;
		case "yd":
			return 1093.61;
		case "mi":
			return 0.621371;
		case "NM":
			return 0.539957;
		case "km":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionPulgadas(String unidadDestino) {
		switch (unidadDestino) {
		// ... (conversiones para otras unidades)
		case "mm":
			return 25.4;
		case "cm":
			return 2.54;
		case "m":
			return 0.0254;
		case "km":
			return 2.54e-5;
		case "ft":
			return 0.0833333;
		case "yd":
			return 0.0277778;
		case "mi":
			return 1.5783e-5;
		case "NM":
			return 1.3715e-5;
		case "in":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionPies(String unidadDestino) {
		switch (unidadDestino) {
		// ... (conversiones para otras unidades)
		case "mm":
			return 304.8;
		case "cm":
			return 30.48;
		case "m":
			return 0.3048;
		case "km":
			return 0.0003048;
		case "in":
			return 12;
		case "yd":
			return 0.333333;
		case "mi":
			return 0.000189394;
		case "NM":
			return 0.000164579;
		case "ft":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionYardas(String unidadDestino) {
		switch (unidadDestino) {
		// ... (conversiones para otras unidades)
		case "mm":
			return 914.4;
		case "cm":
			return 91.44;
		case "m":
			return 0.9144;
		case "km":
			return 0.0009144;
		case "in":
			return 36;
		case "ft":
			return 3;
		case "mi":
			return 0.000568182;
		case "NM":
			return 0.000493737;
		case "yd":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionMillas(String unidadDestino) {
		switch (unidadDestino) {
		// ... (conversiones para otras unidades)
		case "mm":
			return 1609344;
		case "cm":
			return 160934.4;
		case "m":
			return 1609.344;
		case "km":
			return 1.60934;
		case "in":
			return 63360;
		case "ft":
			return 5280;
		case "yd":
			return 1760;
		case "NM":
			return 0.868976;
		case "mi":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionMillasNauticas(String unidadDestino) {
		switch (unidadDestino) {
		// ... (conversiones para otras unidades)
		case "mm":
			return 1852000;
		case "cm":
			return 185200;
		case "m":
			return 1852;
		case "km":
			return 1.852;
		case "in":
			return 72913.4;
		case "ft":
			return 6076.12;
		case "yd":
			return 2025.37;
		case "mi":
			return 1.15078;
		case "NM":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	// Excepción personalizada para manejar unidades desconocidas
	@SuppressWarnings("serial")
	public class UnidadDesconocidaException extends Exception {
		public UnidadDesconocidaException(String mensaje) {
			super(mensaje);
		}
	}
}
