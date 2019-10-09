package br.com.sgoa.Enums;

import lombok.Getter;

import java.io.Serializable;

public enum TipoMovimento implements Serializable {

    DEBITO("Debito"),
    CREDITO("Credito");

    @Getter
    private String descricao;

    TipoMovimento(String descricao) {
        this.descricao = descricao;
    }
}
