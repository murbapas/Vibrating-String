/*
 * SquareFunction.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 * 
 * Implementation of the calculation of the square function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * A {@code SquareFunction}.
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 */
public class SquareFunction implements IMathFunction {

  public double[] calc(double[] x, double length) {
    double[] y = new double[x.length];

    for (int i = 0; i < x.length; i++) {
      y[i] = calc(x[i], length);
    }
    return y;
  }

  public double calc(double x, double length) {
    return (length * x - Math.pow(x, 2));
  }

  public double calcBn(double x, double n, double length) {
    return calc(x, length) * Math.sin(n * Math.PI * x / length);
  }

  @Override
  public String toString() {
    return "Square";
  }
}