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
   * @return the result, the function-dependent values
   */
  public double[] calc(double[] x);
}