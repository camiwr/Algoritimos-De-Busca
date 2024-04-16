package entidades;

import java.util.Set;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Stack;
import java.util.ArrayList;

import enums.TipoBuscaNaoInformada;

import java.util.Queue;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class BuscasNaoInformadas implements Busca {
    private Map<String, Map<String, Integer>> mapaRomenia;
    private int limiteProfundidade;
    private int profundidadeMaximaIterativa;

    public BuscasNaoInformadas(int limiteProfundidade, int profundidadeMaximaIterativa) {
        this.mapaRomenia = inicializarMapa();
        this.limiteProfundidade = limiteProfundidade;
        this.profundidadeMaximaIterativa = profundidadeMaximaIterativa;
    }

    @Override
    public List<No> buscar(No inicial, No objetivo, TipoBuscaNaoInformada tipo) {
        switch (tipo) {
            case LARGURA:
                return buscaEmLargura(inicial, objetivo);
            case PROFUNDIDADE:
                return buscaEmProfundidade(inicial, objetivo);
            case PROFUNDIDADE_LIMITADA:
                return buscaProfundidadeLimitada(inicial, objetivo, this.limiteProfundidade);
            case APROFUNDAMENTO_ITERATIVO:
                return buscaAprofundamentoIterativo(inicial, objetivo, this.profundidadeMaximaIterativa);
            case CUSTO_UNIFORME:
                return buscaCustoUniforme(inicial, objetivo);
            default:
                throw new IllegalArgumentException("Tipo de busca não suportado");
        }
    }
        
    private Map<String, Map<String, Integer>> inicializarMapa() {
        Map<String, Map<String, Integer>> mapaRomenia = new HashMap<>();
        mapaRomenia.put("Arad", Map.of("Sibiu", 140, "Zerind", 75, "Timisoara", 118));
        mapaRomenia.put("Zerind", Map.of("Arad", 75, "Oradea", 71));
        mapaRomenia.put("Oradea", Map.of("Zerind", 71, "Sibiu", 151));
        mapaRomenia.put("Sibiu", Map.of("Arad", 140, "Oradea", 151, "Fagaras", 99, "Rimnicu Vilcea", 80));
        mapaRomenia.put("Timisoara", Map.of("Arad", 118, "Lugoj", 111));
        mapaRomenia.put("Lugoj", Map.of("Timisoara", 111, "Mehadia", 70));
        mapaRomenia.put("Mehadia", Map.of("Lugoj", 70, "Drobeta", 75));
        mapaRomenia.put("Drobeta", Map.of("Mehadia", 75, "Craiova", 120));
        mapaRomenia.put("Craiova", Map.of("Drobeta", 120, "Rimnicu Vilcea", 146, "Pitesti", 138));
        mapaRomenia.put("Rimnicu Vilcea", Map.of("Sibiu", 80, "Craiova", 146, "Pitesti", 97));
        mapaRomenia.put("Fagaras", Map.of("Sibiu", 99, "Bucharest", 211));
        mapaRomenia.put("Pitesti", Map.of("Rimnicu Vilcea", 97, "Craiova", 138, "Bucharest", 101));
        mapaRomenia.put("Bucharest", Map.of("Fagaras", 211, "Pitesti", 101, "Giurgiu", 90, "Urziceni", 85));
        mapaRomenia.put("Giurgiu", Map.of("Bucharest", 90));
        mapaRomenia.put("Urziceni", Map.of("Bucharest", 85, "Vaslui", 142, "Hirsova", 98));
        mapaRomenia.put("Hirsova", Map.of("Urziceni", 98, "Eforie", 86));
        mapaRomenia.put("Eforie", Map.of("Hirsova", 86));
        mapaRomenia.put("Vaslui", Map.of("Iasi", 92, "Urziceni", 142));
        mapaRomenia.put("Iasi", Map.of("Vaslui", 92, "Neamt", 87));
        mapaRomenia.put("Neamt", Map.of("Iasi", 87));
            
        return mapaRomenia;
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

    // Busca de Custo uniforme
    public List<No> buscaCustoUniforme(No inicial, No objetivo) {
        PriorityQueue<No> frontier = new PriorityQueue<>(Comparator.comparingInt(No::getCusto));
        Map<String, Integer> custoAteAgora = new HashMap<>();
        frontier.add(inicial);
        custoAteAgora.put(inicial.getEstado(), 0);

        while (!frontier.isEmpty()) {
            No current = frontier.poll();
            if (current.getEstado().equals(objetivo.getEstado())) {
                return current.caminho();
            }

            for (Map.Entry<String, Integer> adjacente : mapaRomenia.get(current.getEstado()).entrySet()) {
                No next = new No(adjacente.getKey(), current, "", current.getCusto() + adjacente.getValue());
                int newCost = custoAteAgora.get(current.getEstado()) + adjacente.getValue();
                if (!custoAteAgora.containsKey(next.getEstado()) || newCost < custoAteAgora.get(next.getEstado())) {
                    custoAteAgora.put(next.getEstado(), newCost);
                    next.setCusto(newCost);  // atualiza o custo total no nó
                    frontier.add(next);
                }
            }
        }
        return new ArrayList<>();
    }

    // Busca de Profundidade Limitada
    private List<No> buscaProfundidadeLimitada(No atual, No objetivo, int limite) {
        if (atual.getEstado().equals(objetivo.getEstado())) {
            return atual.caminho();
        }
        if (limite == 0) {
            return new ArrayList<>();
        }
        List<No> resultadoTemporario = new ArrayList<>();
        for (No child : atual.explorar(new Problema(atual.getEstado(), objetivo.getEstado()))) {
            List<No> resultado = buscaProfundidadeLimitada(child, objetivo, limite - 1);
            if (!resultado.isEmpty()) {
                resultadoTemporario = resultado;
                break;
            }
        }
        return resultadoTemporario;
    }
    
    // Busca em Aprofundamento Iterativo
    private List<No> buscaAprofundamentoIterativo(No inicial, No objetivo, int maxDepth) {
        for (int depth = 0; depth <= maxDepth; depth++) {
            List<No> resultado = buscaProfundidadeLimitada(inicial, objetivo, depth);
            if (!resultado.isEmpty()) {
                return resultado;
            }
        }
        return new ArrayList<>();
    }
}
