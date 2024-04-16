package entidades;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Problema {
    private String estadoInicial;
    private String estadoFinal;
    private final Map<String, Map<String, Integer>> mapaRomenia = new HashMap<>();
    private final Map<String, Integer> heuristicaDistancias = new HashMap<>();

    public Problema(String estadoInicial, String estadoFinal) {
        this.estadoInicial = estadoInicial;
        this.estadoFinal = estadoFinal;
        inicializarMapa();
        inicializarHeuristica();
    }
    
    private void inicializarMapa() {

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
    }

    private void inicializarHeuristica() {

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

    public Map<String, Integer> getAdjacentes(String cidade) {
        return mapaRomenia.getOrDefault(cidade, Map.of());
    }

    // retorna uma lista de ações possíveis (cidades acessíveis) a partir de um estado dado.
    public List<String> acoes(String estado) {
        return new ArrayList<>(mapaRomenia.getOrDefault(estado, Map.of()).keySet());
    }

    // verifica se o estado existe no mapa e se a ação (destino) é uma das possíveis a partir desse estado
    public String transicao(String estado, String acao) {
        return mapaRomenia.containsKey(estado) && mapaRomenia.get(estado).containsKey(acao) ? acao : null;
    }

    // verifica se o estado atual é igual ao estado final
    public boolean objetivo(String estado) {
        return estado.equals(this.estadoFinal);
    }

    // retorna o custo entre dois estados diretamente conectados
    public int custo(String estado1, String acao, String estado2) {
        if (mapaRomenia.containsKey(estado1) && mapaRomenia.get(estado1).containsKey(estado2)) {
            return mapaRomenia.get(estado1).get(estado2);
        }
        return Integer.MAX_VALUE;
        // Retorna um valor alto se não houver caminho direto
    }
    
    
    
// gets e sets
    public String getEstadoInicial() {
        return estadoInicial;
    }


    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }


    public String getEstadoFinal() {
        return estadoFinal;
    }


    public void setEstadoFinal(String estadoFinal) {
        this.estadoFinal = estadoFinal;
    }


    public Map<String, Map<String, Integer>> getMapaRomenia() {
        return mapaRomenia;
    }


    public Map<String, Integer> getHeuristicaDistancias() {
        return heuristicaDistancias;
    }
}
