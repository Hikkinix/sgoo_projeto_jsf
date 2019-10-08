package br.com.sgoa.Enums;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public enum TipoProduto {

    MATERIA_PRIMA("Materia - Prima"),
    PRODUTO_VENDA("Produto"),
    PRODUTO_IMOBILIZADO("Imobilizado");

    @Getter
    private String descricao;

    TipoProduto(String descricao) {
        this.descricao =descricao;
    }

    public List<TipoProduto> getListaTipoProduto(){
        List<TipoProduto> descricaoTipos = new ArrayList<>();
        for (TipoProduto tipoProduto : TipoProduto.values()) {
            descricaoTipos.add(tipoProduto);
            System.out.println(tipoProduto.getDescricao());

        }
        return descricaoTipos;
    }
}
