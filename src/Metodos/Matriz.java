/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Gustavoo
 */
public class Matriz extends Representacao{
    private int mprincipal[][];
    private int maux[][];
    private boolean estrutura;
    private int qtdvertices;
    private int tempo;
    private int[] val;

    public Matriz() {
    }
    
    
    @Override
    public void init(int qtdvertices, boolean estrutura) {
        this.qtdvertices = qtdvertices;
        this.estrutura = estrutura;
        mprincipal = new int[qtdvertices][qtdvertices];
        maux = new int[qtdvertices][3];
        tempo = 0;
        val = new int[qtdvertices];
        inicializaMatriz();
    }

    @Override
    public void addAresta(int v1, int v2, int valor) {
        if (estrutura == true) {
            mprincipal[v1][v2] = valor;
            mprincipal[v2][v1] = valor;
        } else {
            mprincipal[v1][v2] = valor;
        }
    }

    @Override
    public void imprimeRepresentacao() {
        String matriz = "";
        for (int i = 0; i < qtdvertices; i++) {
            for (int j = 0; j < qtdvertices; j++) {
                matriz += mprincipal[i][j] + " ";
            }
            matriz += "\n";
        }
        System.out.println(matriz);
    }
    
    public void inicializaMatriz(){
        for(int i = 0; i < qtdvertices; i++){
            for(int j = 0; j < qtdvertices; j++) 
                mprincipal[i][j] = Integer.MAX_VALUE;
        }
    }
     
    public boolean getEstrutura() {
        return estrutura;
    }

    public int getQtdVertices() {
        return qtdvertices;
    }

/****************************Representação Matriz******************************/    
    
    public int verificaAdjacencia(int v1, int v2){
        if(mprincipal[v1][v2] != Integer.MAX_VALUE) return mprincipal[v1][v2];
        else return Integer.MAX_VALUE;
    }
    
    public void inicializaMatrizBusca(){
        
        for(int i=0; i<qtdvertices; i++)
            for(int j=0; j<3; j++)
                maux[i][j] = 0;
    }
    
    
/***********************************Buscas*************************************/    
/****************************Busca Em Profundidade*****************************/    
    
    public int[][] buscaProfundidade(int noRaiz){
        inicializaMatrizBusca();
        tempo = 0;
        buscaVisita(noRaiz);
        
        for(int i=0; i<qtdvertices; i++){
            if(maux[i][0] == 0)
                buscaVisita(i);
        }
 
        return maux;
    }
    
    public void buscaVisita(int vertice){
        tempo += 1;
        maux[vertice][0] = tempo; //tempo de inicio
        
        for(int i=0; i<qtdvertices; i++){
            if(mprincipal[vertice][i] != Integer.MAX_VALUE)
                if(maux[i][0] == 0) {
                    maux[i][2] = vertice;
                    buscaVisita(i);
                } 
        }
        tempo += 1;
        maux[vertice][1] = tempo; //tempo de fim
    }
    
/***********************************Exibir*************************************/    
        
    public void exibirAux(){
        String matriz = "";
        for(int i = 0; i < qtdvertices; i++){
            for(int j = 0; j < 3; j++) 
                matriz += maux[i][j]+" ";
            matriz += "\n";
        }
        System.out.println(matriz);
    }
    
    
/****************************Busca Em Largura**********************************/    

    
    public ArrayList buscaLargura (int vert){
        int u, j, i = 0;
        int max = Integer.MAX_VALUE;
        int num = qtdvertices;
        ArrayList<Integer>listavertices = new ArrayList();     
        
        for (i = 0; i < num; i++){
            if (i != vert){
                maux[i][0] = 0;   //coluna 0 = cor ; 0 = branco,1 = cinza, 2 = preto;
                maux[i][1] = max; //coluna 1 = distancia;
                maux[i][2] = max; //coluna 2 = pai;
            }            
        }
        maux[vert][0] = 1;
        maux[vert][1] = 0;
        maux[vert][2] = max;
        
       listavertices.add(vert);
        
        while (listavertices.isEmpty() != true){
            u = (int)listavertices.get(0);
            listavertices.remove(0);            
       
            for (j = 0; j < num; j++){
                if (mprincipal[u][j] != max){ //adjacente de u
                    if (maux[j][0] == 0){ // se for branco
                        maux[j][0] = 1; //cinza
                        maux[j][1] = maux[u][1] + 1;
                        maux[j][2] = u;
                        listavertices.add(j);
                    }//if branco
                }//if adjacente  
            }//for adjacencias
            maux[u][0] = 2;
        }
        
        return recuperaCaminho(vert);
    }
    
    
    public ArrayList recuperaCaminho(int vertice){
        ArrayList<String> caminhos = new ArrayList();
        StringBuilder aux = new StringBuilder();
        StringBuilder num = new StringBuilder();
        int cont = 0;
        int i, pai;
        
        for(i=0; i<qtdvertices; i++){
            
            if (i != vertice) {
                pai = maux[i][2];
                if (pai < qtdvertices) {
                    num.append(i);
                    num.reverse();
                    aux.append(num);
                    aux.append(" - ");
                    num.delete(0,num.length());
                    while (pai != vertice) {
                        num.append(pai);
                        num.reverse();
                        aux.append(num);
                        aux.append(" - ");
                        num.delete(0, num.length());
                        pai = maux[pai][2];
                        if (pai > qtdvertices) {
                            break;
                        }
                    }
                    num.append(vertice);
                    num.reverse();
                    aux.append(num);
                    aux.reverse();
                    num.delete(0, num.length());
                    caminhos.add(aux.toString());
                    aux.delete(0, aux.length());
                    val[cont] = maux[i][1];
                    cont++;
                }
            }
        }
        return caminhos;
    }
    
