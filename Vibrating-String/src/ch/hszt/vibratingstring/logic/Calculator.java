/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hszt.vibratingstring.logic;

import java.util.List;

/**
 *
 * @author pascal
 */
public class Calculator implements Runnable {

    private final double[] y;
    //private double[] x = {0, 0.1000, 0.2000, 0.3000, 0.4000, 0.5000, 0.6000, 0.7000, 0.8000, 0.9000, 1.0000, 1.1000, 1.2000, 1.3000, 1.4000, 1.5000, 1.6000, 1.7000, 1.8000, 1.9000, 2.0000, 2.1000, 2.2000, 2.3000, 2.4000, 2.5000, 2.6000, 2.7000, 2.8000, 2.9000, 3.0000, 3.1000, 3.2000, 3.3000, 3.4000, 3.5000, 3.6000, 3.7000, 3.8000, 3.9000, 4.0000, 4.1000, 4.2000, 4.3000, 4.4000, 4.5000, 4.6000, 4.7000, 4.8000, 4.9000, 5.0000, 5.1000, 5.2000, 5.3000, 5.4000, 5.5000, 5.6000, 5.7000, 5.8000, 5.9000, 6.0000, 6.1000, 6.2000};
    private double[] x;
    private VibraString string;
    private double c;
    private int length;

    public Calculator(double[] x, double[] y, VibraString string) {
        this.y = y;
        this.x = x;
        this.string = string;
        c = string.getC();
        length = string.getLength();
    }

    public void run() {
        while (true) {
            for (double t = 0.0; t < 10.0; t+= 0.001) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                synchronized (y) {
                    int l = y.length;
                    for (int x = 0; x < l; x++) {
                        y[x] = 0.0;
                        for (double n = 1.0; n < 10.0; n++){
                            y[x] += (string.fourierKoeff(n) * Math.cos(c * n * Math.PI * t / length)
                                    + string.fourierKoeffst(n) * Math.sin(c * n * Math.PI * t / length))
                                    * Math.sin(n * Math.PI * this.x[x] / length);
//                            System.out.println("y" + x + "= " + y[x]);
//                            System.out.println(string.fourierKoeff(n));
                        }
                    }
                    y.notify();

                }

            }
        }
    }
}
