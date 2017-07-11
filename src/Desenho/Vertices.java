/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Desenho;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Gi Benvenuto
 */
public class Vertices {
    private float x;
    private float y;
    private int ray = 8;
    private Boolean selected = true;
    private Color color = Color.RED;
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void draw(java.awt.Graphics2D g2) {
        if (this.selected) {
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f)); // Transparencia
            g2.setStroke(new java.awt.BasicStroke(1.0f)); //Contorno
        } else { //not selected and there is not a global vertex selected
            g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 0.2f));
            g2.setStroke(new java.awt.BasicStroke(1.5f));
        }
        
        

        g2.setColor(this.color);        
        g2.fillOval(((int) this.x) - this.getRay(), ((int) this.y)
                - this.getRay(), this.getRay() * 2, this.getRay() * 2);

        g2.setColor(Color.BLACK);
        g2.drawOval(((int) this.x) - this.getRay(), ((int) this.y)
                - this.getRay(), this.getRay() * 2, this.getRay() * 2);
        
        //drawText(g2, new Point((int) this.x, (int) this.x), new Point((int) this.y, (int) this.y), String.valueOf(this.ID), 0);

        g2.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, 1.0f));

    }
    
      private void drawText(Graphics2D g2, Point s, Point t, String text, int deslocamento) {
        float r = (float) Math.sqrt(Math.pow(s.x - t.x, 2) + Math.pow(s.y - t.y, 2));
        float cos = (t.x - s.x) / r;
        float sen = (t.y - s.y) / r;

        Point pc = new Point(Math.round(deslocamento * -cos) + t.x, Math.round(deslocamento * -sen) + t.y);

        g2.setFont(new Font(("Verdana"), Font.BOLD, 14));
        if (this.color != Color.BLACK) {
            g2.setColor(java.awt.Color.BLACK);
        } else {
            g2.setColor(java.awt.Color.WHITE);
        }
        g2.drawString(text, pc.x, pc.y);
    }

    public float getX() {
        return x;
    }

    public void setX(float X) {
        this.x = X;
    }

    public float getY() {
        return y;
    }

    public void setY(float Y) {
        this.y = Y;
    }

    public int getRay() {
        return ray;
    }

    public void setRay(int ray) {
        this.ray = ray;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Boolean isSelected() {
        return selected;
    }

    public void setSelected(Boolean flag) {
        this.selected = flag;
    }
    
}
