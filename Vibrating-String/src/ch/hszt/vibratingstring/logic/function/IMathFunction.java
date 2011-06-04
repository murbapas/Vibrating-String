/*
 * IMathFunction.java (Created on May 22, 2011, 1:08:12 AM)
 *
 * @author Pascal Murbach
 *
 * The Interface for a mathematical function which provides 
 * a method to numerically calculate the function with a set
 * of given values
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * @author Pascal Murbach
 */
public interface IMathFunction {

  /**
   * The method to implement the numerical calculation
   * of a math function
   * @param x the function-independent values
   * @param length the length of the vibrating string
   * @return the result, the function-dependent values
   */
  public double[] calc(double[] x, double length);

  /**
   * Calculate a single value
   * @param x the x-value
   * @param length the length of the vibrating string
   * @return the result, the function-dependent value
   */
  public double calc(double x, double length);

  /**
   * The method to implement the numerical calculation
   * of the functions fourier coefficient Bn for a given 
   * count of harmonics
   * @param x
   * @param n
   * @param length
   * @return the fourier coefficient
   */
  public double calcBn(double x, double n, double length);
}