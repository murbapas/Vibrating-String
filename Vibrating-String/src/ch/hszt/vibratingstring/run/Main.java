/*
 * Main.java (Created on May 22, 2011, 1:07:25 AM)
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 * 
 * This is the main class, from which the program is started
 */
package ch.hszt.vibratingstring.run;

import ch.hszt.vibratingstring.gui.VibraStringWindow;

/**
 * A {@code Main}.
 * 
 * @author Pascal Murbach / Farhan Fayyaz
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {   
    VibraStringWindow mainWindow = new VibraStringWindow();
    mainWindow.setVisible(true);
  }
}
