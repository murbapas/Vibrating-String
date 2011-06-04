/*
 * RectangleFunction.java (Created on Jun 4, 2011, 6:01:17 PM)
 * 
 * @author Farhan Fayyaz (fafa at ten.ch)
 * 
 * Implementation of the calculation of the sawtooth function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * A {@code RectangleFunction}.
 *
 * @author Farhan Fayyaz
 */
public class RectangleFunction implements IMathFunction {

  public double[] calc(double[] x, double length) {
    double[] y = new double[x.length];

    for (int i = 0; i < y.length; i++) {
      y[i] = calc(x[i], length);
      System.out.println(y[i]);
    }
    return y;
  }

  public double calc(double x, double length) {
    //return Math.sin(x) / Math.abs(Math.sin(x));
    double b = 2 * Math.PI / length;
    
    if (x >= 0.0d || x <= length / 2) {
      return Math.sin(b * x) / Math.abs(Math.sin(b * x));
    }    
    return 1.0d;
  }

  public double calcBn(double x, double n, double length) {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}