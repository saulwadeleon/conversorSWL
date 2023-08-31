package superficie;

public class Superficie {
	private double valor;
	private String unidad;

	public Superficie(double valor, String unidad) {
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
	 * Convierte la superficie a la unidad especificada.
	 *
	 * @param unidadDestino La unidad de destino a la cual se debe convertir la
	 *                      superficie.
	 * @return El valor de la superficie en la unidad de destino.
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

	// Método privado para obtener el factor de conversión entre unidades
	private double obtenerFactorConversion(String unidadOrigen, String unidadDestino) {
		switch (unidadOrigen) {
		case "ac":
			return conversionAcres(unidadDestino);
		case "a":
			return conversionAreas(unidadDestino);
		case "ha":
			return conversionHectareas(unidadDestino);
		case "cm2":
			return conversionCentimetrosCuadrados(unidadDestino);
		case "ft2":
			return conversionPiesCuadrados(unidadDestino);
		case "in2":
			return conversionPulgadasCuadradas(unidadDestino);
		case "m2":
			return conversionMetrosCuadrados(unidadDestino);
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionAcres(String unidadDestino) {
		// Implementación de conversiones
		switch (unidadDestino) {
		case "m2":
			return 4046.86;
		case "a":
			return 40.4686;
		case "ha":
			return 0.404686;
		case "cm2":
			return 4.047e+7;
		case "ft2":
			return 43561.545456528;
		case "in2":
			return 6272862.5457400334999;
		case "ac":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionAreas(String unidadDestino) {
		// Implementación de conversiones
		switch (unidadDestino) {
		case "m2":
			return 100;
		case "ac":
			return 0.0247105;
		case "ha":
			return 0.009999984562570001;
		case "cm2":
			return 1000000;
		case "ft2":
			return 1076.39;
		case "in2":
			return 155000;
		case "a":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionHectareas(String unidadDestino) {
		// Implementación de conversiones
		switch (unidadDestino) {
		case "m2":
			return 10000;
		case "ac":
			return 2.47105;
		case "a":
			return 100;
		case "cm2":
			return 100000000;
		case "ft2":
			return 107638.938004612;
		case "in2":
			return 15500007.072664132342;
		case "ha":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionCentimetrosCuadrados(String unidadDestino) {
		// Implementación de conversiones
		switch (unidadDestino) {
		case "m2":
			return 0.0001;
		case "ac":
			return 2.4711e-8;
		case "a":
			return 1e-4;
		case "ha":
			return 1.00001869e-8;
		case "ft2":
			return 0.00107639;
		case "in2":
			return 0.155;
		case "cm2":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionPiesCuadrados(String unidadDestino) {
		// Implementación de conversiones
		switch (unidadDestino) {
		case "m2":
			return 0.092903;
		case "ac":
			return 2.2957e-5;
		case "a":
			return 0.09290304;
		case "ha":
			return 9.2903e-6;
		case "cm2":
			return 929.03;
		case "in2":
			return 144;
		case "ft2":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionPulgadasCuadradas(String unidadDestino) {
		// Implementación de conversiones
		switch (unidadDestino) {
		case "m2":
			return 0.00064516;
		case "ac":
			return 1.5942e-7;
		case "a":
			return 0.00064516;
		case "ha":
			return 6.4516e-8;
		case "cm2":
			return 6.4516;
		case "ft2":
			return 0.00694444;
		case "in2":
			return 1; // Conversión a la misma unidad
		default:
			return -1; // Unidad desconocida
		}
	}

	private double conversionMetrosCuadrados(String unidadDestino) {
		// Implementación de conversiones
		switch (unidadDestino) {
		case "ac":
			return 0.0002471054;
		case "a":
			return 0.01;
		case "ha":
			return 0.0001;
		case "cm2":
			return 10000;
		case "ft2":
			return 10.7639104;
		case "in2":
			return 1550.0031;
		case "m2":
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
