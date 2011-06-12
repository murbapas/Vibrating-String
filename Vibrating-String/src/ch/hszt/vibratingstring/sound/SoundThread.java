/*
 * Sound.java (Created on Jun 12, 2011, 1:44:41 AM)
 * 
 * @author Farhan Fayyaz (fafa at ten.ch)
 * 
 * TODO add description
 * This class is ..
 */
package ch.hszt.vibratingstring.sound;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;

import java.io.ByteArrayOutputStream;
import javax.sound.sampled.AudioInputStream;

/**
 * A {@code Sound}.
 *
 * @author Farhan Fayyaz
 */
public class SoundThread extends Thread {

  float sampleRate = 8000.0F;
  //8000,11025,16000,22050,44100
  int sampleSizeInBits = 16;
  //8,16
  int channels = 1;
  //1,2
  boolean signed = true;
  //true,false
  boolean bigEndian = false;
  double[] y;
  //byte tempBuffer[] = new byte[10000];
  ByteArrayOutputStream byteArrayOutputStream;
  AudioInputStream audioInputStream;
  SourceDataLine sourceDataLine;
  AudioFormat audioFormat;

  /**
   * Creates a new instance of {@code Sound}.
   */
  public SoundThread(double[] y) {
    this.y = y;
  }

  @Override
  public void run() {
    while (this != null) {
      synchronized (y) {
        try {
          y.wait();
        } catch (InterruptedException ex) {
          //ex.printStackTrace();
        }

      }
    }
  }
}