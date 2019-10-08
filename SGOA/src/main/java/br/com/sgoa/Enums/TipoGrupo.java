package br.com.sgoa.Enums;

import lombok.Getter;

public enum TipoGrupo {

    PESSOA("Pessoa"),
    PRODUTO("Produto");

    @Getter
    private String descricao;

    TipoGrupo(String descricao) {
    }
}
