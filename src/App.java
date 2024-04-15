import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        No inicial = new No("Arad", null, null, 0);
        No objetivo = new No("Bucharest", null, null, 0);

        BuscasNaoInformadas busca = new BuscasNaoInformadas();

        //Teste da Busca em largura
        // Sequencia que deve aparecer: [Arad, Sibiu, Fagaras, Bucharest]
        List<No> caminho = busca.buscaEmLargura(inicial, objetivo);

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
