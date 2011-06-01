/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hszt.vibratingstring.logic;

import ch.hszt.vibratingstring.gui.VibraStringWindow;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pascal
 */
public class Main {



  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
       



            /* praparing the string */
            VibraString string = new VibraString();
//            string.setLength(6);
//
            double[] x = string.getXGrid();
            final double[] y = string.getYStart();
            System.out.println(string.toString());
//
//            IMathFunction startfp = new TriaFunction();
//            y = startfp.calc(x);
            

            VibraStringWindow mainWindow = new VibraStringWindow(x, y);
            mainWindow.setVisible(true);


            

            //final double[] yStart = {0.0, 0.0998, 0.1987, 0.2955, 0.3894, 0.4794, 0.5646, 0.6442, 0.7174, 0.7833, 0.8415, 0.8912, 0.9320, 0.9636, 0.9854, 0.9975, 0.9996, 0.9917, 0.9738, 0.9463, 0.9093, 0.8632, 0.8085, 0.7457, 0.6755, 0.5985, 0.5155, 0.4274, 0.3350, 0.2392, 0.1411, 0.0416, -0.0584, -0.1577, -0.2555, -0.3508, -0.4425, -0.5298, -0.6119, -0.6878, -0.7568, -0.8183, -0.8716, -0.9162, -0.9516, -0.9775, -0.9937, -0.9999, -0.9962, -0.9825, -0.9589, -0.9258, -0.8835, -0.8323, -0.7728, -0.7055, -0.6313, -0.5507, -0.4646, -0.3739, -0.2794, -0.1822, -0.0831};
            //final double[] yStart = y;
            Calculator calc = new Calculator(x, y, string);
            VibraStringWindow.GraphThread graph = mainWindow.new GraphThread(y, x);


            Thread calculatorThread = new Thread(calc);
            Thread graphThread = new Thread(graph);

            calculatorThread.start();
            graphThread.start();

  }
}
