import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class BuscasNaoInformadas implements Busca {

    @Override
    public List<No> buscar(No inicial, No objetivo) {
        return buscaEmLargura(inicial, objetivo);
    }
    
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

}
