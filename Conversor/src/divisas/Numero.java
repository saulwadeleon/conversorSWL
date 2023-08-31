package divisas;

/**
 * La clase Numero es una implementación personalizada de un número racional, es
 * decir, un número que es representado como la fracción de dos enteros
 * (numerador y denominador).
 */
public class Numero {

	private long numerador;
	private long denominador;

	/**
	 * Este constructor crea una nueva instancia de Numero dada un par de enteros,
	 * los cuales son usados como numerador y denominador, respectivamente. El
	 * método reduceNumber() se llama para simplificar la fracción.
	 *
	 * @param numerator
	 * @param denominator
	 */
	public Numero(long numerator, long denominator) {
		numerador = numerator;
		denominador = denominator;
		reduceNumber();
	}

	/**
	 * Este constructor crea una nueva instancia de Numero con el numerador dado y
	 * un denominador de 1.
	 *
	 * @param numerator
	 */
	public Numero(long numerator) {
		this(numerator, 1);
	}

	/**
	 * Este constructor crea una nueva instancia de Numero con un número de punto
	 * flotante. Convierte el número de punto flotante a una fracción llamando al
	 * método estático valueOf(double n).
	 *
	 * @param n
	 */
	public Numero(double n) {
		Numero number = Numero.valueOf(n);
		numerador = number.getNumerador();
		denominador = number.getDenominador();
		reduceNumber();
	}

	/**
	 * Este método convierte un número de punto flotante a una fracción. Primero,
	 * determina la cantidad de dígitos después del punto decimal y escala tanto el
	 * numerador como el denominador de la fracción por la misma cantidad.
	 * Finalmente, redondea el numerador y devuelve un nuevo Numero.
	 *
	 * @param n
	 * @return
	 */
	public static Numero valueOf(double n) {
		String textNumber = String.valueOf(n);
		long digitsDec = textNumber.length() - 1 - textNumber.indexOf('.');

		long denom = 1;
		for (long i = 0; i < digitsDec; i++) {
			n *= 10;
			denom *= 10;
		}

		long num = Math.round(n);

		return new Numero(num, denom);
	}

	/**
	 * Este método calcula el mínimo común múltiplo y el máximo común divisor de dos
	 * enteros, respectivamente.
	 *
	 * @param numberA
	 * @param numberB
	 * @return
	 */
	private static long multiploComunMinimo(long numberA, long numberB) {
		return Math.abs(numberA * (numberB / multiploComunDivisor(numberA, numberB)));
	}

	/**
	 * Este método calcula el máximo común múltiplo y el máximo común divisor de dos
	 * enteros, respectivamente.
	 *
	 * @param numberA
	 * @param numberB
	 * @return
	 */
	private static long multiploComunDivisor(long numberA, long numberB) {
		long temp = 0;
		while (numberB > 0) {
			temp = numberB;
			numberB = numberA % numberB;
			numberA = temp;
		}
		return numberA;
	}

	/**
	 * Este métodos devuelven el numerador de Numero.
	 *
	 * @return
	 */
	public long getNumerador() {
		return numerador;
	}

	/**
	 * Este métodos devuelven el denominador de Numero.
	 *
	 * @return
	 */
	public long getDenominador() {
		return denominador;
	}

	/**
	 * Este método implementa las operaciones aritméticas básicas de adición,
	 * substracción y multiplicación entre dos Numeros.
	 */
	@Override
	public String toString() {
		return String.valueOf((double) numerador / (double) denominador);
	}

	/**
	 * Este método comprueba si dos Numeros son iguales.
	 */
	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (object instanceof Numero)
			return equals((Numero) object);
		return false;
	}

	/**
	 * Este método comprueba si dos Numeros son iguales.
	 */
	private boolean equals(Numero number) {
		return (number.numerador == numerador && number.denominador == denominador);
	}

	/**
	 * Este método implementa las operaciones aritméticas básicas de adición,
	 * substracción y multiplicación entre dos Numeros.
	 */
	public Numero add(Numero number) {
		final long numeratorA = getNumerador();
		final long numeratorB = number.getNumerador();
		final long denominatorA = getDenominador();
		final long denominatorB = number.getDenominador();

		long numeratorC;
		long denominatorC = multiploComunMinimo(denominatorA, denominatorB);

		numeratorC = (denominatorC / denominatorA) * numeratorA + (denominatorC / denominatorB) * numeratorB;

		return new Numero(numeratorC, denominatorC);
	}

	/**
	 * Este método implementa las operaciones aritméticas básicas de adición,
	 * substracción y multiplicación entre dos Numeros.
	 */
	public Numero sub(Numero number) {
		final long numeratorA = getNumerador();
		final long numeratorB = number.getNumerador();
		final long denominatorA = getDenominador();
		final long denominatorB = number.getDenominador();

		long numeratorC;
		long denominatorC = multiploComunMinimo(denominatorA, denominatorB);

		numeratorC = (denominatorC / denominatorA) * numeratorA - (denominatorC / denominatorB) * numeratorB;

		return new Numero(numeratorC, denominatorC);
	}

	/**
	 * Este método implementa las operaciones aritméticas básicas de adición,
	 * substracción y multiplicación entre dos Numeros.
	 */
	public Numero multiply(Numero number) {
		return new Numero(numerador * number.getNumerador(), denominador * number.getDenominador());
	}

	/**
	 * Este método simplifica la fracción reduciendo tanto el numerador como el
	 * denominador por los mismos factores primos, cuando sea posible.
	 */
	private void reduceNumber() {
		int[] primeNumbers = new int[] { 2, 3, 5, 7, 11, 13, 15, 17 };

		for (Integer prime : primeNumbers)
			if (numerador % prime == 0 && denominador % prime == 0) {
				numerador /= prime;
				denominador /= prime;
			}
	}

}
