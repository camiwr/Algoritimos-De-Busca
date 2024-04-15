import java.util.List;

import entidades.BuscasNaoInformadas;
import entidades.No;
import enums.TipoBuscaNaoInformada;

public class App {
    public static void main(String[] args) throws Exception {
        No inicial = new No("Arad", null, null, 0);
        No objetivo = new No("Bucharest", null, null, 0);

        BuscasNaoInformadas busca = new BuscasNaoInformadas();
        List<No> caminho = busca.buscar(inicial, objetivo, TipoBuscaNaoInformada.PROFUNDIDADE);

        // busca em profundidade
        if (caminho.isEmpty()) {
            System.out.println("Nenhum caminho encontrado.");
        } else {
            System.out.println("Caminho encontrado:");
            for (No no : caminho) {
                System.out.println(no.getEstado());
            }
        }
    }
}
