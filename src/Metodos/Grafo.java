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
 * @author Gi Benvenuto
 */
public class Grafo {

    private Representacao rep;
    private boolean estrutura;
    private int qtdvertices;
    private int maux[][];
    private int tempo;

    public static boolean isPonderado() {
        return true;
    }

    public Grafo(Representacao rep, int qtdvertices, boolean estrutura) {
        this.rep = rep;
        this.estrutura = estrutura;
        this.qtdvertices = qtdvertices;
        this.maux = new int[qtdvertices][qtdvertices];
        this.rep.init(qtdvertices, estrutura);
    }

    public void addAdjacencia(int v1, int v2, int valor) {
        rep.addAresta(v1, v2, valor);
    }

    public void exibir() {
        rep.imprimeRepresentacao();
    }

    public void exibirAux() {
        String matriz = "";
        for (int i = 0; i < qtdvertices; i++) {
            for (int j = 0; j < 3; j++) {
                matriz += maux[i][j] + " ";
            }
            matriz += "\n";
        }
        System.out.println(matriz);
    }

    public int[][] getMaux() {
        return maux;
    }

    //Inicializa Matriz Auxiliar
    public void inicializaMaux() {

        for (int i = 0; i < qtdvertices; i++) {
            for (int j = 0; j < 3; j++) {
                maux[i][j] = 0;
            }
        }
    }

    //Componente Conexos
    public int verificaConexo() {
        int grupo = 0;
        if (estrutura == false) { //se for dígrafo
            return -1;
        } else {
            inicializaMaux(); //inicializa a matriz com zero
            tempo = 0;
            for (int i = 0; i < qtdvertices; i++) {
                if (maux[i][0] == 0) { // posição 0 - tempo inicial (se não passou por ele ainda)
                    grupo = grupo + 1;
                    buscaVisitaCaminho(i, grupo);
                }
            }
        }
        return grupo;

    }

    public void buscaVisitaCaminho(int vertice, int grupo) {
        tempo += 1;
        maux[vertice][0] = tempo; //posição 0 - tempo inicial
        maux[vertice][2] = grupo; //posiçao 2 - grupo

        for (int i = 0; i < qtdvertices; i++) {
            if (rep.verificaAdjacencia(vertice, i) != Integer.MAX_VALUE) {
                if (maux[i][0] == 0) {
                    maux[i][2] = grupo;
                    buscaVisitaCaminho(i, grupo);
                }
            }
        }
        tempo += 1;
        maux[vertice][1] = tempo; //posição 1 - tempo final
    }

    public int[][] buscaProfundidade(int noRaiz) {
        inicializaMaux();
        tempo = 0;
        buscaVisita(noRaiz);

        for (int i = 0; i < qtdvertices; i++) {
            if (maux[i][0] == 0) {
                buscaVisita(i);
            }
        }

        return maux;
    }

    public void buscaVisita(int vertice) {
        tempo += 1;
        maux[vertice][0] = tempo; //tempo de inicio

        for (int i = 0; i < qtdvertices; i++) {
            if (rep.verificaAdjacencia(vertice, i) != Integer.MAX_VALUE) {
                if (maux[i][0] == 0) {
                    maux[i][2] = vertice;
                    buscaVisita(i);
                }
            }
        }
        tempo += 1;
        maux[vertice][1] = tempo; //tempo de fim
    }

    //Arvore Geradora Mínima
    //Funções Auxiliares
    public void inicializaMatrizAGM() {
        for (int i = 0; i < qtdvertices; i++) {
            for (int j = 0; j < 3; j++) {
                maux[i][j] = Integer.MAX_VALUE;
            }
        }
    }

    public int excluiMinimo() {
        int min = Integer.MAX_VALUE, pos = Integer.MAX_VALUE;
        for (int i = 0; i < qtdvertices; i++) {
            if (maux[i][1] < min) {
                if (maux[i][0] != 1) {
                    min = maux[i][1];
                    pos = i;
                }
            }
        }

        return pos;
    }
    //Algortimo de Prim

    public int[][] AGM_Prim(int vertice) {
        int valor;
        inicializaMatrizAGM();
        maux[vertice][1] = 0; //Distancia

        while (maux[vertice][0] == Integer.MAX_VALUE) {
            maux[vertice][0] = 1; //Pertence a solução

            for (int j = 0; j < qtdvertices; j++) {
                if (rep.verificaAdjacencia(vertice, j) != Integer.MAX_VALUE && maux[j][0] != 1) {
                    valor = rep.verificaAdjacencia(vertice, j);
                    if (maux[j][1] > valor) {
                        maux[j][1] = valor;
                        maux[j][2] = vertice; //Pai
                    }
                }
            }

            vertice = excluiMinimo();
            if (vertice == Integer.MAX_VALUE) {
                break;
            }
        }
        exibirAux();
        return maux;

    }

    //Busca em Largura
    public int[][] buscaLargura(int vert) {
        int u, j, i = 0;
        int max = Integer.MAX_VALUE;
        int num = qtdvertices;
        ArrayList<Integer> listavertices = new ArrayList();

        for (i = 0; i < num; i++) {
            if (i != vert) {
                maux[i][0] = 0;   //coluna 0 = cor ; 0 = branco,1 = cinza, 2 = preto;
                maux[i][1] = max; //coluna 1 = distancia;
                maux[i][2] = max; //coluna 2 = pai;
            }
        }
        maux[vert][0] = 1;
        maux[vert][1] = 0;
        maux[vert][2] = max;

        listavertices.add(vert);

        while (listavertices.isEmpty() != true) {
            u = (int) listavertices.get(0);
            listavertices.remove(0);

            for (j = 0; j < num; j++) {
                if (rep.verificaAdjacencia(vert, j) != Integer.MAX_VALUE) { //adjacente de u
                    if (maux[j][0] == 0) { // se for branco
                        maux[j][0] = 1; //cinza
                        maux[j][1] = maux[u][1] + 1;
                        maux[j][2] = u;
                        listavertices.add(j);
                    }//if branco
                }//if adjacente  
            }//for adjacencias
            maux[u][0] = 2;
        }
        return maux;

    }

    public void ordenacaoTopologica() {
        ArrayList<No> ordem;
        No aux;
        buscaProfundidade(0);
        ordem = new ArrayList();

        for (int i = 0; i < maux.length; i++) {
            aux = new No(i, maux[i][1]);
            ordem.add(aux);
        }

        Collections.sort(ordem);

        exibirAux();

        for (int i = 0; i < maux.length; i++) {
            System.out.println(ordem.get(i).getVertice());
        }
    }

}
