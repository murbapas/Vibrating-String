/*
 * GraphPlotPanel.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Farhan Fayyaz (fafa at ten.ch)
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
 * @author Farhan Fayyaz
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

    int w = getWidth();
    int h = getHeight();
    // space between abscissa values calulated with the panel width
    double xScale = (w - 2 * PAD) / (xMax - xMin);
    // space between ordinate values calculated with the panel height
    double yScale = (h - 2 * PAD) / (yMax - yMin);

    // Draw abcissa.
    g2.draw(new Line2D.Double(PAD, (h - PAD) / 2, w - PAD, (h - PAD) / 2));
    // Draw ordinate
    g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));

    // Connect the data points with lines
    g2.setPaint(Color.blue);
    //System.out.println("hier" + y[1]);
    for (int i = 0; i < x.length - 1; i++) {
      double x1 = (PAD + (xScale * x[i]));
      //System.out.println("x" + i + " " + x1);
      double y1 = ((h - PAD) / 2) - (yScale * y[i]);
      //System.out.println("y" + i + " " + y1);
      double x2 = (PAD + (xScale * x[i + 1]));
      double y2 = ((h - PAD) / 2) - (yScale * y[i + 1]);
      g2.draw(new Line2D.Double(x1, y1, x2, y2));
    }
  }

  /**
   * @param d the array of doubles to find the extremals
   * @return the extremal values of x and y
   */
  private double[] getExtremals(double[] d) {
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
   * Set the abscissa and ordinate values and their 
   * extremal values respectively
   * 
   * @param x the abscissa values
   * @param y the ordinate values
   * @throws IllegalArgumentException 
   */
  public void setValues(double[] x, double[] y) {
    if (x.length != y.length) {
      throw new IllegalArgumentException("x and y must be the same size! x: " + x.length + " y: " + y.length);
    }

    this.x = x;
    this.y = y;

    repaint();
  }

  public void setX(double[] x) {
    this.x = x;
    double[] xExtremals = getExtremals(x);
    if (this.xMin == null) {
      this.xMin = xExtremals[0];
      this.xMax = xExtremals[1];
    }

  }

  public void setY(double[] y) {
    this.y = y;
    double[] yExtremals = getExtremals(y);
    if (this.yMin == null) {
      this.yMin = 2 * yExtremals[0];
      this.yMax = 2 * yExtremals[1];
    }
  }

  /**
   * Sets the extremal values of x and y in order
   * to let the panel scale its dimensions accordingly
   * @param x the x-values
   * @param y the y-values
   */
  private void setExtremals(double[] x, double[] y) {
    double[] xExtremals = getExtremals(x);
    double[] yExtremals = getExtremals(y);
    this.xMin = xExtremals[0];
    this.xMax = xExtremals[1];
    this.yMin = 2 * yExtremals[0];
    this.yMax = 2 * yExtremals[1];
  }
}
