/*
 * RectangleFunction.java (Created on Jun 4, 2011, 6:01:17 PM)
 * 
 * @author Farhan Fayyaz / Pascal Murbach
 * 
 * Implementation of the calculation of the trapezium function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * A {@code RectangleFunction}.
 *
 * @author Farhan Fayyaz / Pascal Murbach
 */
public class TrapeziumFunction implements IMathFunction {

  /**
   * The y-values
   */
  double[] y;

  public double[] calc(double[] x, double length) {
    y = new double[x.length];

    y[0] = 0.0d;
    y[y.length - 1] = 0.0d;

    for (int i = 1; i < y.length - 1; i++) {
      y[i] = calc(x[i], length);
    }
    return y;
  }

  public double calc(double x, double length) {
    return 1.0d;
  }

  public double calcBn(double x, double n, double length) {
    return calc(x, length) * Math.sin(n * Math.PI * x / length);
  }

  @Override
  public String toString() {
    return "Trapezium";
  }
}