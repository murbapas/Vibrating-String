/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hszt.vibratingstring.logic;

import java.util.Observable;

/**
 *
 * @author pascal
 */
public class VibraString {

    private int length;
    private double c;
    private IMathFunction f;
    private IMathFunction g;
    private double[] yStart;
    private double[] xGrid;

    public VibraString() {

        length = 6;
        c = 16.0;
        xGrid = this.getXGrid();
        f = new SquareFunction();
        g = new ZeroFunction();
        yStart = f.calc(xGrid);
      
    }

    /**
     * @return the length
     */
    public int getLength() {

        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the c
     */
    public double getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(double c) {
        this.c = c;
    }

    /**
     * @return the f
     */
    public IMathFunction getF() {
        return f;
    }

    /**
     * @param f the f to set
     */
    public void setF(IMathFunction f) {
        this.f = f;
    }

    /**
     * @return the g
     */
    public IMathFunction getG() {
        return g;
    }

    /**
     * @param g the g to set
     */
    public void setG(IMathFunction g) {
        this.g = g;
    }

    public double[] getXGrid(){
        int l = (int)(length / 0.1);
       // System.out.println(l);
        double[] x = new double[l];
        double j = 0.0d;
        for (int i = 0; i < l; i++){
            x[i] = j;
         //   System.out.println(i + "= " + x[i]);
            j += 0.1d;
        }

        return x;

    }

    public double[] getYStart(){
        return yStart;
    }

    /*Fourier-Coefficient with Trapezoidal-approximation*/

    public double fourierKoeff(double n){

        int l = xGrid.length;
        double h = 0.1d;
        double fa = yStart[0] * Math.sin(n*Math.PI*xGrid[0] / length);
        double fb = yStart[l - 1] * Math.sin(n*Math.PI*xGrid[l - 1] / length);
        double sum = 0.0;

        for (int i = 1; i < l; i++){
            sum += yStart[i] * Math.sin(n*Math.PI*xGrid[i] / length);
                    }
        //System.out.println("sum: " + sum);

        double th = h * ((fa + fb)/2.0d + sum);
        //System.out.println("bn: " + ((2/length) * th));
      // System.out.println("th: " + th);
       // System.out.println("bn: " + bn);
        return (2.0d/length) * th;
    }

    public double fourierKoeffst(double n){

        int l = xGrid.length;
        double[] yStart = g.calc(xGrid);
        double h = 0.1;
        double fa = yStart[0] * Math.sin(n*Math.PI*xGrid[0] / length);
        double fb = yStart[l - 1] * Math.sin(n*Math.PI*xGrid[l - 1] / length);
        double sum = 0.0;

        for (int i = 1; i < length; i++){
            sum += yStart[i] * Math.sin(n*Math.PI*xGrid[i] / length);
        }

        double th = h * ((fa + fb)/2.0d + sum);

        return (2.0d/(c*n*Math.PI)) * th;
    }

    @Override
    public String toString(){
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < xGrid.length; i++){
            sb.append(yStart[i] + " ");
        }
        return sb.toString();
    }

}
