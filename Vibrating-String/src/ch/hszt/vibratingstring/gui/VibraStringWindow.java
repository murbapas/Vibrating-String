/*
 * VibraStringWindow.java (Created on May 22, 2011, 1:08:12 AM)
 *
 * @author Farhan Fayyaz (fafa at ten.ch)
 *
 * The main window
 */
package ch.hszt.vibratingstring.gui;

import ch.hszt.vibratingstring.gui.VibraStringWindow.Calculator;
import ch.hszt.vibratingstring.logic.VibraString;
import ch.hszt.vibratingstring.logic.function.IMathFunction;
import ch.hszt.vibratingstring.logic.function.SquareFunction;
import ch.hszt.vibratingstring.logic.function.ZeroFunction;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * A {@code VibraStringWindow}.
 *
 * @author Farhan Fayyaz
 */
@SuppressWarnings("serial")
public class VibraStringWindow extends JFrame {

  /**
   * The vibrating string
   */
  VibraString vibraString;
  /**
   * The settings panel
   */
  private JPanel settingsP;
  /**
   * The panel with the plotted graph
   */
  private GraphPlotPanel stringP;
  /**
   * Screen resolution
   */
  private final Dimension DIM = getDefaultToolkit().getScreenSize();
  /**
   * The ratio between screen and windows height respectively
   */
  private final double RATIO = 0.95;
  private Thread calculatorThread;
  private Thread graphUpdaterThread;

  /**
   * Creates a new instance of {@code VibraStringWindow}
   */
  public VibraStringWindow() {
    configure();
    createGUI();
  }

  /**
   * Set some basic constraints
   */
  private void configure() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setSize(DIM.width, (int) (DIM.height * RATIO));
    this.setTitle("Vibrating String");
  }

  /**
   * Creates the GUI
   */
  private void createGUI() {
    setUpPanels();
  }

  /**
   * Set up the panels
   */
  private void setUpPanels() {
    Container contentPane = this.getContentPane();
    settingsP = new JPanel();
    settingsP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    settingsP.setPreferredSize(new Dimension(this.getSize().width, (int) (this.getSize().height * 0.2)));
    stringP = new GraphPlotPanel();
    stringP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

    IMathFunction f = new SquareFunction();
    IMathFunction g = new ZeroFunction();
    vibraString = new VibraString(2, 160.0d, f, g);

    double precision = 0.1d;
    int l = (int) (vibraString.getLength() / precision);
    double[] x = new double[l];
    double step = 0.0d;
    for (int i = 0; i < l; i++) {
      x[i] = new BigDecimal(step).setScale(1, RoundingMode.UP).doubleValue();
      //System.out.println(x[i]);
      step += precision;
    }
    stringP.setX(x);
    double[] y = f.calc(x);
    stringP.setY(y);

    contentPane.add(settingsP, BorderLayout.NORTH);
    contentPane.add(stringP, BorderLayout.CENTER);

    Calculator calculator = new Calculator(x, y, vibraString);
    GraphUpdater graphUpdater = new GraphUpdater(x, y);
    calculatorThread = new Thread(calculator);
    graphUpdaterThread = new Thread(graphUpdater);

    startThreads();
  }

  /**
   * A {@code GraphUpdater}.
   * 
   * The {@code Runnable} which waits for the newly
   * calculated y-values
   *
   * @author Farhan Fayyaz
   */
  public class GraphUpdater implements Runnable {

    /**
     * The x-values
     */
    private double[] x;
    /**
     * The y-values
     */
    private final double[] y;

    /**
     * Creates a new instance of {@code GraphUpdater}
     * @param x the x-values
     * @param y the y-values
     */
    public GraphUpdater(double[] x, double[] y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public void run() {
      while (true) {
        synchronized (y) {
          try {
            y.wait();
          } catch (InterruptedException ex) {
            ex.printStackTrace();
          }
          stringP.setValues(x, y);
        }
      }
    }
  }

  /**
   * A {@code Calculator}.
   * 
   * The {@code Runnable} which waits for the newly
   * calculated y-values
   *
   * @author Pascal Murbach
   */
  public class Calculator implements Runnable {

    /**
     * The x-values
     */
    private double[] x;
    /**
     * The y-values
     */
    private final double[] y;
    /**
     * The vibrating string
     */
    private VibraString vibraString;
    /**
     * The speed constant
     */
    private double c;
    /**
     * The length of the vibrating string
     */
    private int length;
    /**
     * The count of pairs of (x/y)-values
     */
    private int pairOfValueCnt;

    /**
     * Creates a new instance of {@code VibraStringWindow}.
     * @param x the x-values
     * @param y the y-values
     * @param vibraString the vibrating string
     */
    public Calculator(double[] x, double[] y, VibraString vibraString) {
      this.x = x;
      this.y = y;
      this.vibraString = vibraString;
      this.c = vibraString.getC();
      length = vibraString.getLength();
      pairOfValueCnt = y.length;
    }

    public void run() {
      while (true) {
        for (double t = 0; t < 100; t += 0.0001) {
          try {
            Thread.sleep(20);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }

          synchronized (y) {
            for (int i = 0; i < pairOfValueCnt; i++) {

              y[i] = 0.0;
              for (int n = 1; n < 10; n++) {
                y[i] += (vibraString.fourierCoeff(x, y, n) * Math.cos(c * n * Math.PI * t / length)
                        + vibraString.fourierCoeffST(n) * Math.sin(c * n * Math.PI * t / length))
                        * Math.sin(n * Math.PI * x[i] / length);
              }
            }
            y.notify();
          }
        }
      }
    }
  }

  private void startThreads() {
    calculatorThread.start();
    graphUpdaterThread.start();
  }

  private void stopThreads() {
    calculatorThread.interrupt();
    graphUpdaterThread.interrupt();
  }
}