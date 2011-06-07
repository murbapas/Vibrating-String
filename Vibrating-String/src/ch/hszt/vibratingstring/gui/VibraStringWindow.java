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
import ch.hszt.vibratingstring.logic.function.RandomFunction;
import ch.hszt.vibratingstring.logic.function.SawToothFunction;
import ch.hszt.vibratingstring.logic.function.SinFunction;
import ch.hszt.vibratingstring.logic.function.SquareFunction;
import ch.hszt.vibratingstring.logic.function.TriaFunction;
import ch.hszt.vibratingstring.logic.function.ZeroFunction;
import static java.awt.Toolkit.getDefaultToolkit;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
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
   * The start/stop button
   */
  private JButton startB;
  /**
   * Start function combo box
   */
  private JComboBox startFunctionCB;
  /**
   * 2nd function combo box
   */
  private JComboBox secondFunctionCB;
  /**
   * Vibrating string length text field
   */
  private JTextField stringLengthT;
  /**
   * Vibrating string speed text field
   */
  private JTextField speedT;
  /**
   * x-grid precision text field
   */
  private JTextField precisionT;
  /**
   * time precision text field
   */
  private JTextField timePrecisionT;
  /**
   * harmonic component text field
   */
  private JTextField harmonicCompT;

  /**
   * Creates a new instance of {@code VibraStringWindow}
   */
  public VibraStringWindow() {
    configure();
    createGUI();
    try {
      vibraString = new VibraString(
              Double.parseDouble(stringLengthT.getText().trim()),
              Double.parseDouble(speedT.getText().trim()),
              (IMathFunction) startFunctionCB.getSelectedItem(),
              (IMathFunction) secondFunctionCB.getSelectedItem(),
              Double.parseDouble(precisionT.getText().trim()),
              Double.parseDouble(timePrecisionT.getText().trim()),
              Integer.parseInt(harmonicCompT.getText().trim()));
      vibraString.calcXGrid(
              Double.parseDouble(stringLengthT.getText().trim()),
              Double.parseDouble(precisionT.getText().trim()));
      vibraString.calcYStart(vibraString.getF(), vibraString.getxGrid());

      vibraStringP.setValues(vibraString.getxGrid(), vibraString.getyStart());
    } catch (Exception e) {
      new JOptionPane("Form data invalid", JOptionPane.ERROR_MESSAGE).setVisible(true);
    }
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
    settingsP.setPreferredSize(new Dimension(this.getSize().width, (int) (this.getSize().height * 0.05)));
    settingsP.setLayout(new BorderLayout());

    JPanel leftP = new JPanel();
    leftP.setLayout(new BorderLayout());
    leftP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    leftP.setPreferredSize(new Dimension(200, 100));
    startB = createStartB();
    leftP.add(startB);

    JPanel rightP = new JPanel();
    rightP.setLayout(new GridLayout());

    IMathFunction[] functionList = {
      new SquareFunction(),
      new TriaFunction(),
      new RandomFunction(),
      new SawToothFunction(),
      new SinFunction(),
      new ZeroFunction()
    };

    JLabel startFunctionL = new JLabel("F");
    JLabel secondFunctionL = new JLabel("G");
    JLabel stringLengthL = new JLabel("L");
    JLabel speedL = new JLabel("C");
    JLabel precisionL = new JLabel("XPrec");
    JLabel timePrecisionL = new JLabel("Tprec");
    JLabel harmonicCompL = new JLabel("Harmonic");

    startFunctionCB = new JComboBox(functionList);
    startFunctionCB.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        vibraString = new VibraString(
                Double.parseDouble(stringLengthT.getText().trim()),
                Double.parseDouble(speedT.getText().trim()),
                (IMathFunction) startFunctionCB.getSelectedItem(),
                (IMathFunction) secondFunctionCB.getSelectedItem(),
                Double.parseDouble(precisionT.getText().trim()),
                Double.parseDouble(timePrecisionT.getText().trim()),
                Integer.parseInt(harmonicCompT.getText().trim()));
        vibraString.calcXGrid(
                Double.parseDouble(stringLengthT.getText().trim()),
                Double.parseDouble(precisionT.getText().trim()));
        vibraString.calcYStart(vibraString.getF(), vibraString.getxGrid());
        vibraStringP.setValues(vibraString.getxGrid(), vibraString.getyStart());
      }
    });

    secondFunctionCB = new JComboBox(functionList);
    secondFunctionCB.setSelectedIndex(secondFunctionCB.getItemCount() - 1);
    stringLengthT = new JTextField("2");
    speedT = new JTextField("40");
    precisionT = new JTextField("0.1");
    timePrecisionT = new JTextField("0.001");
    harmonicCompT = new JTextField("10");

    rightP.add(startFunctionL);
    rightP.add(startFunctionCB);
    rightP.add(secondFunctionL);
    rightP.add(secondFunctionCB);
    rightP.add(stringLengthL);
    rightP.add(stringLengthT);
    rightP.add(speedL);
    rightP.add(speedT);
    rightP.add(precisionL);
    rightP.add(precisionT);
    rightP.add(timePrecisionL);
    rightP.add(timePrecisionT);
    rightP.add(harmonicCompL);
    rightP.add(harmonicCompT);

    settingsP.add(leftP, BorderLayout.WEST);
    settingsP.add(rightP, BorderLayout.CENTER);

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

