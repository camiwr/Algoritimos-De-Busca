package entidades;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class No {
    private String estado;
    private No pai;
    private String acao;
    private int custo;

    public No(String estado, No pai, String acao, int custo) {
        this.estado = estado;
        this.pai = pai;
        this.acao = acao;
        this.custo = custo;
    }


    // Retorna o nó filho
    public No noFilho(Problema problema, String acao) {
        String proximoEstado = problema.transicao(this.estado, acao);
        if (proximoEstado == null) {
            return null;
        }
        int custoFilho = problema.custo(this.estado, acao, proximoEstado);
        return new No(proximoEstado, this, acao, custoFilho);
    }

    // Retorna todos os filhos
    // public List<No> explorar(Problema problema) {
    //     List<No> nos = new ArrayList<>();
    //     for (String acao : problema.acoes(this.estado)) {
    //         No filho = noFilho(problema, acao);
    //         if (filho != null) {
    //             nos.add(filho);
    //         }
    //     }
    //     return nos;
    // }

    public List<No> explorar(Problema problema) {
        List<No> filhos = new ArrayList<>();
        Map<String, Integer> adjacentes = problema.getAdjacentes(this.estado);
        for (Map.Entry<String, Integer> entrada : adjacentes.entrySet()) {
            String estadoFilho = entrada.getKey();
            int custoAcao = entrada.getValue();
            filhos.add(new No(estadoFilho, this, "Move to " + estadoFilho, this.custo + custoAcao));
        }
        return filhos;
    }

    // Retorna uma lista nós formando o caminho percorrido
    public List<No> caminho() {
        List<No> caminho = new ArrayList<>();
        for (No noAtual = this; noAtual != null; noAtual = noAtual.pai) {
            caminho.add(0, noAtual);
        }
        return caminho;
    }

    // Retorna uma lista de ações da raiz até o nó obejtivo
    public List<String> solucao() {
        List<String> solucao = new ArrayList<>();
        No noAtual = this;
        while (noAtual.pai != null) { 
            solucao.add(0, noAtual.acao);
            noAtual = noAtual.pai;
        }
        return solucao;
    }

    // gets e sets
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public No getPai() {
        return pai;
    }

    public void setPai(No pai) {
        this.pai = pai;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }
    
}
