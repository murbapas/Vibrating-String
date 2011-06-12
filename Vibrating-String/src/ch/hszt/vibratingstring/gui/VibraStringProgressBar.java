/*
 * VibraStringProgressBar.java (Created on Jun 12, 2011, 7:30:50 PM)
 * 
 * @author Farhan Fayyaz (fafa at ten.ch)
 * 
 * TODO add description
 * This class is ..
 */
package ch.hszt.vibratingstring.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JProgressBar;

/**
 * A {@code VibraStringProgressBar}.
 *
 * @author Farhan Fayyaz
 */
@SuppressWarnings("serial")
public class VibraStringProgressBar extends JProgressBar
        implements ActionListener, Runnable {

  /**
   * Creates a new instance of {@code VibraString}
   * @param min the min value
   * @param max the max value
   */
  public VibraStringProgressBar(int min, int max) {
    super(min, max);
  }

  @Override
  public void run() {
    while (true) {
      // wait for the signal from the GUI
      try {
        synchronized (this) {
          wait();
        }
      } catch (InterruptedException e) {
      }
      // simulate some long-running process like parsing a large file
      for (int i = 0; i <= 100; i++) {
        System.out.println(i);
        this.setValue(i);
        System.out.println("actionPerformed sets jpb value to: " + i);
        try {
          Thread.sleep(50);
        } // make the process last a while
        catch (InterruptedException e) {
        }
      }
    }
  }

  @Override
  public void actionPerformed(ActionEvent ae) {
    // signal the worker thread to get crackin
    synchronized (this) {
      notifyAll();
    }
  }
}