    public int[] recuperDistanciaLargura(){
              
        return val;
    }
    
    
/****************************Ávore Geradora Mínima*****************************/    
    
/****************************Funções Auxiliares********************************/    
    public void inicializaMatrizAGM(){
        for(int i=0; i<qtdvertices; i++){
            for(int j=0; j<3; j++)
                maux[i][j] = Integer.MAX_VALUE;
        }
    }
    
    public int excluiMinimo(){
        int min = Integer.MAX_VALUE, pos = Integer.MAX_VALUE;
        for(int i = 0; i<qtdvertices; i++)
            if(maux[i][1] < min)
                if(maux[i][0] != 1){ 
                    min = maux[i][1]; pos = i;
                }

        return pos;
    }
    
/*********************************PRIM*****************************************/
    
    public ArrayList AGM_Prim(int vertice){
        inicializaMatrizAGM();
        maux[vertice][1] = 0; //Distancia
        
        while(maux[vertice][0] == Integer.MAX_VALUE){
            maux[vertice][0] = 1; //Pertence a solução
            
            for(int j=0; j<qtdvertices; j++)
                if(mprincipal[vertice][j] != Integer.MAX_VALUE && maux[j][0] != 1){
                    if(maux[j][1] > mprincipal[vertice][j]){
                        maux[j][1] = mprincipal[vertice][j];
                        maux[j][2] = vertice; //Pai
                    }
                }
            
            vertice = excluiMinimo();
            if(vertice == Integer.MAX_VALUE)
                break;
        }
        
        return recuperaCaminhoMinimo();
        
    }
    
    public ArrayList recuperaCaminhoMinimo (){
        ArrayList<Aresta> aresta = new ArrayList();
        Aresta aux;
        
        for(int i= 0; i< qtdvertices; i++){
            if (maux[i][2] != Integer.MAX_VALUE){
                aux = new Aresta(i, maux[i][2], maux[i][1]);
                aresta.add(aux);
            }
        }
        return aresta;
    }
    
/********************************KRUSKAL***************************************/
    public ArrayList Kruskal(){
        ArrayList<Aresta> arestas = new ArrayList();
        Aresta aux;
        int cont;
        //Ordenando as arestas e o conjunto dos vértices
        for(int i=0; i<qtdvertices; i++){
            for(int j=0; j<qtdvertices; j++){
                if(mprincipal[i][j] != Integer.MAX_VALUE){
                    aux = new Aresta(i, j, mprincipal[i][j]);
                    arestas.add(aux);
                }
                
            }
            maux[i][0] = i;
        }
        Collections.sort(arestas);
        
        for(int i=0; i<arestas.size(); i++){
            aux = (Aresta)arestas.get(i);
            if(maux[aux.getV1()][0] != maux[aux.getV2()][0]){
                cont = 0;
                while(cont<qtdvertices){
                    if(maux[cont][0] == maux[aux.getV2()][0] && cont != aux.getV2())
                        maux[cont][0] = maux[aux.getV1()][0];
                    cont++;
                }
                maux[aux.getV2()][0] = maux[aux.getV1()][0];
            }
            else arestas.remove(i--);
        }
       
        return arestas;
    }
    
/***************************Caminho Mínimo*************************************/    
/********************************DIJKSTRA**************************************/    
    