//          vibraString = new VibraString(
//                  Double.parseDouble(stringLengthT.getText().trim()),
//                  Double.parseDouble(speedT.getText().trim()),
//                  (IMathFunction) startFunctionCB.getSelectedItem(),
//                  (IMathFunction) secondFunctionCB.getSelectedItem(),
//                  Double.parseDouble(precisionT.getText().trim()),
//                  Double.parseDouble(timePrecisionT.getText().trim()),
//                  Integer.parseInt(harmonicCompT.getText().trim()));
//          vibraString.calcXGrid(
//                  Double.parseDouble(stringLengthT.getText().trim()),
//                  Double.parseDouble(precisionT.getText().trim()));
//          vibraString.calcYStart(vibraString.getF(), vibraString.getxGrid());

          vibraString.setLength(Double.parseDouble(stringLengthT.getText().trim()));
          vibraString.setC(Double.parseDouble(speedT.getText().trim()));
          vibraString.setF((IMathFunction) startFunctionCB.getSelectedItem());
          vibraString.setG((IMathFunction) secondFunctionCB.getSelectedItem());
          vibraString.setTimePrec(Double.parseDouble(timePrecisionT.getText().trim()));
          vibraString.setHarmonicComp(Integer.parseInt(harmonicCompT.getText().trim()));          
          vibraString.calcXGrid(
                  Double.parseDouble(stringLengthT.getText().trim()),
                  Double.parseDouble(precisionT.getText().trim()));
          vibraString.calcYStart(vibraString.getF(), vibraString.getxGrid());
          vibraString.calcStringMovement();

          vibraString.resetYt();
          calculatorThread = new CalculatorThread(vibraString.getxGrid(), vibraString.getyStart());
          graphUpdaterThread = new GraphUpdaterThread(vibraString.getyStart());
          calculatorThread.start();
          graphUpdaterThread.start();
          btn.setText(stopS);
        } else {
          calculatorThread = null;
          graphUpdaterThread = null;
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
     * The y-values
     */
    private final double[] y;

    /**
     * Creates a new instance of {@code GraphUpdaterThread}
     * @param y the y-values
     */
    public GraphUpdaterThread(double[] y) {
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
          vibraStringP.setValues(vibraString.getxGrid(), y);
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
     * The y-values
     */
    private double[] y;
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
    private int tDecay = 0;

    /**
     * Creates a new instance of {@code VibraStringWindow}.
     * @param x the x-values
     * @param y the y-values
     */
    public CalculatorThread(double[] x, double[] y) {
      this.y = y;
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
            for (int i = 0; i < y.length; i++) {
              y[i] = vibraString.getYt()[t][i] * Math.exp((-tDecay * timePrec / tau));
              tDecay += 1;
              System.out.println(y[i]);
            }

            // stop threads if all y-values are 0.0
            int zeroCnt = 0;
            for (int i = 0; i < y.length; i++) {
              if (Math.abs(y[i]) <= Math.pow(10, -17)) {
                zeroCnt += 1;
              }
            }
            if (zeroCnt == y.length) {
              calculatorThread = null;
              graphUpdaterThread = null;
              startB.setText("Start");
            }

            y.notify();
          }
        }
      }
    }
  }
}
