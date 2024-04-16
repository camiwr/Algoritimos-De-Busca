package entidades;

import enums.TipoDeBusca;

import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;


public class BuscasInformadas implements Busca {
    private Map<String, Map<String, Integer>> mapaRomenia;
    private Map<String, Integer> heuristicaDistancias;

    public BuscasInformadas() {
        this.mapaRomenia = inicializarMapa();
        inicializarHeuristica();
    }

    @Override
    public List<No> buscar(No inicial, No objetivo, TipoDeBusca tipo) {
        switch (tipo) {
            case GULOSA:
                return buscaGulosa(inicial, objetivo);
            case A_ESTRELA:
                return buscaAEstrela(inicial, objetivo);
            default:
                throw new UnsupportedOperationException("Tipo de busca não suportado.");
        }
    }

    // Mapa da Romenia
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

    //Heurística de distância em linha reta de uma cidade até Bucareste
    private void inicializarHeuristica() {
        heuristicaDistancias = new HashMap<>();

        heuristicaDistancias.put("Arad", 366);
        heuristicaDistancias.put("Zerind", 374);
        heuristicaDistancias.put("Oradea", 380);
        heuristicaDistancias.put("Sibiu", 253);
        heuristicaDistancias.put("Timisoara", 329);
        heuristicaDistancias.put("Lugoj", 244);
        heuristicaDistancias.put("Mehadia", 241);
        heuristicaDistancias.put("Drobeta", 242);
        heuristicaDistancias.put("Craiova", 160);
        heuristicaDistancias.put("Rimnicu Vilcea", 193);
        heuristicaDistancias.put("Fagaras", 176);
        heuristicaDistancias.put("Pitesti", 100);
        heuristicaDistancias.put("Bucharest", 0);
        heuristicaDistancias.put("Giurgiu", 77);
        heuristicaDistancias.put("Urziceni", 80);
        heuristicaDistancias.put("Hirsova", 151);
        heuristicaDistancias.put("Eforie", 161);
        heuristicaDistancias.put("Vaslui", 199);
        heuristicaDistancias.put("Iasi", 226);
        heuristicaDistancias.put("Neamt", 234);
    }

    // Busca Gulosa
    public List<No> buscaGulosa(No inicial, No objetivo) {
        PriorityQueue<No> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> heuristicaDistancias.get(n.getEstado())));
        Set<String> explored = new HashSet<>();
        frontier.add(inicial);
    
        while (!frontier.isEmpty()) {
            No current = frontier.poll();
            if (current.getEstado().equals(objetivo.getEstado())) {
                return current.caminho();
            }
            explored.add(current.getEstado());
            for (No child : current.explorar(new Problema(current.getEstado(), objetivo.getEstado()))) {
                if (!explored.contains(child.getEstado()) && !frontier.contains(child)) {
                    frontier.add(child);
                }
            }
        }
        return new ArrayList<>();
    }

    //Busca A*
    public List<No> buscaAEstrela(No inicial, No objetivo) {
        PriorityQueue<No> frontier = new PriorityQueue<>(Comparator.comparingInt(n -> n.getCusto() + heuristicaDistancias.get(n.getEstado())));
        Map<String, Integer> custoAteAgora = new HashMap<>();
        frontier.add(inicial);
        custoAteAgora.put(inicial.getEstado(), 0);
    
        while (!frontier.isEmpty()) {
            No current = frontier.poll();
            if (current.getEstado().equals(objetivo.getEstado())) {
                return current.caminho();
            }
    
            for (Map.Entry<String, Integer> adjacente : mapaRomenia.get(current.getEstado()).entrySet()) {
                String proximoEstado = adjacente.getKey();
                int novoCusto = custoAteAgora.get(current.getEstado()) + adjacente.getValue();
                if (!custoAteAgora.containsKey(proximoEstado) || novoCusto < custoAteAgora.get(proximoEstado)) {
                    custoAteAgora.put(proximoEstado, novoCusto);
                    No novoNo = new No(proximoEstado, current, "", novoCusto);
                    frontier.add(novoNo);
                }
            }
        }
        return new ArrayList<>();
    }
    
    
}
