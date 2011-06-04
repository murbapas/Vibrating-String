/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hszt.vibratingstring.logic.function;

/**
 *
 * @author Pascal Murbach
 */
public class SinFunction implements IMathFunction {

  public double[] calc(double[] x, double length) {
    double[] y = new double[x.length];
    for (int i = 0; i < x.length; i++) {
      y[i] = calc(x[i], length);
    }
    return y;
  }
  
  public double calc(double x, double length) {
    return Math.sin(2 * Math.PI / length * x);
  }

  public double calcBn(double x, double n, double length) {
    return Math.sin(2 * Math.PI / length * x) * Math.sin(n * Math.PI * x / length);
  }
}
