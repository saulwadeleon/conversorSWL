package serviciosDivisas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import divisas.Divisas;
import divisas.SetDivisas;
import interfacesDivisas.CargaDivisas;

/**
 * La clase CargaArchivoDivisas carga una lista de divisas desde un archivo. El
 * archivo se especifica al construir un objeto CargaArchivoDivisas.
 */
public class CargaArchivoDivisas implements CargaDivisas {

	private final String archivo;

	public CargaArchivoDivisas(String file) {
		archivo = file;
	}

	/**
	 * El método load() lee el archivo especificado línea por línea. Cada línea debe
	 * tener una lista de propiedades de la divisa, separadas por comas. Estas
	 * propiedades se dividen y se utilizan para construir un objeto Divisas, que
	 * luego se agrega a un SetDivisas.
	 */
	@Override
	public void load() throws IOException {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(archivo)));
			while (true) {
				String linea = reader.readLine();
				if (linea == null)
					break;
				String[] divisa = linea.trim().split(",");
				SetDivisas.getInstancia().add(new Divisas(divisa[0], divisa[1], divisa[2], divisa[3],
						"imagenes/banderas/" + divisa[0] + ".png"));
				// SetDivisas es una forma de almacenamiento único que garantiza que no habrá
				// duplicados en la lista de divisas. La clase SetDivisas es una
				// implementación de singleton, dado que se accede a través del método
				// getInstancia(), lo que implica que hay una sola instancia de la clase en todo
				// el programa.
			}
		} catch (FileNotFoundException ex) {
			throw new RuntimeException(ex);
		}
	}

}
