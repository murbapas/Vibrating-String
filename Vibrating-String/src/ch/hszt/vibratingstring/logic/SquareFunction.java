/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hszt.vibratingstring.logic;

/**
 *
 * @author pascal
 */
public class SquareFunction implements IMathFunction{

    public double[] calc(double[] x) {
        double[] y = new double[x.length];

         for (int i = 0; i < x.length; i++){
            //y[i] = 6.0d * x[i] - Math.pow(x[i], 2.0d);
             y[i] = (2 * x[i] - Math.pow(x[i], 2))/ 48;
        }
        return y;
    }

}