    public ArrayList caminhaMinimoDijkstra(int vertice){
        int original = vertice;
        inicializaMatrizAGM();
        maux[vertice][1] = 0;//valor
        
        while(maux[vertice][0] == Integer.MAX_VALUE){
            maux[vertice][0] = 1; //pertence ao caminho
            
            for(int j=0; j<qtdvertices; j++)
                if(mprincipal[vertice][j] != Integer.MAX_VALUE){
                    if(maux[j][1] > mprincipal[vertice][j]+maux[vertice][1]){
                        maux[j][1] = mprincipal[vertice][j]+maux[vertice][1];
                        maux[j][2] = vertice; //pai
                    }
                }
            
            vertice = excluiMinimo();
            if(vertice == Integer.MAX_VALUE)
                break;
        }
        return recuperaCaminho(original);
    }
   
    
/*****************************BELLMANFORD**************************************/    
    
    public boolean caminhoMinimoBellmanFord(int vertice){
        inicializaMatrizAGM();
        int i, j, k;
        maux[vertice][1] = 0; //distancia
        
        for (k = 0; k < qtdvertices-1; k++){
            
            for(i= 0; i< qtdvertices; i++){
                for(j= 0; j< qtdvertices; j++){
                    if(mprincipal[i][j] != Integer.MAX_VALUE && maux[i][1] != Integer.MAX_VALUE){              
                        if(maux[j][1] > mprincipal[i][j] + maux[i][1]){
                            maux[j][1] = mprincipal[i][j]+maux[i][1];
                            maux[j][2] = i; //pai
                        }
                    }
                    
                }
            }
        }
        
        for(i= 0; i< qtdvertices; i++){
            for(j= 0; j< qtdvertices; j++){
                    if(mprincipal[i][j] != Integer.MAX_VALUE){
                        if(maux[j][1] > mprincipal[i][j] + maux[i][1]){
                            return false;
                        }
                    }
                }
            }
        return true;
    }
    
    public ArrayList recuperaCaminhosBellmanFord(int vertice){
        return recuperaCaminho(vertice);
    }
    
/********************************CAMINHO***************************************/    

    public String caminhoProfundidade(int u, int v){
        inicializaMatrizBusca();
        tempo = 0;
        int pai;
        StringBuilder caminho = new StringBuilder();
        StringBuilder num = new StringBuilder();
        
        buscaVisita(u);
        if(maux[v][1] == 0) return null;
        else{
            pai = v;
            while(pai != u){
                num.append(pai);
                num.reverse();
                caminho.append(num);
                caminho.append(" - ");
                num.delete(0, num.length());
                pai = maux[pai][2];
            }
            num.append(u);
            num.reverse();
            caminho.append(num);
            num.delete(0, num.length());
        }
        caminho.reverse();
        return caminho.toString();
    }
    
    public String caminhoLargura(int u, int v){
        buscaLargura(u);
        int pai;
        StringBuilder caminho = new StringBuilder();
        StringBuilder num = new StringBuilder();
        
        if(maux[v][0] == 0) return null;
        else{
            pai = v;
            while(pai != u){
                num.append(pai);
                num.reverse();
                caminho.append(num);
                caminho.append(" - ");
                num.delete(0, num.length());
                pai = maux[pai][2];
            }
            num.append(u);
            num.reverse();
            caminho.append(num);
            num.delete(0, num.length());
        }
        caminho.reverse();
        //System.out.println(caminho.toString());
        return caminho.toString();
    }
    
/********************************CONEXO****************************************/    
    
    public int[][] verificaConexo(){
        if(estrutura == false) return null;
        else{
            inicializaMatrizBusca();
            tempo = 0;
            for(int i=0; i<qtdvertices; i++)
                if(maux[i][0] == 0)
                    buscaVisitaCaminho(i, i+1);
        }
        exibirAux();
        return maux;
        
    }
    
    public void buscaVisitaCaminho(int vertice, int grupo){
        tempo += 1;
        maux[vertice][0] = tempo;
        maux[vertice][2] = grupo;
        
        for(int i=0; i<qtdvertices; i++){
            if(mprincipal[vertice][i] != Integer.MAX_VALUE){
                if(maux[i][0] == 0){
                    maux[i][2] = grupo;
                    buscaVisitaCaminho(i, grupo);
                }
            }
        }
        
        tempo += 1;
        maux[vertice][1] = tempo;
    }

    
}
