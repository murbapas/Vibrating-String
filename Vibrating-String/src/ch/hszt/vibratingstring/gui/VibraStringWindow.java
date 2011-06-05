/*
 * VibraStringWindow.java (Created on May 22, 2011, 1:08:12 AM)
 *
 * @author Farhan Fayyaz / Pascal Murbach
 *
 * The main window
 */
package ch.hszt.vibratingstring.gui;

import ch.hszt.vibratingstring.gui.VibraStringWindow.CalculatorThread;
import ch.hszt.vibratingstring.logic.VibraString;
import ch.hszt.vibratingstring.logic.function.IMathFunction;
import ch.hszt.vibratingstring.logic.function.TriaFunction;
import ch.hszt.vibratingstring.logic.function.ZeroFunction;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * A {@code VibraStringWindow}.
 *
 * @author Farhan Fayyaz / Pascal Murbach
 */
@SuppressWarnings("serial")
public class VibraStringWindow extends JFrame {

  /**
   * The vibrating string
   */
  private VibraString vibraString;
  /**
   * The settings panel
   */
  private JPanel settingsP;
  /**
   * The panel with the plotted graph
   */
  private GraphPlotPanel vibraStringP;
  /**
   * The start/stop button
   */
  private JButton startB;
  /**
   * Screen resolution
   */
  private final Dimension DIM = getDefaultToolkit().getScreenSize();
  /**
   * The ratio between screen and windows height respectively
   */
  private final double RATIO = 0.75;
  /**
   * The calculator thread
   */
  private volatile Thread calculatorThread;
  /**
   * The graph updater thread
   */
  private volatile Thread graphUpdaterThread;

  /**
   * Creates a new instance of {@code VibraStringWindow}
   */
  public VibraStringWindow() {
    configure();
    createGUI();
    initVibraString();
  }

  /**
   * Set some basic constraints
   */
  private void configure() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setSize(DIM.width, (int) (DIM.height * RATIO));
    this.setTitle(" Vibrating String");
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
    setUpVibraStringPanel(contentPane);
    setUpSettingsPanel(contentPane);
  }

  /**
   * Sets up the settings panel
   * @param contentPane the content pain of this window
   */
  private void setUpSettingsPanel(Container contentPane) {
    settingsP = new JPanel();
    settingsP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    settingsP.setPreferredSize(new Dimension(this.getSize().width, (int) (this.getSize().height * 0.2)));
    settingsP.setLayout(new BorderLayout());

    startB = createStartB();
    settingsP.add(startB);

    contentPane.add(settingsP, BorderLayout.NORTH);
  }

  /**
   * @return the start button
   */
  private JButton createStartB() {
    final String startS = "Start";
    final String stopS = "Stop";
    JButton button = new JButton(startS);

    button.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (btn.getText().equals(startS)) {
          startThreads();
          btn.setText(stopS);
        } else {
          stopThreads();
          btn.setText(startS);
        }
      }
    });

    return button;
  }

  /**
   * Sets up the vibrating string panel
   * @param contentPane the content pain of this window
   */
  private void setUpVibraStringPanel(Container contentPane) {
    vibraStringP = new GraphPlotPanel();
    vibraStringP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    contentPane.add(vibraStringP, BorderLayout.CENTER);
  }

  /**
   * A {@code GraphUpdaterThread}.
   *
   * The {@code Runnable} which waits for the newly
   * calculated y-values
   *
   * @author Farhan Fayyaz
   */
  public class GraphUpdaterThread extends Thread {

    /**
     * The x-values
     */
    private double[] x;
    /**
     * The y-values
     */
    private final double[] y;

    /**
     * Creates a new instance of {@code GraphUpdaterThread}
     * @param x the x-values
     * @param y the y-values
     */
    public GraphUpdaterThread(double[] x, double[] y) {
      this.x = x;
      this.y = y;
    }

    @Override
    public void run() {
      while (graphUpdaterThread != null) {
        synchronized (y) {
          try {
            y.wait();
          } catch (InterruptedException ex) {
            //ex.printStackTrace();
          }
          vibraStringP.setValues(x, y);
        }
      }
    }
  }

  /**
   * A {@code CalculatorThread}.
   *
   * The {@code Runnable} which waits for the newly
   * calculated y-values
   *
   * @author Pascal Murbach
   */
  public class CalculatorThread extends Thread {

    /**
     * The vibrating string
     */
    //private VibraString vibraString;
    /**
     * The x-values
     */
    private double[] x;
    /**
     * The y-values
     */
    private double[] y;
    /**
     * The speed constant
     */
    //private double c;
    /**
     * The length of the vibrating string
     */
    private double length;
    /**
     * The count of pairs of (x/y)-values
     */
    private int pairOfValueCnt;
    /**
     * TODO add doc
     */
    //private int slices;
    /**
     * TODO add doc
     */
    private double[][] yt;

    /**
     * TODO add doc
     */
    private double tau;
    /**
     * TODO add doc
     */
    private double timePrec;
    /**
     * TODO add doc
     */
    private double tDecay = 0;
    /**
     * Creates a new instance of {@code VibraStringWindow}.
     * @param x the x-values
     * @param y the y-values
     * @param vibraString the vibrating string
     */
    public CalculatorThread(double[] x, double[] y) {
      this.x = x;
      this.y = y;
      //this.vibraString = vibraString;
      //this.c = vibraString.getC();
      length = vibraString.getLength();
      pairOfValueCnt = y.length;
      //slices = vibraString.getSlices();
      yt = vibraString.getYt();
      tau = 2.0d / 0.1d;
      timePrec = vibraString.getTimePrec();
      tDecay = 0;

    }

    @Override
    public void run() {
      while (calculatorThread != null) {        
        for (int t = 0; t < vibraString.getSlices(); t++) {

          try {
            Thread.sleep(10);
          } catch (InterruptedException e) {
            //e.printStackTrace();
          }

          synchronized (y) {
            for (int i = 0; i < pairOfValueCnt; i++) {
              y[i] = vibraString.getYt()[t][i] * Math.exp((-tDecay * timePrec / tau));//yt[t][i];
                //y = vibraString.getyTWithDecay();              
//              yt[t][i] *= Math.exp((-tDecay * timePrec) / tau);
              tDecay += 1;
              System.out.println(y[i]);
            }
            y.notify();

            // stop threads if all y-values are 0.0
            int zeroCnt = 0;
            for (int i = 0; i < pairOfValueCnt; i++) {
              if (Math.abs(y[i]) <= Math.pow(10, -17)) {
                zeroCnt += 1;
              }
            }
            if (zeroCnt == y.length) {
              stopThreads();
              startB.setText("Start");
            }
          }
        }
      }
    }
  }

  /**
   * Starts the calculator and graph updater threads
   */
  private void startThreads() {
    vibraString.resetYt();
    calculatorThread = new CalculatorThread(vibraString.getxGrid(), vibraString.getyStart());
    graphUpdaterThread = new GraphUpdaterThread(vibraString.getxGrid(), vibraString.getyStart());
    calculatorThread.start();
    graphUpdaterThread.start();
  }

  /**
   * Stops the calculator and graph updater threads
   */
  private void stopThreads() {
    calculatorThread = null;
    graphUpdaterThread = null;
  }

  private void initVibraString() {
    IMathFunction f = new TriaFunction();
    IMathFunction g = new ZeroFunction();

    vibraString = new VibraString(8, 60.0d, f, g, 0.1d, 0.001d, 10);
    double[] x = vibraString.getxGrid();
    double[] y = vibraString.getyStart();

    vibraStringP.setX(x);
    vibraStringP.setY(y);
  }
}
