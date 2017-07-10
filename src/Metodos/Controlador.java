/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Metodos;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author Gustavoo
 */
public class Controlador {
    private static Grafo g;
    
    public boolean lerArquivo(String nome, boolean rep) {
        int qtdvertices, est;
        boolean estrutura;
        String line;

        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);

           
            est = Integer.parseInt(lerArq.readLine());
            qtdvertices = Integer.parseInt(lerArq.readLine());

            if (est == 1) {
                estrutura = false;
            } else {
                estrutura = true;
            }
            if (rep == true) {
                g = new Grafo(new Matriz(), qtdvertices, estrutura);
            } else {
                g = new Grafo(new Lista(), qtdvertices, estrutura);
            }

            while ((line = lerArq.readLine()) != null && line.trim().length() > 0) {
                StringTokenizer t1 = new StringTokenizer(line, " ");
                int v1 = Integer.parseInt(t1.nextToken().trim()); //verticeInicial
                int v2 = Integer.parseInt(t1.nextToken().trim()); //verticeFinal
                int v3 = Integer.parseInt(t1.nextToken().trim()); //Valor

                g.addAdjacencia(v1, v2, v3);
            }
            
        } catch (IOException e) {

            return false;
        }
        return true;

    }
    
    
    public void componentesConexas (){
        g.verificaConexo();
        g.exibirAux();
    }
    
    public void ordenacaoTopologica(){
        g.ordenacaoTopologica();
    }
    
   
}
