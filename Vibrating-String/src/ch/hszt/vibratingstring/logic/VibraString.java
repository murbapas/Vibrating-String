/*
 * VibraString.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 * 
 * This class represents the vibrating string
 */
package ch.hszt.vibratingstring.logic;

import ch.hszt.vibratingstring.logic.function.IMathFunction;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * A {@code VibraString}.
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 */
public class VibraString {

  /**
   * Length of the vibrating string
   */
  private double length;
  /**
   * Speed constant
   */
  private double speed;
  /**
   * TODO add doc
   */
  private IMathFunction f;
  /**
   * TODO add doc
   */
  private IMathFunction g;
  /**
   * TODO add doc
   */
  private final double EPSILON = 1E-4;
  /**
   * TODO add doc
   */
  private double[] xGrid;
  /**
   * TODO add doc
   */
  private double[] yStart;
  /**
   * TODO add doc
   */
  private double timePrec;
  /**
   * TODO add doc
   */
  private double harmonicComp;
  /**
   * TODO add doc
   */
  private int slices;
  /**
   * TODO add doc
   */
  private double[][] yt;
  private double[][] ytStart;

  /**
   * Creates a new instance of {@code VibraString}
   * @param length the length of the vibrating string
   * @param speed the speed
   * @param f the initial function
   * @param g TODO add doc
   * @param precision the precision
   * @param timePrec the time precision
   * @param harmonicComp the harmonic component 
   */
  public VibraString(double length, double speed, IMathFunction f, IMathFunction g, double precision, double timePrec, int harmonicComp) {
    this.length = length;
    this.speed = speed;
    this.f = f;
    this.g = g;
    this.timePrec = timePrec;
    this.harmonicComp = harmonicComp;

    xGrid = calcXGrid(length, precision);
    yStart = calcYStart(f, xGrid);
    slices = (int) ((2 * length) / (speed * timePrec));
    yt = calcStringMovement();
    ytStart = yt;
  }

  /**
   * Calculates the x-grid using a specific length and precision
   * @param length the length
   * @param precision the precision
   * @return the x-grid
   */
  public double[] calcXGrid(double length, double precision) {

    int l = (int) (length / precision);
    double[] x = new double[l + 1];
    double step = 0.0;

    for (int i = 0; i <= l; i++) {
      //System.out.println(step);
      // no other than rounding mode UP or CEILING, otherwise the scaling of
      // the panel goes wrong
      x[i] = new BigDecimal(step).setScale(1, RoundingMode.HALF_EVEN).doubleValue();
      //System.out.println(x[i]);
      step += precision;
    }
    return x;
  }

  /**
   * 
   * @param f
   * @param x
   * @return 
   */
  public double[] calcYStart(IMathFunction f, double[] x) {
    return f.calc(x, length);
  }

  /**
   * Calculate the fourier coefficient using simpson's quadrature
   * method
   * @param n the nth value to solve in the loop
   * @param a the first x-value
   * @param b the last x-value
   * @param f the function for which the coefficient should be calculated
   * @return the coefficient
   */
  public double fourierCoeff(double n, double a, double b, IMathFunction f) {

    double h = b - a;
    double c = (a + b) / 2.0;
    double d = (a + c) / 2.0;
    double e = (b + c) / 2.0;

    double Q1 = h / 6 * (f.calcBn(a, n, length) + 4 * f.calcBn(c, n, length) + f.calcBn(b, n, length));
    double Q2 = h / 12 * (f.calcBn(a, n, length) + 4 * f.calcBn(d, n, length) + 2 * f.calcBn(c, n, length) + 4 * f.calcBn(e, n, length) + f.calcBn(b, n, length));
    if (Math.abs(Q2 - Q1) <= EPSILON) {
      return Q2 + (Q2 - Q1) / 15;
    }
    return fourierCoeff(n, a, c, f) + fourierCoeff(n, c, b, f);
  }

  /**
   * TODO add doc
   * @return TODO add doc
   */
  public double[][] calcStringMovement() {
    System.out.println("calc string movement");

    // TODO rename
    double[][] yt = new double[slices][yStart.length];

    for (int i = 0; i < yStart.length; i++) {
      yt[0][i] = yStart[i];
    }

    for (int t = 1; t < slices; t++) {
      for (int i = 0; i < yStart.length; i++) {
        for (int n = 1; n < harmonicComp; n++) {
          yt[t][i] += ((2 / length) * fourierCoeff(n, xGrid[0], xGrid[xGrid.length - 1], f)
                  * Math.cos(speed * n * Math.PI * (t * timePrec) / length)
                  + (2 / (speed * n * Math.PI)) * fourierCoeff(n, xGrid[0], xGrid[xGrid.length - 1], g)
                  * Math.sin(speed * n * Math.PI * (t * timePrec) / length))
                  * Math.sin(n * Math.PI * xGrid[i] / length);
        }
      }
    }

    return yt;
  }

  /**
   * @return the length
   */
  public double getLength() {
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
    return speed;
  }

  /**
   * @param c the c to set
   */
  public void setC(double c) {
    this.speed = c;
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

  /**
   * @return the x-grid
   */
  public double[] getxGrid() {
    return xGrid;
  }

  /**
   * @return the harmonic component
   */
  public double getHarmonicComp() {
    return harmonicComp;
  }

  /**
   * Sets the harmonic component
   * @param harmonicComp the harmonic component to set
   */
  public void setHarmonicComp(double harmonicComp) {
    this.harmonicComp = harmonicComp;
  }

  /**
   * @return the slices
   */
  public int getSlices() {
    return slices;
  }

  /**
   * Set the slices
   * @param slices the slices to set
   */
  public void setSlices(int slices) {
    this.slices = slices;
  }

  /**
   * @return the time precision
   */
  public double getTimePrec() {
    return timePrec;
  }

  /**   
   * Sets the time precision
   * @param timePrec the time precision to set
   */
  public void setTimePrec(double timePrec) {
    this.timePrec = timePrec;
  }

  /**
   * @return the initial y-values
   */
  public double[] getyStart() {
    return yStart;
  }

  /**
   * @return TODO add doc
   */
  public double[][] getYt() {
    return yt;
  }

  /**
   * Sets the yt-values to the initial values that were
   * calculated in the constructor
   */
  public void resetYt() {
    this.yt = ytStart;
  }
}