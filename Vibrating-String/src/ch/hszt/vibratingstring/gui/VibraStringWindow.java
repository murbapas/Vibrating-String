/*
 * VibraStringWindow.java (Created on May 22, 2011, 1:08:12 AM)
 *
 * @author Farhan Fayyaz / Pascal Murbach
 *
 * The main window
 */
package ch.hszt.vibratingstring.gui;

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
  private Thread calculatorThread;
  /**
   * The graph updater thread
   */
  private Thread graphUpdaterThread;
  //private Thread soundThread;
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
   * decay text field
   */
  private JTextField decayT;
  /**
   * decay precision text field
   */
  private JTextField tauT;
  /**
   * The progress bar to show 
   * how far the calculation has gone
   * yet
   */
  public static VibraStringProgress progressBar;

  /**
   * Creates a new instance of {@code VibraStringWindow}
   */
  public VibraStringWindow() {
    configure();
    createGUI();
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

  /**
   * Set some basic constraints
   */
  private void configure() {
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(true);
    this.setSize(DIM.width, (int) (DIM.height * RATIO));
    this.setTitle("Vibrating String Java");
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
    settingsP.setPreferredSize(new Dimension(this.getSize().width,
            (int) (this.getSize().height * 0.06)));
    settingsP.setLayout(new BorderLayout());

    JPanel leftP = new JPanel();
    leftP.setLayout(new BorderLayout());
    leftP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));
    leftP.setPreferredSize(new Dimension(200, 100));

    progressBar = new VibraStringProgress(0, 100);
    progressBar.setValue(0);
    progressBar.setStringPainted(true);

    startB = createStartB();

    leftP.add(startB, BorderLayout.NORTH);
    leftP.add(progressBar, BorderLayout.CENTER);

    JPanel centerP = new JPanel();
    centerP.setLayout(new GridLayout(1, 1, 5, 0));
    centerP.setBorder(new EtchedBorder(EtchedBorder.LOWERED));

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
    JLabel decayL = new JLabel("Decay");
    JLabel tauL = new JLabel("DecayPrec");

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
    decayT = new JTextField("0");
    tauT = new JTextField("20");

    JPanel fP = new JPanel(new BorderLayout());
    fP.add(startFunctionL, BorderLayout.NORTH);
    fP.add(startFunctionCB, BorderLayout.CENTER);
    centerP.add(fP);

    JPanel gP = new JPanel(new BorderLayout());
    gP.add(secondFunctionL, BorderLayout.NORTH);
    gP.add(secondFunctionCB, BorderLayout.CENTER);
    centerP.add(gP);

    JPanel stringLengthP = new JPanel(new BorderLayout());
    stringLengthP.add(stringLengthL, BorderLayout.NORTH);
    stringLengthP.add(stringLengthT, BorderLayout.CENTER);
    centerP.add(stringLengthP);

    JPanel speedP = new JPanel(new BorderLayout());
    speedP.add(speedL, BorderLayout.NORTH);
    speedP.add(speedT, BorderLayout.CENTER);
    centerP.add(speedP);

    JPanel precisionP = new JPanel(new BorderLayout());
    precisionP.add(precisionL, BorderLayout.NORTH);
    precisionP.add(precisionT, BorderLayout.CENTER);
    centerP.add(precisionP);

    JPanel timePrecisionP = new JPanel(new BorderLayout());
    timePrecisionP.add(timePrecisionL, BorderLayout.NORTH);
    timePrecisionP.add(timePrecisionT, BorderLayout.CENTER);
    centerP.add(timePrecisionP);

    JPanel harmonicCompP = new JPanel(new BorderLayout());
    harmonicCompP.add(harmonicCompL, BorderLayout.NORTH);
    harmonicCompP.add(harmonicCompT, BorderLayout.CENTER);
    centerP.add(harmonicCompP);

    JPanel decayP = new JPanel(new BorderLayout());
    decayP.add(decayL, BorderLayout.NORTH);
    decayP.add(decayT, BorderLayout.CENTER);
    centerP.add(decayP);

    JPanel tauP = new JPanel(new BorderLayout());
    tauP.add(tauL, BorderLayout.NORTH);
    tauP.add(tauT, BorderLayout.CENTER);
    centerP.add(tauP);

    settingsP.add(leftP, BorderLayout.WEST);
    settingsP.add(centerP, BorderLayout.CENTER);

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
          try {
            vibraString.setLength(
                    Double.parseDouble(stringLengthT.getText().trim()));
            vibraString.setC(
                    Double.parseDouble(speedT.getText().trim()));
            vibraString.setF(
                    (IMathFunction) startFunctionCB.getSelectedItem());
            vibraString.setG(
                    (IMathFunction) secondFunctionCB.getSelectedItem());
            vibraString.setTimePrec(
                    Double.parseDouble(timePrecisionT.getText().trim()));
            vibraString.setHarmonicComp(
                    Integer.parseInt(harmonicCompT.getText().trim()));
            vibraString.calcXGrid(
                    Double.parseDouble(stringLengthT.getText().trim()),
                    Double.parseDouble(precisionT.getText().trim()));
            vibraString.calcYStart(vibraString.getF(), vibraString.getxGrid());

            new Thread(progressBar).start();

            vibraString.calcStringMovement();

            vibraString.resetYt();
            calculatorThread = new CalculatorThread(vibraString.getxGrid(),
                    vibraString.getyStart(),
                    Double.parseDouble(tauT.getText().trim()),
                    Integer.parseInt(decayT.getText().trim()));
            graphUpdaterThread = new GraphUpdaterThread(
                    vibraString.getyStart());
            //soundThread = new SoundThread(vibraString.getyStart());
            calculatorThread.start();
            graphUpdaterThread.start();
            //soundThread.start();

            btn.setText(stopS);

          } catch (Exception ex) {
            new JOptionPane("Form data invalid",
                    JOptionPane.ERROR_MESSAGE).setVisible(true);
          }
        } else {
          calculatorThread.interrupt();
          calculatorThread = null;
          graphUpdaterThread.interrupt();
          graphUpdaterThread = null;
          //soundThread.interrupt();
          //soundThread = null;
          progressBar.setValue(0);

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
  private class GraphUpdaterThread extends Thread {

    /**
     * The y-values
     */
    private final double[] y;

    /**
     * Creates a new instance of {@code GraphUpdaterThread}
     * @param y the y-values
     */
    GraphUpdaterThread(double[] y) {
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
  private class CalculatorThread extends Thread {

    /**
     * The y-values
     */
    private final double[] y;
    /**
     * The decay precision
     */
    private double tau;
    /**
     * The time precision
     */
    private double timePrec;
    /**
     * The decay
     */
    private int tDecay;

    /**
     * Creates a new instance of {@code VibraStringWindow}.
     * @param x the x-values
     * @param y the y-values
     * @param tau precision of decay
     * @param tDecay decay strength
     */
    CalculatorThread(double[] x, double[] y, double tau, int tDecay) {
      this.y = y;
      this.tau = tau;
      this.timePrec = vibraString.getTimePrec();
      this.tDecay = tDecay;
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
              y[i] = vibraString.getYt()[t][i]
                      * Math.exp((-tDecay * timePrec / tau));
              tDecay += 1;
              //System.out.println(y[i]);
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