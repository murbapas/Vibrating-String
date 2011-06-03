/*
 * SawToothFunction.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach
 * 
 * Implementation of the calculation of the sawtooth function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * @author Pascal Murbach
 */
public class SawToothFunction implements IMathFunction {

  public double[] calc(double[] x) {

    double[] y = new double[x.length];

    for (int i = 0; i < x.length; i++) {
      y[i] = calc(x[i]);
    }

    return y;
  }

  public double calc(double x) {
    return Math.asin(Math.sin(1*x));
  }
}