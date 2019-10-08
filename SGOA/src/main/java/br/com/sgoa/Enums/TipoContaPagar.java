package br.com.sgoa.Enums;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public enum TipoContaPagar {

    NA("Não defindo"),
    AVULSA("Conta Avulsa"),
    COMPRA("Compra");

    @Getter @Setter
    private String descricao;

    TipoContaPagar(String descricao) {
        this.descricao =descricao;
    }

    public List<TipoContaPagar> listaTipoProduto(){
        List<TipoContaPagar> lista = new ArrayList<>();

        for (TipoContaPagar p : TipoContaPagar.values()){
            lista.add(p);
            System.out.printf("test " + p.getDescricao());
        }
        return lista;
    }
}
