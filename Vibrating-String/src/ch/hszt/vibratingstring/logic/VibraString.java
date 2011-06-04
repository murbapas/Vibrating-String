/*
 * VibraString.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach
 * 
 * This class represents the vibrating string
 */
package ch.hszt.vibratingstring.logic;

import ch.hszt.vibratingstring.logic.function.IMathFunction;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author pascal
 */
public class VibraString {

    /**
     * Length of the vibrating string
     */
    private int length;
    /**
     * Speed constant
     */
    private double c;
    /**
     * TODO add doc
     */
    private IMathFunction f;
    /**
     * TODO add doc
     */
    private IMathFunction g;
    private final double EPSILON = 1E-4;
    private double[] xGrid;
    private double[] yStart;
    private double timePrec;
    private double harmonicComp;
    private int slices;
    private double[][] yt;

    public double[][] getYt() {
        return yt;
    }

    /**
     * Creates a new instance of {@code VibraString}
     * @param length the length of the vibrating string
     * @param c the speed
     * @param f the initial function
     * @param g TODO add doc
     */
    public VibraString(int length, double c, IMathFunction f, IMathFunction g, double precision, double timePrec, int harmonicComp) {
        this.length = length;
        this.c = c;
        this.f = f;
        this.g = g;
        xGrid = calcXGrid(length, precision);
        yStart = calcYStart(f, xGrid);
        this.timePrec = timePrec;
        this.harmonicComp = harmonicComp;
        slices = (int) ((2 * length) / (c * timePrec));
        yt = calcStringMovement();
    }

    public double getHarmonicComp() {
        return harmonicComp;
    }

    public void setHarmonicComp(double harmonicComp) {
        this.harmonicComp = harmonicComp;
    }

    public int getSlices() {
        return slices;
    }

    public void setSlices(int slices) {
        this.slices = slices;
    }

    public double getTimePrec() {
        return timePrec;
    }

    public void setTimePrec(double timePrec) {
        this.timePrec = timePrec;
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
     * @return the speed constant
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

    public double[] getxGrid() {
        return xGrid;
    }

    public double[] getyStart() {
        return yStart;
    }

    public double[] calcXGrid(int length, double precision) {

        int l = (int) (length / precision);
        double[] x = new double[l];
        double step = 0.0d;
        for (int i = 0; i < l; i++) {
            // no other than rounding mode UP or CEILING, otherwise the scaling of
            // the panel goes wrong
            x[i] = new BigDecimal(step).setScale(1, RoundingMode.UP).doubleValue();
            //System.out.println("muh : "+x[i]);
            step += precision;
        }
        return x;
    }

    public double[] calcYStart(IMathFunction f, double[] x) {
        return f.calc(x, length);
    }

    /**
     * Calculate the fourier coefficient using simpson's quadrature
     * method
     * @param n the nth value to solve in the loop
     * @return the coefficient
     */
    public double fourierCoeff(double n, double a, double b) {

        double h = b - a;
        double c = (a + b) / 2.0;
        double d = (a + c) / 2.0;
        double e = (b + c) / 2.0;

        double Q1 = h / 6 * (f.calcBn(a, n, length) + 4 * f.calcBn(c, n, length) + f.calcBn(b, n, length));
        double Q2 = h / 12 * (f.calcBn(a, n, length) + 4 * f.calcBn(d, n, length) + 2 * f.calcBn(c, n, length) + 4 * f.calcBn(e, n, length) + f.calcBn(b, n, length));
        if (Math.abs(Q2 - Q1) <= EPSILON) {
            return Q2 + (Q2 - Q1) / 15;
        } else {
            return fourierCoeff(n, a, c) + fourierCoeff(n, c, b);
        }


    }

    /**
     * TODO add doc
     * @param n
     * @return
     */
  

    public double[][] calcStringMovement() {

        double[][] yt = new double[slices][yStart.length];

        for (int i = 0; i < yStart.length; i++) {
            yt[0][i] = yStart[i];
        }

        for (int t = 1; t < slices; t++) {



            for (int i = 0; i < yStart.length; i++) {

                for (int n = 1; n < harmonicComp; n++) {
                    yt[t][i] += ((2/length) * fourierCoeff(n, xGrid[0], xGrid[xGrid.length - 1]) * Math.cos(c * n * Math.PI * (t * timePrec) / length)
                            + (2 / (c * n * Math.PI)) * fourierCoeff(n, xGrid[0], xGrid[xGrid.length - 1]) * Math.sin(c * n * Math.PI * (t * timePrec) / length))
                            * Math.sin(n * Math.PI * xGrid[i] / length);
                }
            }
        }

        return yt;
    }
}
