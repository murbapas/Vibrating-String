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

    private double[] y;
    private double[] x = {0, 0.1000, 0.2000, 0.3000, 0.4000, 0.5000, 0.6000, 0.7000, 0.8000, 0.9000, 1.0000, 1.1000, 1.2000, 1.3000, 1.4000, 1.5000, 1.6000, 1.7000, 1.8000, 1.9000, 2.0000, 2.1000, 2.2000, 2.3000, 2.4000, 2.5000, 2.6000, 2.7000, 2.8000, 2.9000, 3.0000, 3.1000, 3.2000, 3.3000, 3.4000, 3.5000, 3.6000, 3.7000, 3.8000, 3.9000, 4.0000, 4.1000, 4.2000, 4.3000, 4.4000, 4.5000, 4.6000, 4.7000, 4.8000, 4.9000, 5.0000, 5.1000, 5.2000, 5.3000, 5.4000, 5.5000, 5.6000, 5.7000, 5.8000, 5.9000, 6.0000, 6.1000, 6.2000};

    public Calculator(double[] y) {
        this.y = y;
    }

    public void run() {
        while (true) {
            for (double k = 0; k <  2 * Math.PI; k = k + 0.1){
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized(y){
                int l = y.length;
                for (int i = 0; i < l; i++){
                y[i] = Math.sin(x[i] + k * (Math.PI));
                //System.out.println(y[i]);
                }
            y.notify();

            }

            }
        }
    }
}
