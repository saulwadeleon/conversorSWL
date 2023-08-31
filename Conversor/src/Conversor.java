import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

import com.formdev.flatlaf.FlatLightLaf;
import com.toedter.calendar.JCalendar;

import divisas.BanderaRenderer;
import divisas.Divisas;
import divisas.JTextFieldConMargen;
import divisas.OperacionComboBox;
import divisas.SetDivisas;
import divisas.TasaCambio;
import interfacesDivisas.CargaDivisas;
import longitud.Longitud;
import longitud.Longitud.UnidadDesconocidaException;
import serviciosDivisas.CargaArchivoDivisas;
import serviciosDivisas.CargaTasaCambioAPI;
import serviciosDivisas.ConversorDivisas;
import serviciosDivisas.GraficaConversor;
import serviciosDivisas.ValidadorEntradaNumerica;
import superficie.Superficie;

@SuppressWarnings("serial")
public class Conversor extends JFrame {

	private JPanel panelContenido;
	boolean btnClicked = false;

	static JLabel lblTasaCambioDia = new JLabel("Conversor de divisas");
	static JLabel lblUltimaActualizacion = new JLabel("Conversor de divisas");
	static JLabel lblVariacion = new JLabel("Conversor de divisas");
	static JLabel lblResultadoS;
	static JLabel lblResultadoDis;

	// Crear instancias de los JComboBox
	static JComboBox<Divisas> comboBox_De = new JComboBox<>();
	static JComboBox<Divisas> comboBox_A = new JComboBox<>();

	private static JTextField textField_De;
	private static JTextField textField_A;
	private static JTextField textField_Sde;
	private static JTextField textField_Sa;
	private static JTextField textField_DisDe;
	private static JTextField textField_Disa;

	JLabel lblViceversa = new JLabel("");
	static JCalendar calendario = new JCalendar();

	// Crea una instancia de CargaTasaCambioAPI
	static CargaTasaCambioAPI instanciaAPI = new CargaTasaCambioAPI();

	static String[] unidadesA = { "Acres (ac)", "Areas (a)", "Hectareas (ha)", "Centimetros cuadrados (cm2)",
			"Pies cuadrados (ft2)", "Pulgadas cuadradas (in2)", "Metros cuadrados (m2)" };
	static String[] unidadesB = { "Milímetros (mm)", "Centimetros (cm)", "Metros (m)", "Kilómetros (km)",
			"Pulgadas (in)", "Pies (ft)", "Yardas (yd)", "Millas (mi)", "Millas náuticas (NM)" };

	// Crear instancias de los JComboBox para Superficies y Longitudes
	static JComboBox<String> comboBox_Sde = new JComboBox<>(unidadesA);
	static JComboBox<String> comboBox_Sa = new JComboBox<>(unidadesA);
	static JComboBox<String> comboBox_Disde = new JComboBox<>(unidadesB);
	static JComboBox<String> comboBox_Disa = new JComboBox<>(unidadesB);

	static String supOrigen;
	static String supDestino;
	static String lonOrigen;
	static String lonDestino;

