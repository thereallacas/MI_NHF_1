

/**
 * The Class ReLu.
 * Quality documentation 2016????
 */
public class ReLu {
	
	/**
	 * Calculate re lu.
	 *
	 * @param x the x
	 * @return the double
	 */
	public static double calculateReLu(double x){
		return x > 0 ? x : 0;
	}
	
	/**
	 * Calculate derivative re lu.
	 *
	 * @param x the x
	 * @return the double
	 */
	public static double calculateDerivativeReLu(double x){
		return x > 0 ? 1 : 0;
	}
}
