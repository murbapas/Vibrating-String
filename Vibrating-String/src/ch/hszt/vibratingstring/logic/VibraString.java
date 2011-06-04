/*
 * VibraString.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach
 * 
 * This class represents the vibrating string
 */
package ch.hszt.vibratingstring.logic;

import ch.hszt.vibratingstring.logic.function.IMathFunction;

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

    //private double[] x;

//    public void setX(double[] x) {
//        this.x = x;
//    }
   

    /**
     * Creates a new instance of {@code VibraString}
     * @param length the length of the vibrating string
     * @param c the speed
     * @param f the initial function
     * @param g TODO add doc
     */
    public VibraString(int length, double c, IMathFunction f, IMathFunction g) {
        this.length = length;
        this.c = c;
        this.f = f;
        this.g = g;
        
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

//  /**
//   * Calculate the set of x-values
//   * to use in the functions
//   * TODO parameterize precision
//   * @return the x-grid
//   */
//  public double[] getXGrid() {
//    double precision = 0.1d;
//    int l = (int) (length / precision);
//    double[] x = new double[l];
//    double step = 0.0d;
//    for (int i = 0; i < l; i++) {
//      x[i] = new BigDecimal(step).setScale(1, RoundingMode.UP).doubleValue();
//      //System.out.println(x[i]);
//      step += precision;
//    }
//    return x;
//  }
//  public double fourierCoeff(double a, double b, double[] x, double[] y, double n) {
//        
//    int fractions = 20;
//    double sum = 0.0d;        
//    double h = (b - a) / fractions;
//    double[] xi = new double[1];   
//    
//    //double sinus = Math.sin(n * Math.PI * x / 10000); 
//        
//    //for (int i = 1; i <= fractions - 1; i++) {
//    for (int i = 1; i <= x.length-1; i++) {
//      //xi[0] = (a + i * h) * sinus;
//      xi[0] = (a + i * h) * Math.sin(n*Math.PI*x[i] / length);
//      sum += f.calc(xi)[0];
//    }
//    double sum2 = 0.0d;
//    //for (int i = 1; i <= fractions; i++) {
//    for (int i = 1; i <= x.length; i++) {
//      //xi[0] = (((a + (i - 1) * h) + (a + i * h)) / 2) * sinus;
//      xi[0] = (((a + (i - 1) * h) + (a + i * h)) / 2) * Math.sin(n*Math.PI*x[i] / length);
//      sum2 += f.calc(xi)[0];
//    }
////    double sf = (h / 3) * (1 / 2 * f.calc(new double[]{a * sinus})[0] + sum + 2 * sum2
////            + 1 / 2 * f.calc(new double[]{b * sinus})[0]);
//        double sf = (h / 3) * (1 / 2 * f.calc(new double[]{a * Math.sin()})[0] + sum + 2 * sum2
//            + 1 / 2 * f.calc(new double[]{b * sinus})[0]);
//
//    return 2 / length * sf;
//  }
    /**
     * Calculate the fourier coefficient using simpson's quatrature
     * method
     * @param n the nth value to solve in the loop
     * @return the coefficient
     */
    public double fourierCoeff(double n, double a, double b) {

        //double bn = (1 / (3 * Math.pow(n, 3) * Math.pow(Math.PI, 3))) * (1 - Math.cos(n * Math.PI));
//    double bn = (1 / (6 * Math.pow(n, 2) * Math.pow(Math.PI, 2))) * Math.sin(n * Math.PI / 2);
//    return bn;

//        double sum = 0.0d;
//        double a = x[0];
//        double b = x[x.length - 1];
//        double h = (b - a) / x.length;
//        double[] y = f.calc(x);
//
//    for (int i = 1; i <= x.length - 1; i++) {
//      double[] xi = {(a + i * h) * Math.sin(n * Math.PI / x.length)};
//      sum += f.calc(xi)[0];
//    }
//    double sum2 = 0.0d;
//    for (int i = 1; i <= x.length; i++) {
//      double[] xi = {(((a + (i - 1) * h) / (a + i * h)) / 2) * Math.sin(n * Math.PI / x.length) };
//      sum2 += f.calc(xi)[0];
//    }
//    double sf = (h / 3) * (1 / 2 * f.calc(new double[]{a})[0] + sum + 2 * sum2
//            + 1 / 2 * f.calc(new double[]{a})[0]);
//
//    return sf;



//    int l = x.length;
//    double h = 0.1d;
//    // double fa = yStart[0] * Math.sin(n*Math.PI*xGrid[0] / length);
//    //double fb = yStart[l - 1] * Math.sin(n*Math.PI*xGrid[l - 1] / length);
//    double sum = 0;
//
//    for (int i = 1; i < l - 1; i++) {
//      //sum += yStart[i] * Math.sin(n*Math.PI*xGrid[i] / length);
//      sum += y[i] * Math.sin(n * Math.PI * x[i] / length);
//    }
//    System.out.println("sum: " + sum);
//
//    double th = h * sum;
//    //      System.out.println("bn: " + ((2/length) * th));
//    //      System.out.println("th: " + th);
//    //      System.out.println("bn: " + bn);
//    return (2.0d/length) * th;



//        double[] y = f.calc(x);
//        double fbn[] = new double[x.length];
//        for (int i = 0; i < x.length; i++) {
//            fbn[i] = (y[i] * Math.sin((n * Math.PI * x[i]) / length));
//        }
//
//
//        double a = x[0];
//        double b = x[fbn.length -1];
//        double h = (b - a) / x.length;
//       // double sf = (b-a)/6*(fbn[(int)a] + 4*(fbn[(int)((a+b)/2)]+fbn[(int)b]));
//
//        double sum = 0.0;
//        for (int i = 0; i < x.length - 1; i++){
//            sum += fbn[i];
//        }
//
//        double tf = h * ((fbn[(int)a] + fbn[(int)b])/ 2 + sum );
//
//        //return sf;
//        return (2 / length) * tf;

          double EPSILON = 1E-6;
//          double a = x[0];
//          double b = x[x.length -1];
          double h = b - a;
          double c = (a + b) / 2.0;
          double d = (a + c) / 2.0;
          double e = (b + c) / 2.0;
           double Q1 = h/6  * (f.calcBn(a, n, length) + 4*f.calcBn(c, n, length) + f.calcBn(b, n, length));
        double Q2 = h/12 * (f.calcBn(a, n, length) + 4*f.calcBn(d, n, length) + 2*f.calcBn(c, n, length) + 4*f.calcBn(e, n, length) + f.calcBn(b, n, length));
        if (Math.abs(Q2 - Q1) <= EPSILON)
            return Q2 + (Q2 - Q1) / 15;
        else
            return fourierCoeff(n, a, c) + fourierCoeff(n, c, b);



    }

    /**
     * TODO add doc
     * @param n
     * @return
     */
    public double fourierCoeffST(double n) {

//        int l = xGrid.length;
//    double[] yStart = g.calc(xGrid);
//    double h = 0.1;
//    double fa = yStart[0] * Math.sin(n * Math.PI * xGrid[0] / length);
//    double fb = yStart[l - 1] * Math.sin(n * Math.PI * xGrid[l - 1] / length);
//    double sum = 0.0;
//
//    for (int i = 1; i < length; i++) {
//      sum += yStart[i] * Math.sin(n * Math.PI * xGrid[i] / length);
//    }
//
//    double th = h * ((fa + fb) / 2.0d + sum);
//
//    return (2.0d / (c * n * Math.PI)) * th;
        return 0.0d;
    }
//  @Override
//  public String toString() {
//    StringBuilder sb = new StringBuilder(x.length);
//    for (int i = 0; i < x.length; i++) {
//      sb.append(y[i]).append(" ");
//    }
//    return sb.toString();
//  }
}
