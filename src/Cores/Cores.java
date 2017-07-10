/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cores;

import java.awt.Color;

/**
 *
 * @author Gi Benvenuto
 */
public abstract class Cores {
    
    public abstract String getName();

    public Color getColor(int index) {
        if (index > 255) {
            index = 255;
        }
        if (index <= 0) {
            index = 0;
        }
        return colors[index];
    }

    protected java.awt.Color[] colors;
}
