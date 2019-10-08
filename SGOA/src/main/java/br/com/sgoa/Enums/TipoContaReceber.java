package br.com.sgoa.Enums;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public enum TipoContaReceber {

    NA("Não defindo"),
    AVULSA("Conta Avulsa"),
    VENDA("venda");

    @Getter @Setter
    private String descricao;

    TipoContaReceber(String descricao) {
        this.descricao =descricao;
    }

    public List<TipoContaReceber> listaTipoProduto(){
        List<TipoContaReceber> lista = new ArrayList<>();

        for (TipoContaReceber p : TipoContaReceber.values()){
            lista.add(p);
            System.out.printf("test " + p.getDescricao());
        }
        return lista;
    }
}
