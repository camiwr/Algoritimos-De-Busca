import java.util.List;

import entidades.BuscasInformadas;
import entidades.BuscasNaoInformadas;
import entidades.No;
import enums.TipoDeBusca;

public class Main {
    public static void main(String[] args) {
        // PARA TESTAR AS BUSCAS BASTA DAR UM RUN JAVA :)

        No inicial = new No("Arad", null, null, 0);
        No objetivo = new No("Bucharest", null, null, 0);

        BuscasNaoInformadas buscasSemCusto = new BuscasNaoInformadas(0, 0);

        // Buscas não-informadas
        testarBusca("Busca em Largura", buscasSemCusto.buscar(inicial, objetivo, TipoDeBusca.LARGURA), false);
        testarBusca("Busca em Profundidade", buscasSemCusto.buscar(inicial, objetivo, TipoDeBusca.PROFUNDIDADE), false);

        BuscasNaoInformadas buscasNaoInformadas = new BuscasNaoInformadas(5, 10);
        BuscasInformadas buscasInformadas = new BuscasInformadas();

        // Buscas não-informadas
        testarBusca("Busca em Profundidade Limitada (limite 5)", buscasNaoInformadas.buscar(inicial, objetivo, TipoDeBusca.PROFUNDIDADE_LIMITADA), false);
        testarBusca("Busca por Aprofundamento Iterativo (máx. profundidade 10)", buscasNaoInformadas.buscar(inicial, objetivo, TipoDeBusca.APROFUNDAMENTO_ITERATIVO), false);

        // Buscas informadas
        testarBusca("Busca de Custo Uniforme", buscasNaoInformadas.buscar(inicial, objetivo, TipoDeBusca.CUSTO_UNIFORME), true);
        testarBusca("Busca Gulosa", buscasInformadas.buscar(inicial, objetivo, TipoDeBusca.GULOSA), true);
        testarBusca("Busca A*", buscasInformadas.buscar(inicial, objetivo, TipoDeBusca.A_ESTRELA), true);
    }

    private static void testarBusca(String descricao, List<No> caminho, boolean mostrarCusto) {
        System.out.println(descricao + ":");
        if (caminho.isEmpty()) {
            System.out.println("Nenhum caminho encontrado.");
        } else {
            for (No no : caminho) {
                if (mostrarCusto) {
                    System.out.println(no.getEstado() + " - custo: " + no.getCusto());
                } else {
                    System.out.println(no.getEstado());
                }
            }
        }
        System.out.println();
    }
}
