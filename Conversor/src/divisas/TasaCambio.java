package divisas;

import java.util.Date;

/**
 * La clase TasaCambio es un contenedor de datos que se usa para representar una
 * tasa de cambio de divisas. Esta clase proporciona una manera de almacenar y
 * recuperar información sobre una tasa de cambio de divisas específica en una
 * fecha determinada.
 *
 * La clase también proporciona métodos getter para cada uno de estos campos, lo
 * que permite que los valores se recuperen pero no se modifiquen después de que
 * se haya construido el objeto TasaCambio.
 */
public class TasaCambio {
	private double tipoCambio;
	private Divisas fromCurrency;
	private Divisas toCurrency;
	private Date fecha;
	@SuppressWarnings("unused")
	private Date fechaInicio;

	public TasaCambio(double tipoCambio, Divisas deDivisa, Divisas aDivisa, Date date) {
		this.tipoCambio = tipoCambio;
		this.fromCurrency = deDivisa;
		this.toCurrency = aDivisa;
		this.fecha = date;
	}

	public TasaCambio(double tipoCambio, Divisas deDivisa, Divisas aDivisa, Date fechaInicio, Date fechaFin) {
		this.tipoCambio = tipoCambio;
		this.fromCurrency = deDivisa;
		this.toCurrency = aDivisa;
		this.fecha = fechaFin;
		this.fechaInicio = fechaInicio;
	}

	public double getTipoCambio() {
		return tipoCambio;
	}

	public Divisas getFromCurrency() {
		return fromCurrency;
	}

	public Divisas getToCurrency() {
		return toCurrency;
	}

	public Date getDate() {
		return fecha;
	}
}
