package br.com.sgoa.Enums;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public enum TipoPlanoPagamento {
    AVISTA("A vista"),
    APRAZO("A prazo");

    @Getter private final String descricao;

    TipoPlanoPagamento(String descricao) {
        this.descricao = descricao;
    }

    public List<TipoPlanoPagamento> getListaTipoPlanos(){
        List<TipoPlanoPagamento> TiposPlanoPagamentos = new ArrayList<>();
        for (TipoPlanoPagamento tipoPlanoPagamento : TipoPlanoPagamento.values()) {
            TiposPlanoPagamentos.add(tipoPlanoPagamento);
            System.out.println(tipoPlanoPagamento.getDescricao());

        }
        return TiposPlanoPagamentos;
    }
}
