/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

/**
 *
 * @author Gi Benvenuto
 */
public class Aresta implements Comparable<Aresta>{
        private int v1;
        private int v2;
        private int valor;

        public Aresta(int v1, int v2, int valor) {
            this.v1 = v1;
            this.v2 = v2;
            this.valor = valor;
        }
        
        public int getV1() {
            return v1;
        }

        public int getV2() {
            return v2;
        }

        public int getValor() {
            return valor;
        }
        
        public int compareTo(Aresta a){
            if(this.valor > a.getValor()) return 1;
            else return -1;
        }
    }
