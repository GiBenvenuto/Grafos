/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

/**
 *
 * @author Gustavoo
 */
public class No implements Comparable<No>{
    private int vertice;
    private int valoradj;
    private No prox;
    
    public No(int vertice, int valoradj){
        this.prox = null;
        this.vertice = vertice;
        this.valoradj = valoradj;
    }

       

    public int getVertice() {
        return vertice;
    }

    public int getValoradj() {
        return valoradj;
    }

    public No getProx() {
        return prox;
    }
    
    public void setProx(No prox){
        this.prox = prox;
    }

    @Override
    public int compareTo(No t) {
            if(this.valoradj < t.getValoradj()) return 1;
            else return -1;    }

  
}
