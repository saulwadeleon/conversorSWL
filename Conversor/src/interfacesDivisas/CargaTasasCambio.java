package interfacesDivisas;

import java.text.ParseException;
import java.util.Date;

import divisas.Divisas;
import divisas.TasaCambio;

/**
 * Esta interfaz CargaTasasCambio define dos métodos load() para obtener la tasa
 * de cambio entre dos divisas. Ambos métodos toman dos objetos Divisas como
 * parámetros, que representan la divisa de origen y la divisa de destino.
 */
public interface CargaTasasCambio {

	/**
	 * El primer método load(Divisas deDivisas, Divisas aDivisas, Date fechaInicio,
	 * Date fechaFinal) también toma un rango de fechas como parámetro. Esto podría
	 * ser útil si la tasa de cambio entre las dos divisas varía con el tiempo y
	 * necesitas obtener la tasa de cambio específica para una fecha determinada.
	 *
	 * @param deDivisas
	 * @param aDivisas
	 * @param fechaInicio
	 * @param fechaFinal
	 * @return
	 * @throws ParseException
	 */
	public TasaCambio load(Divisas deDivisas, Divisas aDivisas, Date fechaInicio, Date fechaFinal)
			throws ParseException;

	/**
	 * El primer método load(Divisas deDivisas, Divisas aDivisas, Date fecha)
	 * también toma una fecha como parámetro. Esto podría ser útil si la tasa de
	 * cambio entre las dos divisas varía con el tiempo y necesitas obtener la tasa
	 * de cambio específica para una fecha determinada.
	 *
	 * @param deDivisas
	 * @param aDivisas
	 * @param fecha
	 * @return
	 */
	public TasaCambio load(Divisas deDivisas, Divisas aDivisas, Date fecha);

	/**
	 * El segundo método load(Divisas deDivisas, Divisas aDivisas) no toma una fecha
	 * como parámetro. Este método podría ser útil si simplemente necesitas obtener
	 * la tasa de cambio actual o más reciente entre las dos divisas.
	 *
	 * @param deDivisas
	 * @param aDivisas
	 * @return
	 */
	public TasaCambio load(Divisas deDivisas, Divisas aDivisas);

}