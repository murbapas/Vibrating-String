/*
 * SquareFunction.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach
 * 
 * Implementation of the calculation of the square function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * @author Pascal Murbach
 */
public class SquareFunction implements IMathFunction {

  public double[] calc(double[] x) {
    double[] y = new double[x.length];

    for (int i = 0; i < x.length; i++) {
      //y[i] = 6.0d * x[i] - Math.pow(x[i], 2.0d);
      y[i] = calc(x[i]);
      //System.out.println("y" + i + ": " + y[i]);
    }
    return y;
  }

  public double calc(double x) {
    return (2 * x - Math.pow(x,2));
  }

  public double calcBn(double x, double n, int L){
  return (2 * x - Math.pow(x,2)) * Math.sin(n * Math.PI * x / L);

  }
}