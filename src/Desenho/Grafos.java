/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Desenho;

import Cores.EscalaCores;
import java.util.ArrayList;

/**
 *
 * @author Gi Benvenuto
 */
public class Grafos {
    protected ArrayList<Vertices> vert = new ArrayList<Vertices>();
    protected ArrayList<Arestas> arest = new ArrayList<Arestas>();
    
    public Grafos(int nVert) {
        EscalaCores cS = new EscalaCores();
        //GrayScale cS = new GrayScale();
        int colorStep = 255 / nVert;
        for (int i=0; i<nVert; i++){
            Vertices v = new Vertices();
            v.setID(i);
            v.setColor(cS.getColor(i*colorStep));
           // v.setColor(Color.RED);
//            if (i % 2 == 0){
//                v.setSelected(false);
//            }
            this.vert.add(v);
        }
        computeCircledPosition(200);
    }

    public void addVertex(Vertices v){
        this.vert.add(v);
    }

    public void addEdge(Arestas e){
        this.arest.add(e);
    }

    public void computeCircledPosition(int ray){
        int nVert = this.vert.size();
        int step = 360 / nVert;
        int deslocX = 100 + ray;
        int deslocY = 100 + ray;
        for (int i=0; i<nVert; i++){
            double ang = i * step;
            ang = ang * Math.PI / 180;//necessario em radianos
            float X = (float) Math.cos(ang);
            float Y = (float) Math.sin(ang);
            X = X * ray + deslocX;
            Y = Y * ray + deslocY;
            this.vert.get(i).setX(X);
            this.vert.get(i).setY(Y);
        }
    }

    public ArrayList<Vertices> getVertex() {
        return this.vert;
    }
    
    public ArrayList<Arestas> getAresta(){
        return this.arest;
    }

    public void draw(java.awt.Graphics2D g2) {
       //Draw each edges of the graph
        for (Arestas edge : arest) {
            edge.draw(g2);
        }
        //Draw each vertice of the graph
        for (Vertices v : this.vert) {
            v.draw(g2);
        }
    }

    public java.awt.Dimension getSize() {
        if (this.vert.size() > 0) {
            float maxX = vert.get(0).getX();
            float minX = vert.get(0).getX();
            float maxY = vert.get(0).getY();
            float minY = vert.get(0).getY();

            //Encontra o maior e menor valores para X e Y
            for (Vertices v : this.vert) {
                if (maxX < v.getX()) {
                    maxX = v.getX();
                } else {
                    if (minX > v.getX()) {
                        minX = v.getX();
                    }
                }

                if (maxY < v.getY()) {
                    maxY = v.getY();
                } else {
                    if (minY > v.getY()) {
                        minY = v.getY();
                    }
                }
            }

            int w = (int) (maxX + (this.vert.get(0).getRay() * 5)) + 350;
            int h = (int) (maxY + (this.vert.get(0).getRay() * 5));

            return new java.awt.Dimension(w, h);
        } else {
            return new java.awt.Dimension(0, 0);
        }
    }    
}
