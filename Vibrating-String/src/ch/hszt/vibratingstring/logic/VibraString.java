/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.hszt.vibratingstring.logic;

import java.util.Observable;

/**
 *
 * @author pascal
 */
public class VibraString extends Observable {

    private int length;
    private double c;
    private IMathFunction f;
    private IMathFunction g;

    public VibraString() {
      
    }

    /**
     * @return the length
     */
    public int getLength() {

        return length;
    }

    /**
     * @param length the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the c
     */
    public double getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(double c) {
        this.c = c;
    }

    /**
     * @return the f
     */
    public IMathFunction getF() {
        return f;
    }

    /**
     * @param f the f to set
     */
    public void setF(IMathFunction f) {
        this.f = f;
    }

    /**
     * @return the g
     */
    public IMathFunction getG() {
        return g;
    }

    /**
     * @param g the g to set
     */
    public void setG(IMathFunction g) {
        this.g = g;
    }

//    public void updateGui (){
//    setChanged();
//    notifyObservers(testY);
//    }
}
