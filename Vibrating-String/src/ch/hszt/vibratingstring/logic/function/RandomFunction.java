/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ch.hszt.vibratingstring.logic.function;

/**
 *
 * @author pascal
 */
public class RandomFunction implements IMathFunction{

    double[] y;

    public double[] calc(double[] x, double length) {
        this.y = new double[x.length];
        y[0] = 0.0d;
        y[y.length - 1] = 0.0d;
        for (int i = 1; i < x.length - 1 ; i++){
            y[i] = calc(x[i], length);
        }
        return y;
    }

    public double calc(double x, double length) {
        return Math.random();
    }

    public double calcBn(double x, double n, double length) {
        return y[(int)x] * Math.sin(n * Math.PI * x / length);
    }

}
