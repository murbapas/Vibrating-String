/*
 * TriaFunction.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach
 * 
 * Implementation of the calculation of the triangle function
 */
package ch.hszt.vibratingstring.logic.function;

/**
 * @author Pascal Murbach
 */
public class TriaFunction implements IMathFunction {
    
    double a = 1.0d / 48.0d;

  public double[] calc(double[] x) {
    double l = x[x.length - 1];

    
    //System.out.println("a: " + a);
    double[] y = new double[x.length];

    for (int i = 0; i < x.length / 2; i++) {
      y[i] = a * x[i];
      //System.out.println("y" + i + " = " + y[i]);
    }

    for (int i = (x.length / 2); i < x.length; i++) {
      y[i] = (l - x[i]) * a;
      // System.out.println("y" + i + " = " + y[i]);
    }

    return y;
  }

    public double calcBn(double x, double n, int l) {
        if (x <= l / 2){
        return a * x * Math.sin(n * Math.PI * x / l);
        }
        return (l - x) * a *Math.sin(n * Math.PI * x / l);
    }
}