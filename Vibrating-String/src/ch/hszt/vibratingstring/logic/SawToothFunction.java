/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hszt.vibratingstring.logic;

/**
 *
 * @author pascal
 */
public class SawToothFunction implements IMathFunction {

    public double[] calc(double[] x) {
        
        double[] y  = new double[x.length];

        for (int i = 0; i< x.length;i++) {
            y[i] = 1*Math.asin(1*Math.sin(x[i]));
        }


        return y;
    }

}