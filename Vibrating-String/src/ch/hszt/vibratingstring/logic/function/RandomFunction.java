/*
 * RandomFunction.java (Created on Jun 4, 2011, 6:01:17 PM)
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 * 
 * Implementation of the calculation of a random function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * A {@code RandomFunction}.
 *
 * @author Pascal Murbach / Farhan Fayyaz
 */
public class RandomFunction implements IMathFunction {

  /**
   * The y-values
   */
  double[] y;

  public double[] calc(double[] x, double length) {
    this.y = new double[x.length];
    y[0] = 0.0d;
    y[y.length - 1] = 0.0d;
    for (int i = 1; i < x.length - 1; i++) {
      y[i] = calc(x[i], length);
    }
    return y;
  }

  public double calc(double x, double length) {
    return Math.random();
  }

  public double calcBn(double x, double n, double length) {
    return y[(int) (x * 10)] * Math.sin(n * Math.PI * x / length);
  }

  @Override
  public String toString() {
    return "Random";
  }
}
