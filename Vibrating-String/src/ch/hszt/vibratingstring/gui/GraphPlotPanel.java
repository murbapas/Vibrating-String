/*
 * GraphPlotPanel.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Farhan Fayyaz / Pascal Murbach
 * 
 * This is the panel in which the graph is painted
 */
package ch.hszt.vibratingstring.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

/**
 * A {@code GraphPlotPanel}.
 *
 * @author Farhan Fayyaz / Pascal Murbach
 */
@SuppressWarnings("serial")
public class GraphPlotPanel extends JPanel {

  /**
   * abscissa-values
   */
  private double[] x;
  /**
   * The smallest abscissa value
   */
  private Double xMin;
  /**
   * The biggest abscissa value
   */
  private Double xMax;
  /**
   * ordinate-values
   */
  private double[] y;
  /**
   * The smallest ordinate value
   */
  private Double yMin;
  /**
   * The biggest ordinate value
   */
  private Double yMax;
  /**
   * Gap between edge of JPanel and graph
   */
  private final int PAD = 20;

  /**
   * Creates a new instance of {@code GraphPlotPanel}.
   */
  public GraphPlotPanel() {
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
            RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
            RenderingHints.VALUE_INTERPOLATION_BICUBIC);
    g2.setRenderingHint(RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
    g2.setPaint(Color.blue);

//    double[] newXExtremals = getExtremals(x);
//    double[] newYExtremals = getExtremals(y);
//
//    if (xMin > newXExtremals[0]) {
//      setxMin(newXExtremals[0]);
//    }
//
//    if (xMax < newXExtremals[1]) {
//      setxMax(newXExtremals[1]);
//    }
//
//    if (yMin > newYExtremals[0]) {
//      setyMin(newYExtremals[0]);
//    }
//
//    if (yMax < newYExtremals[1]) {
//      setyMax(newYExtremals[1]);
//    }

    int w = getWidth();
    int h = getHeight();
    // space between abscissa values calulated with the panel width
    double xScale = (w - 2 * PAD) / xMax;//(xMax - xMin);
    // space between ordinate values calculated with the panel height
    double yScale = (h - 2 * PAD) / yMax;//(yMax - yMin);

    // Draw abcissa.
    g2.draw(new Line2D.Double(PAD, (h - PAD) / 2, w - PAD, (h - PAD) / 2));
    // Draw ordinate
    g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));

    // Connect the data points with lines
    for (int i = 0; i < x.length - 1; i++) {
      double x1 = (PAD + (xScale * x[i]));
      double y1 = ((h - PAD) / 2) - (yScale * y[i]);

      double x2 = (PAD + (xScale * x[i + 1]));
      double y2 = ((h - PAD) / 2) - (yScale * y[i + 1]);
      g2.draw(new Line2D.Double(x1, y1, x2, y2));
    }
  }

  /**
   * @param d the array of doubles to find the extremals
   * @return the extremal values of x and y
   */
  public double[] getExtremals(double[] d) {
    double max = -Integer.MAX_VALUE;
    double min = -max;

    for (int i = 0; i < d.length; i++) {
      if (d[i] < min) {
        min = d[i];
      }
      if (d[i] > max) {
        max = d[i];
      }
    }
    return new double[]{min, max};
  }

  /**
   * Sets the extremal values
   * @param x the x-values
   * @param y the y-values
   */
  public void setExtremals(double[] x, double[] y) {
    double[] xExtremals = getExtremals(x);
    this.xMin = xExtremals[0];
    this.xMax = xExtremals[1];

    double[] yExtremals = getExtremals(y);
    this.yMin = 2 * yExtremals[0];
    this.yMax = 2 * yExtremals[1];
  }

  /**
   * Set the abscissa and ordinate values and their 
   * extremal values respectively
   * 
   * @param x the abscissa values
   * @param y the ordinate values
   * @throws IllegalArgumentException 
   */
  public void setValues(double[] x, double[] y) {
    if (x.length != y.length) {
      throw new IllegalArgumentException("x and y must be the same size! x: "
              + x.length + " y: " + y.length);
    }
    this.x = x;
    this.y = y;

    double[] xExtremals = getExtremals(x);
    if (this.xMin == null) {
      this.xMin = xExtremals[0];
      this.xMax = xExtremals[1];
    }

    double[] yExtremals = getExtremals(y);
    if (this.yMin == null) {
      this.yMin = 2 * yExtremals[0];
      this.yMax = 2 * yExtremals[1];
    }

    repaint();
  }

  /**
   * Sets the maximum x-value
   * @param xMax the maximum x-value to set
   */
  private void setxMax(Double xMax) {
    this.xMax = xMax;
  }

  /**
   * @return the maximum x-value
   */
  private Double getxMax() {
    return xMax;
  }

  /**
   * Sets the minimum x-value
   * @param xMin the minimum x-value to set
   */
  private void setxMin(Double xMin) {
    this.xMin = xMin;
  }

  /**
   * @return the minimum x-value
   */
  private Double getxMin() {
    return xMin;
  }

  /**
   * Sets the maximum y-value
   * @param yMax the maximum y-value to set
   */
  private void setyMax(Double yMax) {
    this.yMax = yMax;
  }

  /**
   * @return the maximum y-value
   */
  private Double getyMax() {
    return yMax;
  }

  /**
   * Sets the minimum y-value
   * @param yMin the minimum y-value to set
   */
  private void setyMin(Double yMin) {
    this.yMin = yMin;
  }

  /**
   * @return the minimum y-value
   */
  private Double getyMin() {
    return yMin;
  }
}