/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hszt.vibratingstring.logic;

/**
 *
 * @author pascal
 */
public class TriaFunction implements IMathFunction {

    public double[] calc(double[] x) {
        double l = x[x.length-1];

        double a = 1.0d / 48.0d ;
        //System.out.println("a: " + a);
        double[] y  = new double[x.length];



        for (int i = 0; i < x.length / 2; i++){
            y[i] = a * x[i];
           // System.out.println("y" + i + " = " + y[i]);
        }

        for (int i = (x.length / 2); i < x.length ; i++){
            y[i] = (l - x[i]) * a;
           // System.out.println("y" + i + " = " + y[i]);
        }

        return y;
    }

}
