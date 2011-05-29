/*
 * GraphPlotPanel.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Farhan Fayyaz (fafa at ten.ch)
 * 
 * TODO add description
 * This class is ..
 */
package ch.hszt.vibratingstring.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;
import javax.swing.JPanel;

/**
 * A {@code GraphPlotPanel.
 *
 * @author Farhan Fayyaz
 */
@SuppressWarnings("serial")
public class GraphPlotPanel extends JPanel {

  /**
   * abscissa-values
   */
  private double[] x = {0, 0.1000, 0.2000, 0.3000, 0.4000, 0.5000, 0.6000, 0.7000, 0.8000, 0.9000, 1.0000, 1.1000, 1.2000, 1.3000, 1.4000, 1.5000, 1.6000, 1.7000, 1.8000, 1.9000, 2.0000, 2.1000, 2.2000, 2.3000, 2.4000, 2.5000, 2.6000, 2.7000, 2.8000, 2.9000, 3.0000, 3.1000, 3.2000, 3.3000, 3.4000, 3.5000, 3.6000, 3.7000, 3.8000, 3.9000, 4.0000, 4.1000, 4.2000, 4.3000, 4.4000, 4.5000, 4.6000, 4.7000, 4.8000, 4.9000, 5.0000, 5.1000, 5.2000, 5.3000, 5.4000, 5.5000, 5.6000, 5.7000, 5.8000, 5.9000, 6.0000, 6.1000, 6.2000};
  /**
   * The smallest abscissa value
   */
  private double xMin;
  /**
   * The biggest abscissa value
   */
  private double xMax;
  /**
   * ordinate-values
   */
  private double[] y;
  /**
   * The smallest ordinate value
   */
  private double yMin;
  /**
   * The biggest ordinate value
   */
  private double yMax;
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
      System.out.println("x" + i + " " + x1);
      double y1 = ((h - PAD) / 2) - (yScale * y[i]);
      System.out.println("y" + i + " " + y1);
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
    
    double[] xExtremals = getExtremals(x);
    double[] yExtremals = getExtremals(y);
    
    this.xMin = xExtremals[0];
    this.xMax = xExtremals[1];
    this.yMin = yExtremals[0];
    this.yMax = yExtremals[1];
    this.x = x;
    this.y = y;
    
    repaint();
  }
}
