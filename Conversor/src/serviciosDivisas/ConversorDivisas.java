package serviciosDivisas;

import java.util.Date;

import divisas.Divisas;

/**
 * La clase ConversorDivisas proporciona dos métodos estáticos para convertir
 * una cantidad de una divisa a otra.
 */
public class ConversorDivisas {

	static double ultimaTasa;

	/**
	 * Este método convierte una cantidad de la divisa origen (fromCurrency) a la
	 * divisa destino (toCurrency). Utiliza el método obtenerTipoCambio() del
	 * servicio TipoCambioService para obtener la tasa de cambio entre las dos
	 * divisas, luego multiplica la cantidad por esa tasa de cambio para obtener el
	 * valor convertido.
	 *
	 * @param cantidad
	 * @param deDivisa
	 * @param aDivisa
	 * @param date
	 * @return
	 */
	public static double convertirDivisas(double cantidad, Divisas deDivisa, Divisas aDivisa, Date fecha) {
		double tasaCambio = TipoCambioService.obtenerTipoCambio(deDivisa, aDivisa, fecha);
		ultimaTasa = tasaCambio;
		return cantidad * tasaCambio;
	}

	/**
	 * Este método es similar al anterior, pero hace la conversión en sentido
	 * inverso, es decir, de toCurrency a fromCurrency. Obtiene la tasa de cambio de
	 * toCurrency a fromCurrency y luego multiplica la cantidad por esa tasa para
	 * obtener el valor convertido.
	 *
	 * @param cantidad
	 * @param deDivisa
	 * @param aDivisa
	 * @return
	 */
	public static double convertirDivisasInverso(double cantidad, Divisas deDivisa, Divisas aDivisa, Date fecha) {
		double tasaCambio = TipoCambioService.obtenerTipoCambio(aDivisa, deDivisa, fecha);
		ultimaTasa = tasaCambio;
		return cantidad * tasaCambio;
	}

	public static double getVariacion(Divisas deDivisa, Divisas aDivisa, Date fecha) {
		if (ultimaTasa == 0) {
			return 0.0;
		}

		double oldRate = ultimaTasa;
		double newRate = TipoCambioService.obtenerTipoCambio(deDivisa, aDivisa, fecha);

		return (newRate - oldRate) / oldRate;
	}
}
