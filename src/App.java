import java.util.List;

import entidades.BuscasNaoInformadas;
import entidades.No;
import enums.TipoBuscaNaoInformada;

public class App {
    public static void main(String[] args) throws Exception {
        No inicial = new No("Arad", null, null, 0);
        No objetivo = new No("Bucharest", null, null, 0);

        BuscasNaoInformadas busca = new BuscasNaoInformadas(0, 0);

        
        List<No> caminhoLimitado = busca.buscar(inicial, objetivo, TipoBuscaNaoInformada.CUSTO_UNIFORME);
        printCaminho(caminhoLimitado, "Custo Uniforme");
    }

    private static void printCaminho(List<No> caminho, String tipoBusca) {
        if (caminho.isEmpty()) {
            System.out.println("Nenhum caminho encontrado usando " + tipoBusca + ".");
        } else {
            System.out.println("Caminho encontrado usando:" + tipoBusca + ":");
            for (No no : caminho) {
                System.out.println(no.getEstado() + " - custo: " + no.getCusto());
            }
        }

    }

}
