package serviciosDivisas;

import java.util.Date;

import divisas.Divisas;
import divisas.TasaCambio;

/**
 * La clase TipoCambioService proporciona un método estático para obtener la
 * tasa de cambio entre dos divisas.
 */
public class TipoCambioService {

	/**
	 * Este método obtiene la tasa de cambio entre la divisa de origen
	 * (fromCurrency) y la divisa destino (toCurrency). Para hacerlo, se crea una
	 * nueva instancia de CargaTasaCambioAPI y llama a su método load() para obtener
	 * una instancia de TasaCambio que representa la tasa de cambio entre las dos
	 * divisas. Luego, devuelve la tasa de cambio obtenida llamando al método
	 * getTipoCambio() de la instancia TasaCambio.
	 *
	 * @param deDivisa
	 * @param aDivisa
	 * @return
	 */
	public static double obtenerTipoCambio(Divisas deDivisa, Divisas aDivisa, Date fecha) {
		CargaTasaCambioAPI cargaTasaCambioAPI = new CargaTasaCambioAPI();
		TasaCambio tasaCambio = cargaTasaCambioAPI.load(deDivisa, aDivisa, fecha);
		return tasaCambio.getTipoCambio();
	}

}
