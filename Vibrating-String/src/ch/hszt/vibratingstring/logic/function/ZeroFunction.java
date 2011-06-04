/*
 * VibraString.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach
 * 
 * Implementation of the calculation of the zero function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * @author Pascal Murbach
 */
public class ZeroFunction implements IMathFunction {

  public double[] calc(double[] x, double length) {
    double[] y = new double[x.length];
    for (int i = 0; i < x.length; i++) {
      y[i] = calc(x[i], length);
    }
    return y;
  }

  public double calc(double x, double length) {
    return 0.0d;
  }

  public double calcBn(double x, double n, double length) {
    return 0.0d;
  }
}