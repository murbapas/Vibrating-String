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
   * The start function
   */
  private IMathFunction f;
  /**
   * The second function
   */
  private IMathFunction g;
  /**
   * The precision, when the integration method 
   * should return the result
   */
  private final double EPSILON = 1E-4;
  /**
   * The grid of x-values used to calculate the y-values
   */
  private double[] xGrid;
  /**
   * The initial y-values of the start function
   */
  private double[] yStart;
  /**
   * The time precision
   */
  private double timePrec;
  /**
   * The count of harmonic components
   */
  private int harmonicComp;
  /**
   * Row dimension of the yt-array
   * The y-values of one particular point of time 
   * are stored in a slice
   */
  private int slices;
  /**
   * y-values for each point of time
   */
  private double[][] yt;
  /**
   * initial y-values for each point of time
   * to have the values accessible for another plot
   */
  private double[][] ytStart;

  /**
   * Creates a new instance of {@code VibraString}
   * @param length the length of the vibrating string
   * @param speed the speed
   * @param f the initial function
   * @param g the second function
   * @param precision the precision
   * @param timePrec the time precision
   * @param harmonicComp the harmonic component 
   */
  public VibraString(double length,
          double speed,
          IMathFunction f,
          IMathFunction g,
          double precision,
          double timePrec,
          int harmonicComp) {
    this.length = length;
    this.speed = speed;
    this.f = f;
    this.g = g;
    this.timePrec = timePrec;
    this.harmonicComp = harmonicComp;
    calcSlices();
  }

  /**
   * Calculates the x-grid using a specific length and precision
   * @param length the length
   * @param precision the precision
   * @return the x-grid
   */
  public double[] calcXGrid(double length, double precision) {
    int l = (int) Math.abs((length / precision));
    double[] x = new double[l + 1];
    double step = 0.0;

    for (int i = 0; i <= l; i++) {
      x[i] = new BigDecimal(step).setScale(1, RoundingMode.HALF_EVEN).doubleValue();
      step += precision;
    }
    this.xGrid = x;
    return x;
  }

  /**
   * Calculates the initial y-values
   * @param f
   * @param x
   * @return the initial y-values
   */
  public double[] calcYStart(IMathFunction f, double[] x) {
    this.yStart = f.calc(x, length);
    return this.yStart;
  }

  /**
   * Calculate the fourier coefficient using simpson's adaptive quadrature
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

    double Q1 = h / 6 * (f.calcBn(a, n, length)
            + 4 * f.calcBn(c, n, length) + f.calcBn(b, n, length));
    double Q2 = h / 12 * (f.calcBn(a, n, length)
            + 4 * f.calcBn(d, n, length)
            + 2 * f.calcBn(c, n, length)
            + 4 * f.calcBn(e, n, length) + f.calcBn(b, n, length));
    if (Math.abs(Q2 - Q1) <= EPSILON) {
      return Q2 + (Q2 - Q1) / 15;
    }
    return fourierCoeff(n, a, c, f) + fourierCoeff(n, c, b, f);
  }

  public double adaptiveSimpsons(double n, IMathFunction f,
          double a, double b,
          double epsilon,
          int maxRecursionDepth) {
    double c = (a + b) / 2, h = b - a;
    double fa = f.calcBn(a, n, length);
    double fb = f.calcBn(b, n, length);
    double fc = f.calcBn(c, n, length);
    double S = (h / 6) * (fa + 4 * fc + fb);
    return adaptiveSimpsonsAux(n, f, a, b, epsilon, S, fa, fb, fc, maxRecursionDepth);
  }

  private double adaptiveSimpsonsAux(double n, IMathFunction f, double a, double b, double epsilon,
          double S, double fa, double fb, double fc, int bottom) {
    double c = (a + b) / 2, h = b - a;
    double d = (a + c) / 2, e = (c + b) / 2;
    double fd = f.calcBn(d, n, length), fe = f.calcBn(e, n, length);
    double Sleft = (h / 12) * (fa + 4 * fd + fc);
    double Sright = (h / 12) * (fc + 4 * fe + fb);
    double S2 = Sleft + Sright;
    if (bottom <= 0 || Math.abs(S2 - S) <= 15 * epsilon) {
      return S2 + (S2 - S) / 15;
    }
    return adaptiveSimpsonsAux(n, f, a, c, epsilon / 2, Sleft, fa, fc, fd, bottom - 1)
            + adaptiveSimpsonsAux(n, f, c, b, epsilon / 2, Sright, fc, fb, fe, bottom - 1);
  }

  /**
   * Calculates the whole string movement over
   * time and saves the values in a double[][] array
   * @return the calculated string movement
   */
  public double[][] calcStringMovement() {
    yt = new double[slices][yStart.length];

    for (int i = 0; i < yStart.length; i++) {
      yt[0][i] = yStart[i];
    }
   
    for (int t = 1; t < slices; t++) {
      for (int i = 0; i < yStart.length; i++) {             
        for (int n = 1; n < harmonicComp; n++) {
          yt[t][i] += ((2 / length)
                  * adaptiveSimpsons(n, f, xGrid[0], xGrid[xGrid.length - 1], EPSILON, 100)
                  * Math.cos(speed * n * Math.PI * (t * timePrec) / length)
                  + (2 / (speed * n * Math.PI))
                  * adaptiveSimpsons(n, g, xGrid[0], xGrid[xGrid.length - 1], EPSILON, 100)
                  * Math.sin(speed * n * Math.PI * (t * timePrec) / length))
                  * Math.sin(n * Math.PI * xGrid[i] / length);
//          yt[t][i] += ((2 / length)
//                  * fourierCoeff(n, xGrid[0], xGrid[xGrid.length - 1], f)
//                  * Math.cos(speed * n * Math.PI * (t * timePrec) / length)
//                  + (2 / (speed * n * Math.PI))
//                  * fourierCoeff(n, xGrid[0], xGrid[xGrid.length - 1], g)
//                  * Math.sin(speed * n * Math.PI * (t * timePrec) / length))
//                  * Math.sin(n * Math.PI * xGrid[i] / length);
        }
      }
    }

    ytStart = yt;

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
  public void setLength(double length) {
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
  public void setHarmonicComp(int harmonicComp) {
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
   */
  private void calcSlices() {
    this.slices = (int) ((2 * length) / (speed * timePrec));
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
   * @return the whole movement of the string
   * over time
   */
  public double[][] getYt() {
    return yt;
  }

  /**
   * Sets the yt-values to the initial values that were
   * calculated in the constructor. for faster re-initialization
   * of the string
   */
  public void resetYt() {
    this.yt = ytStart;
  }
}