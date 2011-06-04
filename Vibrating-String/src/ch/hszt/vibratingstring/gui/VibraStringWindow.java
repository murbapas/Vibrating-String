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
import ch.hszt.vibratingstring.logic.function.SawToothFunction;
import ch.hszt.vibratingstring.logic.function.SquareFunction;
import ch.hszt.vibratingstring.logic.function.TriaFunction;
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
    /**
     * The calculator thread
     */
    private Thread calculatorThread;
    /**
     * The graph updater thread
     */
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

        IMathFunction f = new SawToothFunction();
        IMathFunction g = new ZeroFunction();

        vibraString = new VibraString(2, 250.0d, f, g, 0.1d, 0.0001d, 10);

        double[] x = vibraString.getxGrid();
        double[] y = vibraString.getyStart();

        stringP.setX(x);
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
        private double length;
        /**
         * The count of pairs of (x/y)-values
         */
        private int pairOfValueCnt;

        private int slices;

        private double[][] yt;

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
            slices = vibraString.getSlices();
            yt = vibraString.getYt();

        }

        public void run() {
            while (true) {
                for (int t = 0; t < slices; t++) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (y) {
                        for (int i = 0; i < pairOfValueCnt; i++) {

                            y[i] = yt[t][i];
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
