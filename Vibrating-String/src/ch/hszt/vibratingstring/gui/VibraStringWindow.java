/*
 * VibraStringWindow.java (Created on May 22, 2011, 1:08:12 AM)
 * 
 * @author Farhan Fayyaz (fafa at ten.ch)
 * 
 * TODO add description
 * This class is ..
 */
package ch.hszt.vibratingstring.gui;

import ch.hszt.vibratingstring.logic.VibraString;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

/**
 * A {@code VibraStringWindow.
 *
 * @author Farhan Fayyaz
 */
@SuppressWarnings("serial")
public class VibraStringWindow extends JFrame {

  /**
   * The settings panel
   */
  private JPanel settingsP = new JPanel();
  /**
   * The panel with the plotted graph
   */
  private GraphPlotPanel stringP = new GraphPlotPanel();
  /**
   * Screen resolution
   */
  private final Dimension dim = getDefaultToolkit().getScreenSize();
  /**
   * The vibrating string
   */
  private VibraString vibraString = new VibraString();
  /**
   * The thread for the calculations
   */
  private CalculationThread thread;

  /**
   * Creates a new instance of {@code MainWindow}
   */
  public VibraStringWindow() {
    configure();
    createGUI();
    init();
  }

  /**
   * Set some basic constraints
   */
  private void configure() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setSize(dim.width, (int) (dim.height * 0.95));
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

    settingsP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    settingsP.setPreferredSize(new Dimension(this.getSize().width, (int) (this.getSize().height * 0.2)));
    stringP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

    contentPane.add(settingsP, BorderLayout.NORTH);
    contentPane.add(stringP, BorderLayout.CENTER);
  }

  /**
   * Initiate the {@code CalculationThread}
   */
  private void init() {
    thread = new CalculationThread();
    start();
  }

  /**
   * Start the {@code CalculationThread}
   */
  private void start() {
    thread.start();
  }

  /**
   * 
   */
  private class CalculationThread extends Thread {   

    @Override
    public void run() {
      while (true) {
    //double[] x = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
    //double[] y = {2, -4, 6,- 8, 10, -12, 14, -16, 18, -20, 22,-24, 26, -28, 30, -32, 34, -36, 38, -40, 42};
    //double[] y = {0, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22,24, 26, 28, 30, 32, 34, 36, 38, 40, 42};
        double[] x = {0, 0.1000, 0.2000, 0.3000, 0.4000, 0.5000, 0.6000, 0.7000, 0.8000, 0.9000, 1.0000, 1.1000, 1.2000, 1.3000, 1.4000, 1.5000, 1.6000, 1.7000, 1.8000, 1.9000, 2.0000, 2.1000, 2.2000, 2.3000, 2.4000, 2.5000, 2.6000, 2.7000, 2.8000, 2.9000, 3.0000, 3.1000, 3.2000, 3.3000, 3.4000, 3.5000, 3.6000, 3.7000, 3.8000, 3.9000, 4.0000, 4.1000, 4.2000, 4.3000, 4.4000, 4.5000, 4.6000, 4.7000, 4.8000, 4.9000, 5.0000, 5.1000, 5.2000, 5.3000, 5.4000, 5.5000, 5.6000, 5.7000, 5.8000, 5.9000, 6.0000, 6.1000, 6.2000};
        double[] y = {0, 0.0998, 0.1987, 0.2955, 0.3894, 0.4794, 0.5646, 0.6442, 0.7174, 0.7833, 0.8415, 0.8912, 0.9320, 0.9636, 0.9854, 0.9975, 0.9996, 0.9917, 0.9738, 0.9463, 0.9093, 0.8632, 0.8085, 0.7457, 0.6755, 0.5985, 0.5155, 0.4274, 0.3350, 0.2392, 0.1411, 0.0416, -0.0584, -0.1577, -0.2555, -0.3508, -0.4425, -0.5298, -0.6119, -0.6878, -0.7568, -0.8183, -0.8716, -0.9162, -0.9516, -0.9775, -0.9937, -0.9999, -0.9962, -0.9825, -0.9589, -0.9258, -0.8835, -0.8323, -0.7728, -0.7055, -0.6313, -0.5507, -0.4646, -0.3739, -0.2794, -0.1822, -0.0831};
    //stringP.setData(x, y);   
        
//        double[] x = {0, 0.1000, 0.2000, 0.3000, 0.4000, 0.5000, 0.6000, 0.7000, 0.8000, 0.9000, 1.0000, 1.1000, 1.2000, 1.3000, 1.4000, 1.5000, 1.6000, 1.7000, 1.8000, 1.9000, 2.0000, 2.1000, 2.2000, 2.3000, 2.4000, 2.5000, 2.6000, 2.7000, 2.8000, 2.9000, 3.0000, 3.1000, 3.2000, 3.3000, 3.4000, 3.5000, 3.6000, 3.7000, 3.8000, 3.9000, 4.0000, 4.1000, 4.2000, 4.3000, 4.4000, 4.5000, 4.6000, 4.7000, 4.8000, 4.9000, 5.0000, 5.1000, 5.2000, 5.3000, 5.4000, 5.5000, 5.6000, 5.7000, 5.8000, 5.9000, 6.0000, 6.1000, 6.2000};
//        double[] y = new double[x.length];
//        for (int i = 0; i < y.length; i++) {
//          y[i] = Math.random()*Math.sin(x[i]);
//        }
        stringP.setValues(x, y);
      }
    }
  }
}
