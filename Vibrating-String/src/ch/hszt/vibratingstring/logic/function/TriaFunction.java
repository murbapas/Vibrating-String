/*
 * TriaFunction.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 * 
 * Implementation of the calculation of the triangle function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * A {@code TriaFunction}.
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 */
public class TriaFunction implements IMathFunction {

  /**
   * Coefficient for the function
   */
  private final double A = 1;//1.0d / 48.0d;

  public double[] calc(double[] x, double length) {

    double[] y = new double[x.length];

    for (int i = 0; i < x.length; i++) {
      y[i] = calc(x[i], length);
    }

    return y;
  }

  public double calc(double x, double length) {
    if (x <= length / 2) {
      return A * x;
    }
    return (length - x) * A;
  }

  public double calcBn(double x, double n, double length) {
    return calc(x,length) * Math.sin(n * Math.PI * x / length);
  }
}