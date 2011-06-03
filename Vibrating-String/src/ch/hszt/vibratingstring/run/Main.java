/*
 * Main.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach
 * 
 * This is the main class, from which the program is started
 */
package ch.hszt.vibratingstring.run;

import ch.hszt.vibratingstring.gui.VibraStringWindow;

/**
 * @author Pascal Murbach
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {



    // show the window
    VibraStringWindow mainWindow = new VibraStringWindow();
    mainWindow.setVisible(true);

    // set up calculator and graph
//    VibraStringWindow.Calculator runCalc = mainWindow.new Calculator(x, y, vibraString);
//    VibraStringWindow.GraphPlotter runGraph = mainWindow.new GraphPlotter(x, y);

    // finally set up and start the threads
//    Thread calculatorThread = new Thread(runCalc);
//    Thread graphThread = new Thread(runGraph);
//    calculatorThread.start();
//    graphThread.start();

  }
}
