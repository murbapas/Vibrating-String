/*
 * SawToothFunction.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 * 
 * Implementation of the calculation of the sawtooth function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * A {@code SawToothFunction}.
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 */
public class SawToothFunction implements IMathFunction {

  public double[] calc(double[] x, double length) {

    double[] y = new double[x.length];

    for (int i = 0; i < x.length; i++) {
      y[i] = calc(x[i], length);
    }

    return y;
  }

  public double calc(double x, double length) {
    return 1 * Math.asin(Math.sin(2 * Math.PI / length * x));
  }

  public double calcBn(double x, double n, double length) {
    return calc(x, length) * Math.sin(n * Math.PI * x / length);
  }

  @Override
  public String toString() {
    return "Sawtooth";
  }
}