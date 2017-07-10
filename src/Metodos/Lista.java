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
public class Lista extends Representacao{
    private boolean estrutura;
    private No lista[];
    private int qtdvertices;
    private int maux[][];
    private int tempo;
    private int[] val;
    
    
     @Override
    public void init(int qtdvertices, boolean estrutura) {
        this.estrutura = estrutura;
        this.qtdvertices = qtdvertices;
        lista = new No[qtdvertices];
        tempo = 0;
        maux = new int[qtdvertices][3];
        val = new int[qtdvertices];
        inicializaLista();
    }

    @Override
    public void addAresta(int v1, int v2, int valor) {
        No novo;
        novo = new No(v2, valor);
        //uma adjacencia vai ser feita independente da estrutura
        if (lista[v1].getProx() == null) {
            lista[v1].setProx(novo);
        } else {
            No aux = lista[v1];
            while (aux.getProx() != null) {
                aux = aux.getProx();
            }
            aux.setProx(novo);
        }
        //se for grafo, precisa adicionar outr adjacencia
        if (estrutura == true) {
            novo = new No(v1, valor);
            if (lista[v2].getProx() == null) {
                lista[v2].setProx(novo);
            } else {
                No aux = lista[v2];
                while (aux.getProx() != null) {
                    aux = aux.getProx();
                }
                aux.setProx(novo);
            }
        }
    }

    @Override
    public void imprimeRepresentacao() {
         String msg = "";
        No aux;
        
        for(int i = 0; i<qtdvertices; i++){
            msg += lista[i].getVertice();
            msg += "    ";
            aux = lista[i].getProx();
            while(aux != null){
                msg += aux.getVertice()  + "(" + aux.getValoradj() + ")" + "  ";
                aux = aux.getProx();
            }
            msg += "\n";
        }
        
        System.out.println(msg);
    }
     
    @Override
    public int verificaAdjacencia (int v1, int v2){
        No l = lista[v1];
        while (l.getProx() != null){
            l = l.getProx();
            if (l.getVertice() == v2){
                return l.getValoradj();
            }
            
        }
        return Integer.MAX_VALUE;
    }

   
    public void inicializaLista(){
        for(int i = 0; i < qtdvertices; i++)
            lista[i] = new No(i, 0);
    }

    public boolean getEstrutura() {
        return estrutura;
    }

    public int getQrdvertices() {
        return qtdvertices;
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
  
/***********************************Buscas*************************************/    
/****************************Busca em Profundidade*****************************/     
    
    public void inicializaMatrizBusca(){
        for(int i=0; i<qtdvertices; i++)
            for(int j=0; j<3; j++)
                maux[i][j] = 0;
    }
    
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
        maux[vertice][0] = tempo;
        No aux = lista[vertice].getProx();
        
        while (aux != null) {
            if (maux[aux.getVertice()][0] == 0){
                maux[aux.getVertice()][2] = vertice;
                buscaVisita(aux.getVertice());
            }
            else aux = aux.getProx();
        }
        
        tempo += 1;
        maux[vertice][1] = tempo;
    }
     
    
/****************************Busca em Largura**********************************/  
        
    public ArrayList buscaLargura (int vert){
       
        int u, j, i = 0;
        int max = Integer.MAX_VALUE;
        int num = qtdvertices;
        No l;
        ArrayList<Integer>listavertices = new ArrayList();     
        
        for (i = 0; i < num; i++){
            if (i != vert){
                maux[i][0] = 0;   //coluna 0 = cor ; 0 = branco,1 = cinza, 2 = preto;
                maux[i][1] = max; //coluna 1 = distancia
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
       
            l = lista[u];
            while (l.getProx() != null){ //adjacente de u
                j = l.getProx().getVertice();
                    if (maux[j][0] == 0){ // se for branco
                        maux[j][0] = 1; //cinza
                        maux[j][1] = maux[u][1] + 1;
                        maux[j][2] = u;
                        listavertices.add(j);
                    }//if branco
                     l = l.getProx();
                }//while adjacente  

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
    
    public ArrayList AGM_Prim(int vertice){
        No l;
        inicializaMatrizAGM();
        int j= 0;
        maux[vertice][1] = 0;
        
        while(maux[vertice][0] == Integer.MAX_VALUE){
            maux[vertice][0] = 1;
            
            l = lista[vertice].getProx();
            while(l != null){               
                j = l.getVertice();
                    if(maux[j][1] > l.getValoradj()  &&  maux[j][0] != 1){
                        maux[j][1] = l.getValoradj();
                        maux[j][2] = vertice;
                    }
                 l = l.getProx();    
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
    
      public ArrayList Kruskal(){
        ArrayList<Aresta> arestas = new ArrayList();
        Aresta aux;
        No l;
        int cont, j;
        //Ordenando as arestas e o conjunto dos vértices
        for(int i=0; i<qtdvertices; i++){
            l = lista[i].getProx();
            while(l != null){
                    j = l.getVertice();
                    aux = new Aresta(i, j, l.getValoradj());
                    arestas.add(aux);  
                    l = l.getProx();
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
            else  arestas.remove(i--);
        }
      
        return arestas;
    }
    
    
    public ArrayList caminhaMinimoDijkstra(int vertice){
        inicializaMatrizAGM();
        int j, original = vertice;
        No l;
        maux[vertice][1] = 0;
        
        while(maux[vertice][0] == Integer.MAX_VALUE){
            maux[vertice][0] = 1;
            l = lista[vertice];
            while(l.getProx() != null){
                l = l.getProx();
                j = l.getVertice();
                    if(maux[j][1] > l.getValoradj() + maux[vertice][1]){
                        maux[j][1] = l.getValoradj() + maux[vertice][1];
                        maux[j][2] = vertice;
                    }
                }
            
            vertice = excluiMinimo();
            if(vertice == Integer.MAX_VALUE)
                break;
        }
        return recuperaCaminho(original);
    }
    
    public boolean caminhoMinimoBellmanFord(int vertice){
        inicializaMatrizAGM();
        int i, j, k;
        No l;
        maux[vertice][1] = 0;
        
        for (k = 0; k < qtdvertices-1; k++){
            
            for(i= 0; i< qtdvertices; i++){
                l = lista[i];
                while(l.getProx() != null){
                    l = l.getProx();
                    if(maux[i][1] != Integer.MAX_VALUE){ 
                        j = l.getVertice();
                        if(maux[j][1] > l.getValoradj() + maux[i][1]){
                            maux[j][1] = l.getValoradj() + maux[i][1];
                            maux[j][2] = i;
                        }
                    }
                    
                }
            }
        }
        
        for(i= 0; i< qtdvertices; i++){
            l = lista[i];
            while(l.getProx() != null){
                l = l.getProx();
                j = l.getVertice();
                    if(maux[j][1] > l.getValoradj() + maux[i][1]){
                        return false;
                    }
                }
            }
        
        
        return true;
        
        
    }
    
    public ArrayList recuperaCaminhosBellmanFord(int vertice){
        return recuperaCaminho(vertice);
    }
    
    /********************************CAMINH
     * @param u
     * @param v
     * @param uO*    
     * @return **************************************/    

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
        
        for (int i = 0; i < qtdvertices; i++) {
            No aux = lista[vertice].getProx();

            while (aux != null) {
                if (maux[aux.getVertice()][0] == 0) {
                    maux[aux.getVertice()][2] = grupo;
                    buscaVisitaCaminho(aux.getVertice(), grupo);
                } else {
                    aux = aux.getProx();
                }
            }

        }

        tempo += 1;
        maux[vertice][1] = tempo;
    }

   

        
}
