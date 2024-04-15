package entidades;

import java.util.Set;
import java.util.List;
import java.util.Stack;

import enums.TipoBuscaNaoInformada;

import java.util.Queue;
import java.util.HashSet;
import java.util.LinkedList;

public class BuscasNaoInformadas implements Busca {

    @Override
    public List<No> buscar(No inicial, No objetivo, TipoBuscaNaoInformada tipo) {
        switch (tipo) {
            case LARGURA:
                return buscaEmLargura(inicial, objetivo);
            case PROFUNDIDADE:
                return buscaEmProfundidade(inicial, objetivo);
            default:
                throw new IllegalArgumentException("Tipo de busca não suportado");
        }
    }
    
    // busca em largura
    public List<No> buscaEmLargura(No inicial, No objetivo) {
        Queue<No> fronteira = new LinkedList<>();
        Set<String> explorados = new HashSet<>();

        fronteira.add(inicial);
        while (!fronteira.isEmpty()) {
            No atual = fronteira.poll();
            if (atual.getEstado().equals(objetivo.getEstado())) {
                return atual.caminho();
            }
            explorados.add(atual.getEstado());
            // O estado do nó atual é adicionado ao conjunto de estados explorados

            for (No filho : atual.explorar(new Problema(atual.getEstado(), objetivo.getEstado()))) {
                if (!explorados.contains(filho.getEstado()) && !fronteira.contains(filho)) {
                    fronteira.add(filho);
                }
            }
        }
        return null;
    }

    // Busca em Profundidade
    public List<No> buscaEmProfundidade(No inicial, No objetivo) {
        Stack<No> frontier = new Stack<>();
        Set<String> explored = new HashSet<>();

        frontier.push(inicial);
        while (!frontier.isEmpty()) {
            No current = frontier.pop();
            if (current.getEstado().equals(objetivo.getEstado())) {
                return current.caminho();
            }
            if (!explored.contains(current.getEstado())) {
                explored.add(current.getEstado());
                for (No child : current.explorar(new Problema(current.getEstado(), objetivo.getEstado()))) {
                    if (!explored.contains(child.getEstado())) {
                        frontier.push(child);
                    }
                }
            }
        }
        return null;
    }
}