	/**
	 * Create the application.
	 */
	public Conversor() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("imagenes/menus/icono.png"));
		setTitle("Conversor by Saul Wade");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {

		setResizable(false);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 900, 640);
		panelContenido = new JPanel();
		panelContenido.setBackground(new Color(17, 19, 22));
		panelContenido.setBorder(null);
		setContentPane(panelContenido);
		panelContenido.setLayout(null);

		JPanel menu = new JPanel();
		menu.setBorder(null);
		menu.setBackground(new Color(33, 36, 38));
		menu.setForeground(Color.BLACK);
		menu.setBounds(0, 0, 120, 600);
		panelContenido.add(menu);
		menu.setLayout(null);

		JLabel btnMenuDivisas = new JLabel("");
		btnMenuDivisas.setIcon(new ImageIcon("imagenes/menus/btnMenuDivisas.png"));
		btnMenuDivisas.setHorizontalAlignment(SwingConstants.CENTER);
		btnMenuDivisas.setToolTipText("Conversor de Divisas");
		btnMenuDivisas.setForeground(new Color(255, 255, 255));
		btnMenuDivisas.setBounds(0, 0, 120, 120);
		menu.add(btnMenuDivisas);

		JLabel btnSuperficie = new JLabel("");
		btnSuperficie.setIcon(new ImageIcon("imagenes/menus/btnSuperficie.png"));
		btnSuperficie.setHorizontalAlignment(SwingConstants.CENTER);
		btnSuperficie.setToolTipText("Conversor de Areas");
		btnSuperficie.setForeground(new Color(255, 255, 255));
		btnSuperficie.setBounds(0, 0, 120, 120);
		btnSuperficie.setBounds(0, 120, 120, 120);
		menu.add(btnSuperficie);

		JLabel btnDistancias = new JLabel("");
		btnDistancias.setIcon(new ImageIcon("imagenes/menus/btnDistancias.png"));
		btnDistancias.setToolTipText("Conversor de Distancias");
		btnDistancias.setHorizontalAlignment(SwingConstants.CENTER);
		btnDistancias.setForeground(new Color(255, 255, 255));
		btnDistancias.setBounds(0, 240, 120, 120);
		menu.add(btnDistancias);

		JPanel panelDivisas = new JPanel();
		panelDivisas.setBackground(new Color(17, 19, 22));
		panelDivisas.setBorder(null);
		panelDivisas.setBounds(120, 0, 764, 600);
		panelContenido.add(panelDivisas);
		panelDivisas.setVisible(false);
		panelDivisas.setLayout(null);

		JLabel lblTitulo = new JLabel("Conversor de divisas");
		lblTitulo.setBounds(10, 15, 300, 25);
		lblTitulo.setForeground(new Color(255, 255, 255));
		lblTitulo.setFont(new Font("Roboto", Font.BOLD, 18));
		panelDivisas.add(lblTitulo);

		lblTasaCambioDia.setBounds(10, 45, 600, 50);
		lblTasaCambioDia.setBackground(new Color(200, 200, 200));
		lblTasaCambioDia.setForeground(new Color(200, 200, 200));
		lblTasaCambioDia.setFont(new Font("Roboto", Font.BOLD, 34));
		panelDivisas.add(lblTasaCambioDia);

		lblUltimaActualizacion.setBounds(30, 85, 400, 25);
		lblUltimaActualizacion.setForeground(new Color(200, 200, 200));
		lblUltimaActualizacion.setFont(new Font("Roboto", Font.PLAIN, 12));
		panelDivisas.add(lblUltimaActualizacion);

		lblVariacion.setBounds(30, 110, 300, 25);
		lblVariacion.setForeground(Color.RED);
		lblVariacion.setFont(new Font("Roboto Medium", Font.PLAIN, 16));
		panelDivisas.add(lblVariacion);

		JPanel panelCambio = new JPanel();
		panelCambio.setBounds(30, 145, 705, 165);
		panelCambio.setBackground(new Color(33, 36, 38));
		panelDivisas.add(panelCambio);
		panelCambio.setLayout(null);

		JLabel lblNewLabel = new JLabel("De");
		lblNewLabel.setForeground(new Color(255, 255, 255));
		lblNewLabel.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblNewLabel.setBounds(10, 11, 46, 15);
		panelCambio.add(lblNewLabel);

		comboBox_De.setForeground(new Color(255, 255, 255));
		comboBox_De.setFont(new Font("Roboto Medium", Font.PLAIN, 18));
		comboBox_De.setBackground(new Color(67, 80, 98));
		comboBox_De.setMaximumRowCount(12);
		comboBox_De.setBounds(10, 35, 300, 50);
		comboBox_De.setBorder(null);
		panelCambio.add(comboBox_De);
		// comboBox_De.setEditable(true);

		JLabel lblNewLabel_1 = new JLabel("A");
		lblNewLabel_1.setForeground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(391, 11, 46, 15);
		panelCambio.add(lblNewLabel_1);

		comboBox_A.setForeground(new Color(255, 255, 255));
		comboBox_A.setFont(new Font("Roboto Medium", Font.PLAIN, 18));
		comboBox_A.setBackground(new Color(67, 80, 98));
		comboBox_A.setMaximumRowCount(12);
		comboBox_A.setBounds(390, 35, 300, 50);
		comboBox_A.setBorder(null);
		panelCambio.add(comboBox_A);
		// comboBox_A.setEditable(true);

		textField_De = new JTextFieldConMargen(10);
		textField_De.setText("1");
		textField_De.setForeground(new Color(255, 255, 255));
		textField_De.setFont(new Font("Roboto", Font.BOLD, 26));
		textField_De.setBackground(new Color(75, 75, 75));
		textField_De.setBounds(10, 85, 300, 60);
		textField_De.setBorder(null);
		textField_De.setColumns(10);
		panelCambio.add(textField_De);

		lblViceversa.setHorizontalAlignment(SwingConstants.CENTER);
		lblViceversa.setIcon(new ImageIcon("imagenes/menus/arrows.png"));
		lblViceversa.setBounds(325, 60, 50, 50);
		panelCambio.add(lblViceversa);

		textField_A = new JTextFieldConMargen(10);
		textField_A.setForeground(new Color(255, 255, 255));
		textField_A.setFont(new Font("Roboto", Font.BOLD, 26));
		textField_A.setColumns(10);
		textField_A.setBorder(null);
		textField_A.setBackground(new Color(75, 75, 75));
		textField_A.setBounds(390, 85, 300, 60);
		panelCambio.add(textField_A);

		JPanel panelCalendario = new JPanel();
		panelCalendario.setBounds(30, 320, 705, 270);
		panelCalendario.setBorder(null);
		panelCalendario.setBackground(new Color(33, 36, 38));
		panelDivisas.add(panelCalendario);

		Locale.Builder localeBuilder = new Locale.Builder();
		localeBuilder.setLanguage("es");
		localeBuilder.setRegion("MX");
		Locale locacionMexico = localeBuilder.build();
		panelCalendario.setLayout(new GridLayout(0, 2, 0, 0));

		calendario.getDayChooser().getDayPanel().setForeground(new Color(75, 75, 75));
		calendario.setBackground(new Color(75, 75, 75));
		calendario.setBorder(null);
		calendario.setLocale(locacionMexico);
		panelCalendario.add(calendario);

		JPanel panelSuperficie = new JPanel();
		panelSuperficie.setBorder(null);
		panelSuperficie.setBackground(new Color(17, 19, 22));
		panelSuperficie.setBounds(120, 0, 764, 600);
		panelContenido.add(panelSuperficie);
		panelSuperficie.setVisible(false);
		panelSuperficie.setLayout(null);

		JLabel lblTituloS = new JLabel("Conversor de superficies");
		lblTituloS.setForeground(Color.WHITE);
		lblTituloS.setFont(new Font("Roboto", Font.BOLD, 18));
		lblTituloS.setBounds(10, 15, 300, 25);
		panelSuperficie.add(lblTituloS);

		JPanel panelCambioS = new JPanel();
		panelCambioS.setLayout(null);
		panelCambioS.setBackground(new Color(33, 36, 38));
		panelCambioS.setBounds(30, 100, 705, 165);
		panelSuperficie.add(panelCambioS);

		JLabel lblSupDe = new JLabel("De");
		lblSupDe.setForeground(Color.WHITE);
		lblSupDe.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblSupDe.setBounds(10, 11, 46, 15);
		panelCambioS.add(lblSupDe);

		comboBox_Sde.setSelectedItem("Hectareas (ha)");
		comboBox_Sde.setMaximumRowCount(12);
		comboBox_Sde.setForeground(Color.WHITE);
		comboBox_Sde.setFont(new Font("Roboto Medium", Font.PLAIN, 18));
		comboBox_Sde.setBorder(null);
		comboBox_Sde.setBackground(new Color(67, 80, 98));
		comboBox_Sde.setBounds(10, 35, 300, 50);
		panelCambioS.add(comboBox_Sde);

		JLabel lblSupA = new JLabel("A");
		lblSupA.setForeground(Color.WHITE);
		lblSupA.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblSupA.setBounds(391, 11, 46, 15);
		panelCambioS.add(lblSupA);

		comboBox_Sa.setSelectedItem("Metros cuadrados (m2)");
		comboBox_Sa.setMaximumRowCount(12);
		comboBox_Sa.setForeground(Color.WHITE);
		comboBox_Sa.setFont(new Font("Roboto Medium", Font.PLAIN, 18));
		comboBox_Sa.setBorder(null);
		comboBox_Sa.setBackground(new Color(67, 80, 98));
		comboBox_Sa.setBounds(390, 35, 300, 50);
		panelCambioS.add(comboBox_Sa);

		textField_Sde = new JTextFieldConMargen(10);
		textField_Sde.setText("1");
		textField_Sde.setForeground(Color.WHITE);
		textField_Sde.setFont(new Font("Roboto", Font.BOLD, 26));
		textField_Sde.setColumns(10);
		textField_Sde.setBorder(null);
		textField_Sde.setBackground(new Color(75, 75, 75));
		textField_Sde.setBounds(10, 85, 300, 60);
		panelCambioS.add(textField_Sde);

		JLabel lblViceversa_1 = new JLabel("");
		lblViceversa_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblViceversa_1.setBounds(325, 60, 50, 50);
		panelCambioS.add(lblViceversa_1);

		textField_Sa = new JTextFieldConMargen(10);
		textField_Sa.setForeground(Color.WHITE);
		textField_Sa.setFont(new Font("Roboto", Font.BOLD, 26));
		textField_Sa.setColumns(10);
		textField_Sa.setBorder(null);
		textField_Sa.setBackground(new Color(75, 75, 75));
		textField_Sa.setBounds(390, 85, 300, 60);
		panelCambioS.add(textField_Sa);

		JPanel panelResultadoS = new JPanel();
		panelResultadoS.setLayout(null);
		panelResultadoS.setBackground(new Color(33, 36, 38));
		panelResultadoS.setBounds(30, 300, 705, 165);
		panelSuperficie.add(panelResultadoS);

		lblResultadoS = new JLabel("");
		lblResultadoS.setFont(new Font("Roboto", Font.BOLD, 20));
		lblResultadoS.setBackground(new Color(200, 200, 200));
		lblResultadoS.setForeground(new Color(200, 200, 200));
		lblResultadoS.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoS.setBounds(50, 40, 605, 100);
		panelResultadoS.add(lblResultadoS);

		JPanel panelDistancias = new JPanel();
		panelDistancias.setBackground(new Color(17, 19, 22));
		panelDistancias.setBounds(120, 0, 764, 600);
		panelContenido.add(panelDistancias);
		panelDistancias.setVisible(false);
		panelDistancias.setLayout(null);

		JLabel lblTituloD = new JLabel("Conversor de Distancias");
		lblTituloD.setBounds(10, 15, 300, 25);
		lblTituloD.setForeground(Color.WHITE);
		lblTituloD.setFont(new Font("Roboto", Font.BOLD, 18));
		panelDistancias.add(lblTituloD);

		JPanel panelCambioD = new JPanel();
		panelCambioD.setBounds(30, 100, 705, 165);
		panelCambioD.setLayout(null);
		panelCambioD.setBackground(new Color(33, 36, 38));
		panelDistancias.add(panelCambioD);

		JLabel lblDisDe = new JLabel("De");
		lblDisDe.setForeground(Color.WHITE);
		lblDisDe.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblDisDe.setBounds(10, 11, 46, 15);
		panelCambioD.add(lblDisDe);

		comboBox_Disde.setSelectedItem("Kilómetros (km)");
		comboBox_Disde.setMaximumRowCount(12);
		comboBox_Disde.setForeground(Color.WHITE);
		comboBox_Disde.setFont(new Font("Roboto Medium", Font.PLAIN, 18));
		comboBox_Disde.setBorder(null);
		comboBox_Disde.setBackground(new Color(67, 80, 98));
		comboBox_Disde.setBounds(10, 35, 300, 50);
		panelCambioD.add(comboBox_Disde);

		JLabel lblDisA = new JLabel("A");
		lblDisA.setForeground(Color.WHITE);
		lblDisA.setFont(new Font("Roboto", Font.PLAIN, 16));
		lblDisA.setBounds(391, 11, 46, 15);
		panelCambioD.add(lblDisA);

		comboBox_Disa.setSelectedItem("Metros (m)");
		comboBox_Disa.setMaximumRowCount(12);
		comboBox_Disa.setForeground(Color.WHITE);
		comboBox_Disa.setFont(new Font("Roboto Medium", Font.PLAIN, 18));
		comboBox_Disa.setBorder(null);
		comboBox_Disa.setBackground(new Color(67, 80, 98));
		comboBox_Disa.setBounds(390, 35, 300, 50);
		panelCambioD.add(comboBox_Disa);

		textField_DisDe = new JTextFieldConMargen(10);
		textField_DisDe.setText("1");
		textField_DisDe.setForeground(Color.WHITE);
		textField_DisDe.setFont(new Font("Roboto", Font.BOLD, 26));
		textField_DisDe.setColumns(10);
		textField_DisDe.setBorder(null);
		textField_DisDe.setBackground(new Color(75, 75, 75));
		textField_DisDe.setBounds(10, 85, 300, 60);
		panelCambioD.add(textField_DisDe);

		JLabel lblViceversa_1_2 = new JLabel("");
		lblViceversa_1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lblViceversa_1_2.setBounds(325, 60, 50, 50);
		panelCambioD.add(lblViceversa_1_2);

		textField_Disa = new JTextFieldConMargen(10);
		textField_Disa.setForeground(Color.WHITE);
		textField_Disa.setFont(new Font("Roboto", Font.BOLD, 26));
		textField_Disa.setColumns(10);
		textField_Disa.setBorder(null);
		textField_Disa.setBackground(new Color(75, 75, 75));
		textField_Disa.setBounds(390, 85, 300, 60);
		panelCambioD.add(textField_Disa);

		JPanel panelResultadoD = new JPanel();
		panelResultadoD.setBounds(30, 300, 705, 165);
		panelResultadoD.setLayout(null);
		panelResultadoD.setBackground(new Color(33, 36, 38));
		panelDistancias.add(panelResultadoD);

		lblResultadoDis = new JLabel("");
		lblResultadoDis.setFont(new Font("Roboto", Font.BOLD, 20));
		lblResultadoDis.setHorizontalAlignment(SwingConstants.CENTER);
		lblResultadoDis.setForeground(UIManager.getColor("ScrollBar.background"));
		lblResultadoDis.setBackground(UIManager.getColor("ScrollBar.background"));
		lblResultadoDis.setBounds(50, 40, 605, 100);
		panelResultadoD.add(lblResultadoDis);

		JLabel lblBienvenida = new JLabel("<html>Bienvenido a la App Conversora by Saul Wade<br><br>"
				+ "Puedes convertir divisas con su cotización del día o histórica.<br>"
				+ "Puedes convertir superficies y longitudes.</html>");
		lblBienvenida.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenida.setForeground(UIManager.getColor("ScrollBar.background"));
		lblBienvenida.setFont(new Font("Roboto", Font.BOLD, 20));
		lblBienvenida.setBackground(UIManager.getColor("ScrollBar.background"));
		lblBienvenida.setBounds(140, 120, 700, 100);
		panelContenido.add(lblBienvenida);

		// ChartPanel chartPanel = new ChartPanel((JFreeChart) null);
		// panelCalendario.add(chartPanel);

		// Acciones del boton divisas
		btnMenuDivisas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!btnClicked) {
					btnMenuDivisas.setIcon(new ImageIcon("imagenes/menus/btnMenuDivisasH.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!btnClicked) {
					btnMenuDivisas.setIcon(new ImageIcon("imagenes/menus/btnMenuDivisas.png"));
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Cambia la imagen cuando se hace clic.
				btnMenuDivisas.setIcon(new ImageIcon("imagenes/menus/btnMenuDivisasH.png"));
				btnSuperficie.setIcon(new ImageIcon("imagenes/menus/btnSuperficie.png"));
				btnDistancias.setIcon(new ImageIcon("imagenes/menus/btnDistancias.png"));

				// Hace visible el panelDivisas.
				panelDivisas.setVisible(true);

				// Hace invisible los paneles.
				panelSuperficie.setVisible(false);
				panelDistancias.setVisible(false);

				// Actualiza la variable de control para indicar que el botón ha sido clicado.
				btnClicked = true;
			}
		});

		// Acciones del boton superficie
		btnSuperficie.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!btnClicked) {
					btnSuperficie.setIcon(new ImageIcon("imagenes/menus/btnSuperficieH.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!btnClicked) {
					btnSuperficie.setIcon(new ImageIcon("imagenes/menus/btnSuperficie.png"));
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Cambia la imagen cuando se hace clic.
				btnSuperficie.setIcon(new ImageIcon("imagenes/menus/btnSuperficieH.png"));
				btnMenuDivisas.setIcon(new ImageIcon("imagenes/menus/btnMenuDivisas.png"));
				btnDistancias.setIcon(new ImageIcon("imagenes/menus/btnDistancias.png"));

				// Hace invisible los paneles.
				panelDivisas.setVisible(false);
				panelDistancias.setVisible(false);
				// Hace visible el panelSuperficies.
				panelSuperficie.setVisible(true);

				// Actualiza la variable de control para indicar que el botón ha sido clicado.
				btnClicked = true;
			}
		});

		// Acciones del boton distancias
		btnDistancias.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if (!btnClicked) {
					btnDistancias.setIcon(new ImageIcon("imagenes/menus/btnDistanciasH.png"));
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if (!btnClicked) {
					btnDistancias.setIcon(new ImageIcon("imagenes/menus/btnDistancias.png"));
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// Cambia la imagen cuando se hace clic.
				btnDistancias.setIcon(new ImageIcon("imagenes/menus/btnDistanciasH.png"));
				btnMenuDivisas.setIcon(new ImageIcon("imagenes/menus/btnMenuDivisas.png"));
				btnSuperficie.setIcon(new ImageIcon("imagenes/menus/btnSuperficie.png"));

				// Hace invisible los paneles.
				panelDivisas.setVisible(false);
				panelSuperficie.setVisible(false);
				// Hace visible el panelSuperficies.
				panelDistancias.setVisible(true);

				// Actualiza la variable de control para indicar que el botón ha sido clicado.
				btnClicked = true;
			}
		});

		// Para textField_De
		textField_De.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (ValidadorEntradaNumerica.esEntradaNumerica(textField_De.getText())) {
					double cantidad = Double.parseDouble(textField_De.getText());
					Divisas deMoneda = (Divisas) comboBox_De.getSelectedItem();
					Divisas aMoneda = (Divisas) comboBox_A.getSelectedItem();
					double resultado = ConversorDivisas.convertirDivisas(cantidad, deMoneda, aMoneda,
							calendario.getDate());
					textField_A.setText(Double.toString(resultado));
				}
			}
		});

		// Para textField_A
		textField_A.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (ValidadorEntradaNumerica.esEntradaNumerica(textField_A.getText())) {
					double cantidad = Double.parseDouble(textField_A.getText());
					Divisas deMoneda = (Divisas) comboBox_De.getSelectedItem();
					Divisas aMoneda = (Divisas) comboBox_A.getSelectedItem();
					double resultado = ConversorDivisas.convertirDivisasInverso(cantidad, deMoneda, aMoneda,
							calendario.getDate());
					textField_De.setText(aMoneda.simbolo() + " " + resultado);
				}
			}
		});

		// Para comboBox_De y comboBox_A
		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (ValidadorEntradaNumerica.esEntradaNumerica(textField_De.getText())) {
						double cantidad = Double.parseDouble(textField_De.getText());
						Divisas deMoneda = (Divisas) comboBox_De.getSelectedItem();
						Divisas aMoneda = (Divisas) comboBox_A.getSelectedItem();
						double resultado = ConversorDivisas.convertirDivisas(cantidad, deMoneda, aMoneda,
								calendario.getDate());
						textField_A.setText(Double.toString(resultado));
						mostrarGrafica();
					}
				}
			}
		};
		comboBox_De.addItemListener(itemListener);
		comboBox_A.addItemListener(itemListener);

		((AbstractDocument) textField_De.getDocument()).setDocumentFilter(new DocumentFilter() {
			@Override
			public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
					throws BadLocationException {
				String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

				if (string.matches("^[0-9]*\\.?[0-9]*$")) {
					super.replace(fb, offset, length, text, attrs); // Permite la actualizacion
				} else {
					// Logica para prevenir la actualizacion
				}
			}
		});

		lblViceversa.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// Intercambiar las selecciones de comboBox_De y comboBox_A
				Object cambioDe = comboBox_De.getSelectedItem();
				Object cambioA = comboBox_A.getSelectedItem();

				comboBox_De.setSelectedItem(cambioA);
				comboBox_A.setSelectedItem(cambioDe);
				actualizaUI();
				mostrarGrafica();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// Cambiar el puntero del mouse a una mano cuando el mouse entra en el
				// componente
				lblViceversa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// Restaurar el puntero del mouse cuando el mouse sale del componente
				lblViceversa.setCursor(Cursor.getDefaultCursor());
			}
		});

		calendario.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent e) {
				// Actualizar las conversiones cuando el usuario seleccione una fecha en el
				// calendario
				if ("calendar".equals(e.getPropertyName())) {
					actualizaUI();
					mostrarGrafica();
				}
			}
		});

		// Para textField_Sde
		textField_Sde.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (ValidadorEntradaNumerica.esEntradaNumerica(textField_Sde.getText())) {
					double cantidadSupOrigen = Double.parseDouble(textField_Sde.getText());
					supOrigen = (String) comboBox_Sde.getSelectedItem();
					supDestino = (String) comboBox_Sa.getSelectedItem();
					conversorSuperficies(cantidadSupOrigen, supOrigen, supDestino, textField_Sa);
				}
			}
		});

		// Para comboBox_Sde y comboBox_Sa
		ItemListener itemListenerSup = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (ValidadorEntradaNumerica.esEntradaNumerica(textField_Sde.getText())) {
						double cantidadSupOrigen = Double.parseDouble(textField_Sde.getText());
						supOrigen = (String) comboBox_Sde.getSelectedItem();
						supDestino = (String) comboBox_Sa.getSelectedItem();
						conversorSuperficies(cantidadSupOrigen, supOrigen, supDestino, textField_Sa);
					}
				}
			}
		};
		comboBox_Sde.addItemListener(itemListenerSup);
		comboBox_Sa.addItemListener(itemListenerSup);

		// Para textField_DisDe
		textField_DisDe.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (ValidadorEntradaNumerica.esEntradaNumerica(textField_DisDe.getText())) {
					double cantidadLonOrigen = Double.parseDouble(textField_DisDe.getText());
					lonOrigen = (String) comboBox_Disde.getSelectedItem();
					lonDestino = (String) comboBox_Disa.getSelectedItem();
					conversorLogitud(cantidadLonOrigen, lonOrigen, lonDestino, textField_Disa);
				}
			}
		});

		// Para comboBox_Disde y comboBox_Disa
		ItemListener itemListenerLon = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					if (ValidadorEntradaNumerica.esEntradaNumerica(textField_DisDe.getText())) {
						double cantidadLonOrigen = Double.parseDouble(textField_DisDe.getText());
						String lonOrigen = (String) comboBox_Disde.getSelectedItem();
						String lonDestino = (String) comboBox_Disa.getSelectedItem();
						conversorLogitud(cantidadLonOrigen, lonOrigen, lonDestino, textField_Disa);
					}
				}
			}
		};
		comboBox_Disde.addItemListener(itemListenerLon);
		comboBox_Disa.addItemListener(itemListenerLon);

	}

	/**
	 * Lanzamiento de la aplicacion
	 */
	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Fallo al inicializar LaF");
		}

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					Conversor frame = new Conversor();
					frame.setVisible(true);

					// Centrar la ventana en la pantalla
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		// Crear el conjunto de divisas y cargar los datos desde el archivo
		// "archivo_divisas.txt"
		SetDivisas.getInstancia().clear(); // Limpiar el conjunto si hay datos previos
		CargaDivisas cargaDivisas = new CargaArchivoDivisas("archivos/archivo_divisas.txt");
		try {
			cargaDivisas.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// Llenar los JComboBox con los objetos Divisas y mostrar el ícono de la bandera
		OperacionComboBox operacionCombo = new OperacionComboBox();
		operacionCombo.llenarComboBox(comboBox_De);
		operacionCombo.llenarComboBox(comboBox_A);

		// Establecer los elementos iniciales de los JComboBox
		Divisas divisaUSD = OperacionComboBox.buscarDivisaPorCodigo("USD");
		Divisas divisaMXN = OperacionComboBox.buscarDivisaPorCodigo("MXN");

		comboBox_De.setSelectedItem(divisaUSD);
		comboBox_A.setSelectedItem(divisaMXN);

		// Filtrado de los JComboBox -- Elimina el icono de la bandera del item
		// seleccionado
		// AutoCompleteDecorator.decorate(comboBox_De, null);
		// AutoCompleteDecorator.decorate(comboBox_A);

		// Aplicar el renderizador para mostrar el ícono de la bandera
		comboBox_De.setRenderer(new BanderaRenderer());
		comboBox_A.setRenderer(new BanderaRenderer());

		// Llama a actualizarEtiquetas con estos valores
		actualizaUI();
		mostrarGrafica();
		defaultOtrasConversiones();

	}

	/**
	 * El método actualizaUI() realiza una serie de operaciones para actualizar la
	 * interfaz de usuario (UI) de la aplicación que realiza conversiones de
	 * divisas.
	 *
	 */
	private static void actualizaUI() {
		// Obtener las divisas seleccionadas en los combo box
		Divisas deDivisa = (Divisas) comboBox_De.getSelectedItem();
		Divisas aDivisa = (Divisas) comboBox_A.getSelectedItem();
		Date hoy = calendario.getDate();

		// Obtener la cantidad que será convertida a la otra moneda
		double cantidad = ValidadorEntradaNumerica.esEntradaNumerica(textField_De.getText())
				? Double.parseDouble(textField_De.getText())
				: 0.0;

		/*
		 * Por defecto, la fecha seleccionada es la del día en que se ejecute la app,
		 * sin embargo, el usuario puede seleccionar cualquier otra fecha en el
		 * calendario para la cual quiera conocer el tipo de cambio que aplicó en esa
		 * fecha
		 */
		Date fechaSeleccionada = calendario.getDate();

		// Obtenemos el valor de cambio para la fecha seleccionada y la del día anterior
		TasaCambio tasaCambio = instanciaAPI.load(deDivisa, aDivisa, fechaSeleccionada);

		// Actualizar el resultado en el textfield de destino.
		double resultado = tasaCambio.getTipoCambio() * cantidad;
		textField_A.setText(String.format("%.4f", resultado));

		// Actualizar lblTasaCambioDia
		String tasaCambioDiaText = String.format("1 %s = %.4f %s", deDivisa.codigo(), tasaCambio.getTipoCambio(),
				aDivisa.codigo());
		lblTasaCambioDia.setText(tasaCambioDiaText);

		// Actualizar lblUltimaActualizacion
		if (hoy.equals(calendario.getDate())) {
			DateFormat df = new SimpleDateFormat("dd 'de' MMMM 'de' YYYY, HH:mm:ss z");
			String ultimaActualizacionText = String.format("ÚLTIMA ACTUALIZACIÓN • %s",
					df.format(tasaCambio.getDate()));
			lblUltimaActualizacion.setText(ultimaActualizacionText);
		}

		// Para la variación del tipo de cambio, se obtiene la tasa de cambio para el
		// día anterior
		Calendar cal = Calendar.getInstance();
		cal.setTime(hoy);
		cal.add(Calendar.DATE, -1); // Restar un día
		Date ayer = cal.getTime();

		TasaCambio tasaCambioAyer = instanciaAPI.load(deDivisa, aDivisa, ayer);
		double variacion = tasaCambio.getTipoCambio() - tasaCambioAyer.getTipoCambio();

		if (variacion < 0) {
			lblVariacion.setForeground(Color.RED);
		} else {
			lblVariacion.setForeground(Color.GREEN);
		}
		double porcentajeVariacion = (variacion / tasaCambioAyer.getTipoCambio()) * 100;
		String variacionText = String.format("%.4f (%.2f%%)", variacion, porcentajeVariacion);
		lblVariacion.setText(variacionText);

	}

	/**
	 * Función que se encarga de mostrar una gráfica de conversión entre divisas
	 * utilizando la biblioteca JFreeChart.
	 *
	 */
	private static void mostrarGrafica() {
		// Obtención de las divisas de origen y destino seleccionadas desde los combo
		// boxes
		Divisas deDivisa = (Divisas) comboBox_De.getSelectedItem();
		Divisas aDivisa = (Divisas) comboBox_A.getSelectedItem();

		// Obtención de la fecha final seleccionada desde el componente calendario
		Date fechaFinal = calendario.getDate();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fechaFinal);
		cal.add(Calendar.YEAR, -1); // Resta un año para obtener la fecha de inicio para el gráfico
		Date fechaInicio = cal.getTime();

		XYDataset dataset = GraficaConversor.createDataset(deDivisa, aDivisa, fechaInicio, fechaFinal);
		JFreeChart chart = GraficaConversor.createChart(dataset, deDivisa, aDivisa);

		// Aquí se obtiene la trama XY del gráfico
		XYPlot plot = chart.getXYPlot();

		// Se obtiene el eje X que es un DateAxis
		DateAxis xAxis = (DateAxis) plot.getDomainAxis();

		// Aquí configuramos el formato de fecha para que se muestre solo el año y el
		// mes
		xAxis.setDateFormatOverride(new SimpleDateFormat("dd MMM yyyy"));

		// Crea y configura un panel de gráfico y añádelo al JFrame
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(800, 600));

		JPanel panelCalendario = (JPanel) calendario.getParent();
		if (panelCalendario.getComponentCount() > 1) {
			panelCalendario.remove(1); // Elimina el componente existente en la posición 1 (chartPanel anterior)
		}
		panelCalendario.add(chartPanel, 1); // Agrega el nuevo chartPanel en la posición 1
		panelCalendario.revalidate();
		panelCalendario.repaint();
	}

	// Método para actualizar la conversión de superficies en tiempo real
	private static void conversorSuperficies(double cantidadSupOrigen, String supOrigen, String supDestino,
			JTextField textField_Sa) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		Pattern pattern = Pattern.compile("\\((.*?)\\)");
		Matcher matcherOrigen = pattern.matcher(supOrigen);
		Matcher matcherDestino = pattern.matcher(supDestino);

		if (matcherOrigen.find() && matcherDestino.find()) {
			String parentesisOrigen = matcherOrigen.group(1);
			String parentesisDestino = matcherDestino.group(1);

			try {
				Superficie superficie = new Superficie(cantidadSupOrigen, parentesisOrigen);
				double valorConvertido = superficie.convertirA(parentesisDestino);
				textField_Sa.setText(String.valueOf(valorConvertido));
				// Actualizar el JLabel con el resultado de la conversión
				String resultadoTexto = df.format(cantidadSupOrigen) + " " + supOrigen + " es igual a: "
						+ df.format(valorConvertido) + " " + supDestino;
				lblResultadoS.setText(resultadoTexto);
			} catch (Superficie.UnidadDesconocidaException ex) {
				textField_Sa.setText("Unidad desconocida");
				lblResultadoS.setText(""); // Limpiar el resultado en caso de error
			}
		} else {
			textField_Sa.setText("Unidad desconocida");
			lblResultadoS.setText(""); // Limpiar el resultado en caso de error
		}
	}

	// Método para actualizar la conversión de longitudes en tiempo real
	private static void conversorLogitud(double cantidadSupOrigen, String supOrigen, String supDestino,
			JTextField textField_Disa) {
		DecimalFormat df = new DecimalFormat("#,###.##");
		Pattern pattern = Pattern.compile("\\((.*?)\\)");
		Matcher matcherOrigen = pattern.matcher(supOrigen);
		Matcher matcherDestino = pattern.matcher(supDestino);

		if (matcherOrigen.find() && matcherDestino.find()) {
			String parentesisOrigen = matcherOrigen.group(1);
			String parentesisDestino = matcherDestino.group(1);

			try {
				Longitud longitud = new Longitud(cantidadSupOrigen, parentesisOrigen);
				double valorConvertido;
				valorConvertido = longitud.convertirA(parentesisDestino);
				textField_Disa.setText(String.valueOf(valorConvertido));

				// Actualizar el JLabel con el resultado de la conversión
				String resultadoTexto = df.format(cantidadSupOrigen) + " " + supOrigen + " es igual a: "
						+ df.format(valorConvertido) + " " + supDestino;
				lblResultadoDis.setText(resultadoTexto);
			} catch (UnidadDesconocidaException ex) {
				textField_Disa.setText("Unidad desconocida");
				lblResultadoS.setText(""); // Limpiar el resultado en caso de error
			}

		} else {
			textField_Disa.setText("Unidad desconocida");
			lblResultadoS.setText(""); // Limpiar el resultado en caso de error
		}
	}

	private static void defaultOtrasConversiones() {
		double cantidadSupOrigen = Double.parseDouble(textField_Sde.getText());
		supOrigen = (String) comboBox_Sde.getSelectedItem();
		supDestino = (String) comboBox_Sa.getSelectedItem();
		conversorSuperficies(cantidadSupOrigen, supOrigen, supDestino, textField_Sa);

		double cantidadLonOrigen = Double.parseDouble(textField_DisDe.getText());
		lonOrigen = (String) comboBox_Disde.getSelectedItem();
		lonDestino = (String) comboBox_Disa.getSelectedItem();
		conversorLogitud(cantidadLonOrigen, lonOrigen, lonDestino, textField_Disa);

	}
}