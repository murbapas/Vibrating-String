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
       // try {
            VibraStringWindow mainWindow = new VibraStringWindow();
            mainWindow.setVisible(true);
            // Double[] values;
            final double[] y1 = {0.0, 0.0998, 0.1987, 0.2955, 0.3894, 0.4794, 0.5646, 0.6442, 0.7174, 0.7833, 0.8415, 0.8912, 0.9320, 0.9636, 0.9854, 0.9975, 0.9996, 0.9917, 0.9738, 0.9463, 0.9093, 0.8632, 0.8085, 0.7457, 0.6755, 0.5985, 0.5155, 0.4274, 0.3350, 0.2392, 0.1411, 0.0416, -0.0584, -0.1577, -0.2555, -0.3508, -0.4425, -0.5298, -0.6119, -0.6878, -0.7568, -0.8183, -0.8716, -0.9162, -0.9516, -0.9775, -0.9937, -0.9999, -0.9962, -0.9825, -0.9589, -0.9258, -0.8835, -0.8323, -0.7728, -0.7055, -0.6313, -0.5507, -0.4646, -0.3739, -0.2794, -0.1822, -0.0831};
            //    Double[] y2 = {1.0000, 0.9950, 0.9801, 0.9553, 0.9211, 0.8776, 0.8253, 0.7648, 0.6967, 0.6216, 0.5403, 0.4536, 0.3624, 0.2675, 0.1700, 0.0707, -0.0292, -0.1288, -0.2272, -0.3233, -0.4161, -0.5048, -0.5885, -0.6663, -0.7374, -0.8011, -0.8569, -0.9041, -0.9422, -0.9710, -0.9900, -0.9991, -0.9983, -0.9875, -0.9668, -0.9365, -0.8968, -0.8481, -0.7910, -0.7259, -0.6536, -0.5748, -0.4903, -0.4008, -0.3073, -0.2108, -0.1122, -0.0124, 0.0875, 0.1865, 0.2837, 0.3780, 0.4685, 0.5544, 0.6347, 0.7087, 0.7756, 0.8347, 0.8855, 0.9275, 0.9602, 0.9833, 0.9965};
            //    List<Double[]> values = new ArrayList<Double[]>();
            //    values.add(y2);
            Calculator calc = new Calculator(y1);
            VibraStringWindow.GraphThread graph = mainWindow.new GraphThread(y1);
//            Class<VibraStringWindow.GraphThread> clazz = VibraStringWindow.GraphThread.class;
//            Constructor<VibraStringWindow.GraphThread> constructor = clazz.getConstructor(VibraStringWindow.class);
//            VibraStringWindow.GraphThread graph = constructor.newInstance(mainWindow);
//        } catch (NoSuchMethodException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (SecurityException ex) {
//            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }

            Thread calculatorThread = new Thread(calc);
            Thread graphThread = new Thread(graph);

            calculatorThread.start();
            graphThread.start();

  }
}
