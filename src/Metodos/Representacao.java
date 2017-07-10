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
public abstract class Representacao {
    protected int numVert = 0;
    
    public int getNumVertices(){
        return numVert;
    }
    public abstract void init(int qtdvertices, boolean estrutura);
    public abstract void addAresta(int v1, int v2, int valor);
    public abstract void imprimeRepresentacao();
    public abstract int verificaAdjacencia (int v1, int v2);
}
