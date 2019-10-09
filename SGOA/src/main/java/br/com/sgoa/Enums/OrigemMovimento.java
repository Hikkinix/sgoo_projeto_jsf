package br.com.sgoa.Enums;

import lombok.Getter;

import java.io.Serializable;

public enum OrigemMovimento implements Serializable {

    NA(""),
    COMPRA("Compra"),
    VENDA("Venda"),
    CONTAPAGA("Conta avulsa paga"),
    CONTARECEBER("Conta avulsa recebida");

    @Getter
    private String descricao;

    OrigemMovimento(String descricao) {
        this.descricao = descricao;
    }
}
