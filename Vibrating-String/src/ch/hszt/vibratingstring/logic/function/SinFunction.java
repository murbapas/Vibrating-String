/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hszt.vibratingstring.logic.function;

/**
 *
 * @author pascal
 */
public class SinFunction implements IMathFunction{

    public double[] calc(double[] x) {
        double[] y = new double[x.length];
        for (int i = 0; i < x.length; i ++){
        y[i] = calc(x[i]);
        }
        return y;
    }

    public double calc(double x){
    return Math.sin(2 * x);
    }

    public double calcBn(double x, double n, int l) {
        return Math.sin(2*x) * Math.sin(n * Math.PI * x / l);
    }

}
