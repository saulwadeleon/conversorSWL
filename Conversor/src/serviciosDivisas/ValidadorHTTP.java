package serviciosDivisas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLConnection;

/**
 * La clase ValidadorHTTP permite realizar solicitudes HTTP y obtener respuestas
 * de un servidor.
 */
public class ValidadorHTTP {

	private static ValidadorHTTP instancia;

	private ValidadorHTTP() {
	}

	/**
	 * Este método devuelve la única instancia de ValidadorHTTP. Si la instancia no
	 * existe, la crea.
	 *
	 * @return
	 */
	public static ValidadorHTTP getInstancia() {
		if (instancia == null)
			instancia = new ValidadorHTTP();
		return instancia;
	}

	/**
	 * Este método toma un URI como entrada y abre una conexión a esa URL. Lee la
	 * respuesta del servidor y la devuelve como un String.
	 *
	 * @param uri
	 * @return
	 * @throws IOException
	 */
	public String getRespuestaHTTP(URI uri) throws IOException {
		URLConnection abrirConexion = uri.toURL().openConnection();
		BufferedReader entrada = new BufferedReader(new InputStreamReader(abrirConexion.getInputStream()));
		String respuesta = leeRespuesta(entrada);
		return respuesta;
	}

	/**
	 * Este es un método es utilizado para leer la respuesta de la conexión HTTP.
	 * Toma un BufferedReader como argumento, que se utiliza para leer la respuesta
	 * del servidor línea por línea y agregarla a un constructor String, para
	 * conviertir el String Builder a un String y devolverlo.
	 *
	 * @param in
	 * @return
	 * @throws IOException
	 */
	private String leeRespuesta(BufferedReader in) throws IOException {
		String lineaEntrada;
		StringBuilder respuestaLeida = new StringBuilder();

		while ((lineaEntrada = in.readLine()) != null)
			respuestaLeida.append(lineaEntrada);

		return respuestaLeida.toString();
	}
}
