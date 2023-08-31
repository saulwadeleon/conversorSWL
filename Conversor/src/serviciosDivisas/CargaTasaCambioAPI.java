package serviciosDivisas;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import divisas.Divisas;
import divisas.TasaCambio;
import interfacesDivisas.CargaTasasCambio;

public class CargaTasaCambioAPI implements CargaTasasCambio {

	private static final String urlString = "http://currencies.apps.grandtrunk.net/getrate/";
	private static final String urlRangoString = "http://currencies.apps.grandtrunk.net/getrange/";

	private static final CargaTasaCambioAPI instancia = new CargaTasaCambioAPI();

	// Nuevo miembro de la clase: un mapa para almacenar los tipos de cambio que ya
	// hemos obtenido.
	private Map<String, TasaCambio> cache;

	public CargaTasaCambioAPI() {
		cache = new HashMap<>();
	}

	public static CargaTasaCambioAPI getInstancia() {
		return instancia;
	}

	// Cada uno de los métodos 'load' modificado para usar la caché antes de
	// solicitar a la API.

	@Override
	public TasaCambio load(Divisas deDivisa, Divisas aDivisa, Date fechaDesde, Date fechaHasta) {
		String clave = generaClave(deDivisa, aDivisa, fechaDesde, fechaHasta);

		if (!cache.containsKey(clave)) {
			cache.put(clave, cargarTasaCambio(deDivisa, aDivisa, fechaDesde, fechaHasta));
		}

		return cache.get(clave);
	}

	@Override
	public TasaCambio load(Divisas deDivisa, Divisas aDivisa, Date fecha) {
		String clave = generaClave(deDivisa, aDivisa, fecha);

		if (!cache.containsKey(clave)) {
			cache.put(clave, cargarTasaCambio(deDivisa, aDivisa, fecha));
		}

		return cache.get(clave);
	}

	@Override
	public TasaCambio load(Divisas deDivisa, Divisas aDivisa) {
		return load(deDivisa, aDivisa, new Date());
	}

	// Un método privado para generar una clave única para cada par de divisas y
	// fechas.
	private String generaClave(Divisas deDivisa, Divisas aDivisa, Date fechaDesde, Date fechaHasta) {
		return deDivisa.codigo() + aDivisa.codigo() + formatoFecha(fechaDesde) + formatoFecha(fechaHasta);
	}

	private String generaClave(Divisas deDivisa, Divisas aDivisa, Date fecha) {
		return generaClave(deDivisa, aDivisa, fecha, fecha);
	}

	// Métodos para cargar las tasas de cambio
	private TasaCambio cargarTasaCambio(Divisas deDivisa, Divisas aDivisa, Date fechaDesde, Date fechaHasta) {
		ValidadorHTTP validador = ValidadorHTTP.getInstancia();

		String respuesta = "";

		try {
			respuesta = validador.getRespuestaHTTP(generaURL(deDivisa, aDivisa, fechaDesde, fechaHasta));
		} catch (IOException ex) {
		}

		// System.out.println("Respuesta de la API: " + respuesta);
		double tipoCambio = Double.parseDouble(respuesta);
		return new TasaCambio(tipoCambio, deDivisa, aDivisa, fechaDesde, fechaHasta);
	}

	private TasaCambio cargarTasaCambio(Divisas deDivisa, Divisas aDivisa, Date fecha) {
		ValidadorHTTP validador = ValidadorHTTP.getInstancia();

		String respuesta = "";

		try {
			respuesta = validador.getRespuestaHTTP(generaURL(deDivisa, aDivisa, fecha));
		} catch (IOException ex) {
		}

		// System.out.println("Respuesta de la API: " + respuesta);
		double tipoCambio = Double.parseDouble(respuesta);
		return new TasaCambio(tipoCambio, deDivisa, aDivisa, fecha);
	}

	protected static URI generaURL(Divisas deDivisa, Divisas aDivisa, Date fechaDesde, Date fechaHasta)
			throws MalformedURLException {
		URI uri = URI.create(urlRangoString + formatoFecha(fechaDesde) + "/" + formatoFecha(fechaHasta) + "/"
				+ deDivisa.codigo() + "/" + aDivisa.codigo());

		return uri;
	}

	protected static URI generaURL(Divisas deDivisa, Divisas aDivisa, Date fecha) throws MalformedURLException {
		URI uri = URI.create(urlString + formatoFecha(fecha) + "/" + deDivisa.codigo() + "/" + aDivisa.codigo());

		return uri;
	}

	protected static String formatoFecha(Date fecha) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(fecha);

		int ano = calendar.get(Calendar.YEAR);
		int mes = calendar.get(Calendar.MONTH) + 1; // Sumar 1 al mes ya que en Calendar los meses van de 0 a 11.
		int dia = calendar.get(Calendar.DAY_OF_MONTH);

		return String.format("%04d-%02d-%02d", ano, mes, dia);
	}
}
