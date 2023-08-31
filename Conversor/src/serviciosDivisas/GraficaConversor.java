package serviciosDivisas;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
// import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import divisas.Divisas;

public class GraficaConversor {

	/**
	 * El método crea y muestra una gráfica utilizando los datos obtenidos del
	 * método createDataset
	 *
	 * @param deDivisa
	 * @param aDivisa
	 * @param fechaInicio
	 * @param fechaFin
	 * @return
	 */
	public static ChartPanel createAndShowChart(Divisas deDivisa, Divisas aDivisa, Date fechaInicio, Date fechaFin) {
		// System.out.println("Recibiendo Dataset para crear la gráfica...");

		// Crear el dataset utilizando los parámetros proporcionados
		XYDataset dataset = createDataset(deDivisa, aDivisa, fechaInicio, fechaFin);

		// Verificar si el dataset es nulo
		if (dataset == null) {
			return null;
		}

		// Imprimir el contenido del dataset en la consola
		// imprimeDataset(dataset);

		// Crear la gráfica utilizando el dataset y los parámetros de las divisas
		JFreeChart grafico = createChart(dataset, deDivisa, aDivisa);

		// Crear un ChartPanel con la gráfica y devolverlo
		return new ChartPanel(grafico);
	}

	/**
	 * Crea y devuelve un CategoryDataset que representa los datos de las tasas de
	 * cambio entre dos divisas en un período de tiempo determinado.
	 *
	 * @param deDivisa    La divisa de origen.
	 * @param aDivisa     La divisa de destino.
	 * @param fechaInicio La fecha de inicio del período de tiempo.
	 * @param fechaFin    La fecha de fin del período de tiempo.
	 * @return Un CategoryDataset con los datos de las tasas de cambio.
	 */
	public static XYDataset createDataset(Divisas deDivisa, Divisas aDivisa, Date fechaInicio, Date fechaFin) {
		XYSeries series = new XYSeries("Tasa de cambio");
		XYSeriesCollection dataset = new XYSeriesCollection();

		try {
			URI uri = CargaTasaCambioAPI.generaURL(deDivisa, aDivisa, fechaInicio, fechaFin);
			ValidadorHTTP validador = ValidadorHTTP.getInstancia();
			String respuestaURI = validador.getRespuestaHTTP(uri);
			// System.out.println("Linea recibida: " + respuestaURI);

			// Expresión regular para buscar una fecha en formato "yyyy-MM-dd" en la línea.
			String formatoCadena = "\\d{4}-\\d{2}-\\d{2}";

			// Buscamos todas las coincidencias de fechas en la respuesta.
			Pattern patronCandena = Pattern.compile(formatoCadena);
			Matcher matcher = patronCandena.matcher(respuestaURI);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// int contador = 0;

			while (matcher.find()) {
				int numInicio = matcher.start();
				int numFin = matcher.end();

				if (numFin + 10 <= respuestaURI.length()) { // Comprobar si hay suficientes caracteres para una tasa de
															// cambio.
					String stringFecha = respuestaURI.substring(numInicio, numFin).trim(); // Eliminar espacios en
																							// blanco
					String stringTasa = respuestaURI.substring(numFin, numFin + 6).trim(); // Eliminar espacios en
																							// blanco
					// Tomar los 6 caracteres siguientes como la tasa de ambio.

					// contador++;
					// System.out.println("Fecha: " + stringFecha + " Tasa:" + stringTasa + "
					// Contador: " + contador);
					// System.out.println("Fecha: " + stringFecha + " Tasa: " + stringTasa);
					// Contador: " + contador);
					// System.out.println(stringTasa);

					try {
						double tasaCambio = Double.parseDouble(stringTasa);

						// Redondear la tasa de cambio a dos decimales
						tasaCambio = Math.round(tasaCambio * 100.0) / 100.0;

						// Verificamos que la fecha tenga un formato válido (yyyy-MM-dd)
						if (isValidDateFormat(stringFecha)) {
							Date fecha = sdf.parse(stringFecha);
							long fechaTasa = fecha.getTime();
							series.add(fechaTasa, tasaCambio);
						}
					} catch (NumberFormatException | ParseException e) {
						// Si ocurre un error al convertir el valor a double, omitimos esta línea y
						// continuamos con la siguiente.
						continue;
					}
				}
			}

			// System.out.println("Dataset creado correctamente: " + series.getItemCount() +
			// " items");
			dataset.addSeries(series);
			return dataset;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * El método imprimeDataset se utiliza para imprimir los datos de un dataset en
	 * la consola en un formato específico, donde cada fila contiene la fecha y el
	 * valor asociado.
	 *
	 * Recibe un dataset del tipo `CategoryDataset`, que es una estructura que
	 * contiene información sobre las categorías (etiquetas) de los ejes X e Y, así
	 * como los valores numéricos asociados a cada categoría.
	 *
	 * El método recorre el dataset e imprime cada fila en el siguiente formato:
	 *
	 * [Fecha] / [Valor]
	 *
	 * Donde [Fecha] representa la etiqueta del eje X (en este caso, la fecha) y
	 * [Valor] representa el valor numérico asociado a esa fecha.
	 *
	 * Esto permite obtener una lista ordenada de las fechas y los valores para su
	 * visualización.
	 */
	/*
	 * public static void imprimeDataset(XYDataset dataset) { TimeSeriesCollection
	 * tsc = (TimeSeriesCollection) dataset;
	 * 
	 * // Obtener el número de series (en este caso, solo debería haber 1) int
	 * numSeries = tsc.getSeriesCount();
	 * 
	 * for (int i = 0; i < numSeries; i++) { TimeSeries series = tsc.getSeries(i);
	 * 
	 * // Obtener el número de items de la serie int numItems =
	 * series.getItemCount();
	 * 
	 * for (int j = 0; j < numItems; j++) { TimeSeriesDataItem item =
	 * series.getDataItem(j); // Obtener la etiqueta del eje X (fecha)
	 * RegularTimePeriod fecha = item.getPeriod(); // Obtener el valor numérico
	 * double valor = item.getValue().doubleValue();
	 * 
	 * // System.out.println(fecha + " " + valor); } } }
	 */

	/**
	 * Este método createChart se encarga de crear y configurar un gráfico de tipo
	 * XYLineChart (gráfico de líneas en coordenadas XY) utilizando la biblioteca
	 * JFreeChart. El gráfico mostrará tasas de cambio a lo largo del tiempo entre
	 * dos divisas específicas.
	 *
	 * @param dataset
	 * @param deDivisa
	 * @param aDivisa
	 * @return
	 */
	public static JFreeChart createChart(XYDataset dataset, Divisas deDivisa, Divisas aDivisa) {
		// Creación de la gráfica con la configuración deseada
		JFreeChart grafico = ChartFactory.createXYLineChart(
				"Tasas de cambio - año previo - " + aDivisa.codigo() + " por " + deDivisa.codigo(), "Fecha",
				"Tasa de cambio", dataset, PlotOrientation.VERTICAL, true, true, false);

		// Configuración de fuentes
		Font titleFont = new Font("Roboto", Font.BOLD, 14);
		Font tasaFont = new Font("Roboto", Font.BOLD, 12);
		Font ejeXFont = new Font("Roboto", Font.PLAIN, 9);
		Font ejeYFont = new Font("Roboto", Font.PLAIN, 10);

		// Configuración del título
		grafico.getTitle().setFont(titleFont);

		// Configuración del plot
		XYPlot plot = grafico.getXYPlot();

		// Configuración de la escala logarítmica en el eje Y
		NumberAxis ejeY = (NumberAxis) plot.getRangeAxis();
		ejeY.setStandardTickUnits(NumberAxis.createStandardTickUnits());
		ejeY.setTickUnit(new NumberTickUnit(3)); // Ajustar según sea necesario

		// Comprueba si el rango de los datos es 0
		double limiteInferior = ejeY.getLowerBound();
		double limiteSuperior = ejeY.getUpperBound();
		if (limiteInferior == limiteSuperior) {
			// Añade una pequeña variación al rango de los datos
			// El rango del eje Y, se ajusta ligeramente si los límites superior e inferior
			// son iguales para evitar divisiones por cero.
			limiteInferior = limiteInferior - 0.005; // disminuye el límite inferior
			limiteSuperior = limiteSuperior + 0.005; // aumenta el límite superior
		}
		ejeY.setRange(limiteInferior, limiteSuperior);

		// Resto de la configuración del eje Y y del eje X
		// ...
		ValueAxis ejeX = new DateAxis();
		ejeX.setLabelFont(tasaFont);
		plot.setDomainAxis(ejeX);
		ejeY = (NumberAxis) plot.getRangeAxis();
		ejeY.setLabelFont(tasaFont);
		ejeX.setTickLabelFont(ejeXFont);
		ejeY.setTickLabelFont(ejeYFont);

		// Configuración del renderizador
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, new Color(0, 120, 215));

		// Hacer invisibles las formas de los puntos en la gráfica
		renderer.setSeriesShapesVisible(0, false);
		plot.setRenderer(renderer);

		// Configuración del fondo
		grafico.setBackgroundPaint(new Color(255, 255, 255));
		plot.setBackgroundPaint(new Color(240, 240, 240));
		plot.setRangeGridlinePaint(new Color(200, 200, 200));

		return grafico;
	}

	/**
	 * El método esta diseñado para verificar si una cadena dada tiene un formato de
	 * fecha válido (en este caso, "yyyy-MM-dd") y si el año es mayor o igual a
	 * 1999. Si hay algún problema al analizar la fecha, como una excepción
	 * ParseException, se devuelve falso.
	 *
	 * @param cadena
	 * @return
	 */
	private static boolean isValidDateFormat(String cadena) {
		try {
			// Crea un objeto SimpleDateFormat con el formato "yyyy-MM-dd"
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			// Intenta analizar la cadena en una fecha
			Date fecha = sdf.parse(cadena);

			// Crea un objeto Calendar y establece la fecha analizada
			Calendar calendario = Calendar.getInstance();
			calendario.setTime(fecha);

			// Obtiene el año del calendario y compara con 1999
			int ano = calendario.get(Calendar.YEAR);

			// Devuelve true si el año es mayor o igual a 1999, de lo contrario, false
			return ano >= 1999;
		} catch (ParseException e) {
			// Si ocurre un error al analizar la fecha, devuelve false
			return false;
		}
	}

}